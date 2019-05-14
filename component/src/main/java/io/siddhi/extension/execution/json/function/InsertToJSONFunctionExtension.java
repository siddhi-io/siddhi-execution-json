/*
 * Copyright (c)  2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.siddhi.extension.execution.json.function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.InvalidModificationException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.exception.SiddhiAppRuntimeException;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.executor.function.FunctionExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;
import io.siddhi.query.api.exception.SiddhiAppValidationException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;


/**
 * This class provides implementation for inserting values to the given json using the path specified.
 */
@Extension(
        name = "setElement",
        namespace = "json",
        description = "This method allows to insert elements into a given JSON present in a specific path. If there " +
                "is no valid path given, it returns the original JSON. Otherwise, it returns the new JSON.",

        parameters = {
                @Parameter(
                        name = "json",
                        description = "The JSON input into which is this function inserts the new value.",
                        type = {DataType.STRING, DataType.OBJECT}),
                @Parameter(
                        name = "path",
                        description = "The path on the JSON input which is used to insert the given element.",
                        type = {DataType.STRING}),
                @Parameter(
                        name = "jsonelement",
                        description = "The JSON element which is inserted by the function into the input JSON.",
                        type = {DataType.STRING, DataType.BOOL, DataType.DOUBLE, DataType.FLOAT, DataType.INT,
                                DataType.LONG, DataType.OBJECT}),
                @Parameter(
                        name = "key",
                        description = "The key which is used to insert the given element into the input JSON.",
                        type = {DataType.STRING})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns the JSON object with the inserted elements.",
                type = {DataType.OBJECT}),
        examples = @Example(
                syntax = "define stream InputStream(json string);\n" +
                        "from InputStream\n" +
                        "select json:setElement(json,\"$.name\") as name\n" +
                        "insert into OutputStream;",
                description = "This returns the JSON object present in the given path with the newly inserted JSON" +
                        "element. The results are directed to the 'OutputStream' stream.")

)
public class InsertToJSONFunctionExtension extends FunctionExecutor {
    private static final Logger log = Logger.getLogger(InsertToJSONFunctionExtension.class);
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    /**
     * The initialization method for {@link FunctionExecutor}, which will be called before other methods and validate
     * the all configuration and getting the initial values.
     *
     * @param attributeExpressionExecutors are the executors of each attributes in the Function
     * @param configReader                 this hold the {@link FunctionExecutor} extensions configuration reader.
     * @param siddhiQueryContext           Siddhi query context
     */
    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors, ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length == 4 || attributeExpressionExecutors.length == 3) {
            if (attributeExpressionExecutors[0] == null) {
                throw new SiddhiAppValidationException("Invalid input given to first argument 'json' of " +
                        "json:setElement() function. Input for 'json' argument cannot be null");
            }
            Attribute.Type inputJsonAttributeType = attributeExpressionExecutors[0].getReturnType();
            if (!(inputJsonAttributeType == Attribute.Type.STRING || inputJsonAttributeType == Attribute.Type.OBJECT)) {
                throw new SiddhiAppValidationException("Invalid parameter type found for first argument 'json' of " +
                        "json:setElement() function, required " + Attribute.Type.STRING + " or " + Attribute.Type
                        .OBJECT + ", but found " + inputJsonAttributeType.toString());
            }

            if (attributeExpressionExecutors[1] == null) {
                throw new SiddhiAppValidationException("Invalid input given to second argument 'path' of " +
                        "json:setElement() function. Input 'path' argument cannot be null");
            }
            Attribute.Type pathAttributeType = attributeExpressionExecutors[1].getReturnType();
            if (pathAttributeType != Attribute.Type.STRING) {
                throw new SiddhiAppValidationException("Invalid parameter type found for second argument 'path' of " +
                        "json:setElement() function, required " + Attribute.Type.STRING + ", but found " +
                        pathAttributeType.toString());
            }

            if (attributeExpressionExecutors[2] == null) {
                throw new SiddhiAppValidationException("Invalid input given to third argument 'jsonElement' of " +
                        "json:setElement() function. Input 'jsonElement' argument cannot be null");
            }

            if (attributeExpressionExecutors.length == 4) {
                if (attributeExpressionExecutors[3] == null) {
                    throw new SiddhiAppValidationException("Invalid input given to third argument 'jsonElement' of " +
                            "json:setElement() function. Input 'jsonElement' argument cannot be null");
                }
                Attribute.Type keyAttributeType = attributeExpressionExecutors[3].getReturnType();
                if (!(keyAttributeType == Attribute.Type.STRING)) {
                    throw new SiddhiAppValidationException("Invalid parameter type found for fourth argument " +
                            "'key'" + " of json:setElement() function, required " + Attribute.Type.STRING + ", " +
                            "but found " + keyAttributeType.toString());
                }
            }
        } else {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to json:setElement() function, "
                    + "required 3 or 4, but found " + attributeExpressionExecutors.length);
        }

        return null;
    }

    /**
     * The main execution method which will be called upon event arrival
     * when there are more than one Function parameter
     *
     * @param data the runtime values of Function parameters
     * @return the Function result
     */
    @Override
    protected Object execute(Object[] data, State state) {
        String jsonInput;
        if (data[0] instanceof String) {
            jsonInput = (String) data[0];
        } else {
            jsonInput = gson.toJson(data[0]);
        }
        String path = data[1].toString();
        Object jsonElement = data[2];
        String key = null;
        if (data.length == 4) {
            key = data[3].toString();
        }
        DocumentContext documentContext = JsonPath.parse(jsonInput);
        Object object = null;
        try {
            object = JsonPath.read(jsonInput, path);
        } catch (PathNotFoundException e) {
            log.warn("The path '" + path + "' is not a valid path for the json '" + jsonInput + "'. Please provide a" +
                    " valid path.");
        } catch (InvalidJsonException e) {
            throw new SiddhiAppRuntimeException("The input JSON is not a valid JSON. Input JSON - " + jsonInput, e);
        }
        if (object instanceof List) {
            try {
                Object parsedJsonElement = gson.fromJson(jsonElement.toString(), Object.class);
                if (jsonElement instanceof String && (parsedJsonElement instanceof Map
                        || parsedJsonElement instanceof List)) {
                    documentContext.add(path, gson.fromJson(jsonElement.toString(), Object.class));
                } else {
                    documentContext.add(path, jsonElement);
                }
            } catch (InvalidModificationException e) {
                log.warn("The path '" + path + "' is not a valid path for the json '" + jsonInput + "'. Please " +
                        "provide a valid path.");
            }
        } else {
            if (key != null) {
                Object parsedJsonElement = gson.fromJson(jsonElement.toString(), Object.class);
                if (jsonElement instanceof String && (parsedJsonElement instanceof Map
                        || parsedJsonElement instanceof List)) {
                    documentContext.put(path, key, gson.fromJson(jsonElement.toString(), Object.class));
                } else {
                    documentContext.put(path, key, jsonElement);
                }
            } else {
                log.warn("Please provide a valid key to insert the given json element. The key cannot be null.");
            }
        }
        return documentContext.json();
    }

    /**
     * The main execution method which will be called upon event arrival
     * when there are zero or one Function parameter
     *
     * @param data null if the Function parameter count is zero or
     *             runtime data value of the Function parameter
     * @return the Function result
     */
    @Override
    protected Object execute(Object data, State state) {
        return null;
    }

    /**
     * return a Class object that represents the formal return type of the method represented by this Method object.
     *
     * @return the return type for the method this object represents
     */
    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.OBJECT;
    }

}
