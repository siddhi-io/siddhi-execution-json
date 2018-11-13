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

package org.wso2.extension.siddhi.execution.json.function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.InvalidModificationException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.log4j.Logger;
import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;

import java.util.List;
import java.util.Map;


/**
 * This class provides implementation for inserting values to the given json using the path specified.
 */
@Extension(
        name = "setElement",
        namespace = "json",
        description = "This method allows to insert elements to the given json based on the specified path. If there " +
                "is no valid path given, it will return the original json. Otherwise it will return the new json",
        parameters = {
                @Parameter(
                        name = "json",
                        description = "The json input which is used to insert the given value",
                        type = {DataType.STRING, DataType.OBJECT}),
                @Parameter(
                        name = "path",
                        description = "The path which is used to insert the given element to the input json",
                        type = {DataType.STRING}),
                @Parameter(
                        name = "jsonelement",
                        description = "The json element which is inserted into the given input json",
                        type = {DataType.STRING, DataType.BOOL, DataType.DOUBLE, DataType.FLOAT, DataType.INT,
                                DataType.LONG, DataType.OBJECT}),
                @Parameter(
                        name = "key",
                        description = "The key which is used to insert the given element to the input json",
                        type = {DataType.STRING})
        },
        returnAttributes = @ReturnAttribute(
                description = "returns the json object with inserted elements",
                type = {DataType.OBJECT}),
        examples = @Example(
                description = "This will return the corresponding json object based on the given path and json element",
                syntax = "define stream InputStream(json string);\n" +
                        "from IpStream\n" +
                        "select json:setElement(json,\"$.name\") as name\n" +
                        "insert into OutputStream;")
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
     * @param siddhiAppContext             Siddhi app runtime context
     */
    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors, ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
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

    }

    /**
     * The main execution method which will be called upon event arrival
     * when there are more than one Function parameter
     *
     * @param data the runtime values of Function parameters
     * @return the Function result
     */
    @Override
    protected Object execute(Object[] data) {
        String jsonInput = data[0].toString();
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
    protected Object execute(Object data) {
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

    /**
     * Used to collect the serializable state of the processing element, that need to be
     * persisted for reconstructing the element to the same state on a different point of time
     *
     * @return stateful objects of the processing element as an map
     */
    @Override
    public Map<String, Object> currentState() {
        return null;
    }

    /**
     * Used to restore serialized state of the processing element, for reconstructing
     * the element to the same state as if was/ on a previous point of time.
     *
     * @param state the stateful objects of the processing element as a map.
     *              This is the same map that is created upon calling currentState() method.
     */
    @Override
    public void restoreState(Map<String, Object> state) {

    }
}
