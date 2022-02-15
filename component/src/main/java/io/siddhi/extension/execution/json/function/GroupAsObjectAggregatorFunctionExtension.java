/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.siddhi.extension.execution.json.function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ParameterOverload;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.exception.SiddhiAppRuntimeException;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.query.processor.ProcessingMode;
import io.siddhi.core.query.selector.attribute.aggregator.AttributeAggregatorExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.siddhi.query.api.definition.Attribute.Type.OBJECT;
import static net.minidev.json.parser.JSONParser.MODE_JSON_SIMPLE;

/**
 * groupAsObject(json, enclosing.element, distinct)
 * Returns JSON object by merging all JSON elements if enclosing element is provided.
 * Returns a JSON array by adding all JSON elements if enclosing element is not provided
 */
@Extension(
        name = "groupAsObject",
        namespace = "json",
        description = "This function aggregates the JSON elements and returns a JSON object by adding " +
                "enclosing.element if it is provided. If enclosing.element is not provided it aggregate the JSON " +
                "elements returns a JSON array.",
        parameters = {
                @Parameter(name = "json",
                        description = "The JSON element that needs to be aggregated.",
                        type = {DataType.STRING, DataType.OBJECT},
                        dynamic = true),
                @Parameter(name = "enclosing.element",
                        description = "The JSON element used to enclose the aggregated JSON elements.",
                        type = {DataType.STRING}, optional = true, defaultValue = "EMPTY_STRING", dynamic = true),
                @Parameter(name = "distinct",
                        description = "This is used to only have distinct JSON elements in the concatenated " +
                                "JSON object/array that is returned.",
                        type = {DataType.BOOL}, optional = true, defaultValue = "false", dynamic = true)
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"json"}),
                @ParameterOverload(parameterNames = {"json", "distinct"}),
                @ParameterOverload(parameterNames = {"json", "enclosing.element"}),
                @ParameterOverload(parameterNames = {"json", "enclosing.element", "distinct"})
        },
        returnAttributes = @ReturnAttribute(
                description = "This returns a JSON object if enclosing element is provided. If there is no enclosing " +
                        "element then it returns a JSON array.",
                type = {DataType.OBJECT}),
        examples = {
                @Example(
                        syntax = "from InputStream#window.length(5)\n" +
                                "select json:groupAsObject(\"json\") as groupedJSONArray\n" +
                                "input OutputStream;",
                        description = "When we input events having values for the `json` as " +
                                "`{\"date\":\"2013-11-19\",\"time\":\"10:30\"}` and " +
                                "`{\"date\":\"2013-11-19\",\"time\":\"12:20\"}`, it" +
                                " returns `[{\"date\":\"2013-11-19\",\"time\":\"10:30\"}" +
                                "{\"date\":\"2013-11-19\",\"time\":\"12:20\"}]` to the 'OutputStream'."),
                @Example(
                        syntax = "from InputStream#window.length(5)\n" +
                                "select json:groupAsObject(\"json\", true) as groupedJSONArray\n" +
                                "input OutputStream;",
                        description = "When we input events having values for the `json` as " +
                                "`{\"date\":\"2013-11-19\",\"time\":\"10:30\"}` and " +
                                "`{\"date\":\"2013-11-19\",\"time\":\"10:30\"}`, it" +
                                " returns `[{\"date\":\"2013-11-19\",\"time\":\"10:30\"}]` to the 'OutputStream'."),
                @Example(
                        syntax = "from InputStream#window.length(5)\n" +
                                "select json:groupAsObject(\"json\", \"result\") as groupedJSONArray\n" +
                                "input OutputStream;",
                        description = "When we input events having values for the `json` as " +
                                "`{\"date\":\"2013-11-19\",\"time\":\"10:30\"}` and " +
                                "`{\"date\":\"2013-11-19\",\"time\":\"12:20\"}`, it" +
                                " returns `{\"result\":[{\"date\":\"2013-11-19\",\"time\":\"10:30\"}," +
                                "{\"date\":\"2013-11-19\",\"time\":\"12:20\"}}` to the 'OutputStream'."),
                @Example(
                        syntax = "from InputStream#window.length(5)\n" +
                                "select json:groupAsObject(\"json\", \"result\", true) as groupedJSONArray\n" +
                                "input OutputStream;",
                        description = "When we input events having values for the `json` as " +
                                "`{\"date\":\"2013-11-19\",\"time\":\"10:30\"}` and " +
                                "`{\"date\":\"2013-11-19\",\"time\":\"10:30\"}`, it" +
                                " returns `{\"result\":[{\"date\":\"2013-11-19\",\"time\":\"10:30\"}]}` " +
                                "to the 'OutputStream'.")
        }

)
public class GroupAsObjectAggregatorFunctionExtension
        extends AttributeAggregatorExecutor<GroupAsObjectAggregatorFunctionExtension.ExtensionState> {

    private static final long serialVersionUID = 1L;
    private static final String KEY_DATA_MAP = "dataMap";
    private Map<Object, Integer> dataMap = new LinkedHashMap<>();
    private SiddhiQueryContext siddhiQueryContext;
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    protected StateFactory<ExtensionState> init(ExpressionExecutor[] expressionExecutors,
                                                ProcessingMode processingMode,
                                                boolean b,
                                                ConfigReader configReader,
                                                SiddhiQueryContext siddhiQueryContext) {
        this.siddhiQueryContext = siddhiQueryContext;
        return () -> new ExtensionState();
    }

    @Override
    public Object processAdd(Object o, ExtensionState extensionState) {
        addJSONElement(o);
        return constructJSONObject(null, false);
    }

    @Override
    public Object processAdd(Object[] objects, ExtensionState extensionState) {
        addJSONElement(objects[0]);
        return processJSONObject(objects);
    }

    @Override
    public Object processRemove(Object o, ExtensionState extensionState) {
        removeJSONElement(o);
        return constructJSONObject(null, false);
    }

    @Override
    public Object processRemove(Object[] objects, ExtensionState extensionState) {
        removeJSONElement(objects[0]);
        return processJSONObject(objects);
    }

    @Override
    public Object reset(ExtensionState extensionState) {
        dataMap.clear();
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return OBJECT;
    }

    private Object processJSONObject(Object[] objects) {
        if (objects.length == 3) {
            return constructJSONObject(objects[1].toString(), Boolean.parseBoolean(objects[2].toString()));
        } else {
            if (objects[1] instanceof Boolean) {
                return constructJSONObject(null, Boolean.parseBoolean(objects[1].toString()));
            } else {
                return constructJSONObject(objects[1].toString(), false);
            }
        }
    }

    private void addJSONElement(Object json) {
        JSONObject jsonObject = getJSONObject(json);
        Integer count = dataMap.get(jsonObject);
        dataMap.put(jsonObject, (count == null) ? 1 : count + 1);
    }

    private void removeJSONElement(Object json) {
        JSONObject jsonObject = getJSONObject(json);
        Integer count = dataMap.get(jsonObject);
        if (count == 1) {
            dataMap.remove(jsonObject);
        } else if (count > 1) {
            dataMap.put(jsonObject, count - 1);
        }
    }

    private JSONObject getJSONObject(Object json) {
        JSONObject jsonObject;
        if (json instanceof JSONObject) {
            try {
                jsonObject = (JSONObject) json;
            } catch (ClassCastException e) {
                throw new SiddhiAppRuntimeException(siddhiQueryContext.getSiddhiAppContext().getName() + ":" +
                        siddhiQueryContext.getName() +
                        ": Provided value is not a valid JSON object." + json, e);
            }
        } else if (json instanceof Map) {
            JSONParser jsonParser = new JSONParser(MODE_JSON_SIMPLE);
            try {
                jsonObject = (JSONObject) jsonParser.parse(gson.toJson(json));
            } catch (ParseException e) {
                throw new SiddhiAppRuntimeException(siddhiQueryContext.getSiddhiAppContext().getName() + ":" +
                        siddhiQueryContext.getName() +
                        ": Cannot parse the given json map into JSONObject." + json, e);
            }
        } else if (json == null) {
            jsonObject = null;
        } else {
            JSONParser jsonParser = new JSONParser(MODE_JSON_SIMPLE);
            try {
                jsonObject = (JSONObject) jsonParser.parse(json.toString());
            } catch (ParseException e) {
                throw new SiddhiAppRuntimeException(siddhiQueryContext.getSiddhiAppContext().getName() + ":" +
                        siddhiQueryContext.getName() +
                        ": Cannot parse the given json into JSONObject." + json, e);
            }
        }
        return jsonObject;
    }

    private Object constructJSONObject(String enclosingElement, boolean isDistinct) {
        JSONArray jsonArray = new JSONArray();
        if (!isDistinct) {
            for (Map.Entry<Object, Integer> entry : dataMap.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    jsonArray.add(entry.getKey());
                }
            }
        } else {
            jsonArray.addAll(dataMap.keySet());
        }

        if (enclosingElement != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(enclosingElement, jsonArray);
            return jsonObject;
        }

        return jsonArray;
    }

    class ExtensionState extends State {

        private final Map<String, Object> state = new HashMap<>();

        @Override
        public boolean canDestroy() {
            return dataMap.isEmpty();
        }

        @Override
        public Map<String, Object> snapshot() {
            return state;
        }

        @Override
        public void restore(Map<String, Object> map) {
            dataMap = (Map<Object, Integer>) map.get(KEY_DATA_MAP);
        }

        private ExtensionState() {
            state.put(KEY_DATA_MAP, dataMap);
        }
    }
}
