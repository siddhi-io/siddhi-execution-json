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
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ParameterOverload;
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


/**
 * This class provides implementation for getting Long values from the given json based on the 'path' provided.
 */
@Extension(
        name = "getLong",
        namespace = "json",
        description = "This returns the long value of the JSON element present in the given path. If" +
                "there is no valid long value in the given path, the method returns 'null'.",
        parameters = {
                @Parameter(
                        name = "json",
                        description = "The JSON input that holds the value in the given path.",
                        type = {DataType.STRING, DataType.OBJECT},
                        dynamic = true),
                @Parameter(
                        name = "path",
                        description = "The path of the JSON element from which the 'getLong' function" +
                                "fetches the long value.",
                        type = {DataType.STRING},
                        dynamic = true)
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"json", "path"})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns the long value of input JSON from the input stream.",
                type = {DataType.LONG}),
        examples = @Example(
                syntax = "json:getLong(json,\"$.age\") as age\n",
                description = "This returns the long value of the JSON input in the given path."
        )
)
public class GetLongJSONFunctionExtension extends FunctionExecutor {
    private static final Logger log = Logger.getLogger(GetLongJSONFunctionExtension.class);
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
        if (attributeExpressionExecutors.length == 2) {
            if (attributeExpressionExecutors[0] == null) {
                throw new SiddhiAppValidationException("Invalid input given to first argument 'json' of " +
                        "json:getLong() function. Input for 'json' argument cannot be null");
            }
            Attribute.Type firstAttributeType = attributeExpressionExecutors[0].getReturnType();
            if (!(firstAttributeType == Attribute.Type.STRING || firstAttributeType == Attribute.Type.OBJECT)) {
                throw new SiddhiAppValidationException("Invalid parameter type found for first argument 'json' of " +
                        "json:getLong() function, required " + Attribute.Type.STRING + " or " + Attribute.Type
                        .OBJECT + ", but found " + firstAttributeType.toString());
            }

            if (attributeExpressionExecutors[1] == null) {
                throw new SiddhiAppValidationException("Invalid input given to second argument 'path' of " +
                        "json:getLong() function. Input 'path' argument cannot be null");
            }
            Attribute.Type secondAttributeType = attributeExpressionExecutors[1].getReturnType();
            if (secondAttributeType != Attribute.Type.STRING) {
                throw new SiddhiAppValidationException("Invalid parameter type found for second argument 'path' of " +
                        "json:getLong() function, required " + Attribute.Type.STRING + ", but found " +
                        secondAttributeType.toString());
            }
        } else {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to json:getLong() function, "
                    + "required 2, but found " + attributeExpressionExecutors.length);
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
        Object filteredJsonElement = null;
        Long returnValue;
        try {
            filteredJsonElement = JsonPath.read(jsonInput, path);
        } catch (PathNotFoundException e) {
            log.error("Cannot find json element for the path '" + path + "'. Hence returning the default value 'null'");
        } catch (InvalidJsonException e) {
            throw new SiddhiAppRuntimeException("The input JSON is not a valid JSON. Input JSON - " + jsonInput, e);
        }
        if (filteredJsonElement instanceof List) {
            if (((List) filteredJsonElement).size() != 1) {
                filteredJsonElement = null;
                log.error("Multiple matches or No matches for the given path '" + path + "' in input json. Please use" +
                        " valid path which provide exact one match in the given json");
            } else {
                filteredJsonElement = ((List) filteredJsonElement).get(0);
            }
        }
        if (filteredJsonElement == null) {
            return null;
        }
        try {
            returnValue = Long.parseLong(filteredJsonElement.toString());
        } catch (NumberFormatException e) {
            returnValue = null;
            log.error("The value that is retrieved using the given path '" + path + "', is not a valid long value. " +
                    "Hence returning the default value 'null'");
        }
        return returnValue;
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
        return Attribute.Type.LONG;
    }

}
