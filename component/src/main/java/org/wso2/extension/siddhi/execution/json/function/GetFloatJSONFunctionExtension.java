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
 * This class provides implementation for getting Float values from the given json based on the 'path' provided.
 */
@Extension(
        name = "getFloat",
        namespace = "json",
        description = "This method will return the Float value of the Json element corresponding to the given path. " +
                "If " +
                "there is no valid Float value at the given path, the method will return 'null'",
        parameters = {
                @Parameter(
                        name = "json",
                        description = "The json input which is used get the value against the given path",
                        type = {DataType.STRING, DataType.OBJECT}),
                @Parameter(
                        name = "path",
                        description = "The path which is used to get the value from given json",
                        type = {DataType.STRING})
        },
        returnAttributes = @ReturnAttribute(
                description = "returns Float value of input json against the given path",
                type = {DataType.FLOAT}),
        examples = @Example(
                description = "This will return the corresponding Float value based on the given path",
                syntax = "define stream InputStream(json string);\n" +
                        "from IpStream\n" +
                        "select json:getFloat(json,\"$.name\") as name\n" +
                        "insert into OutputStream;")
)
public class GetFloatJSONFunctionExtension extends FunctionExecutor {
    private static final Logger log = Logger.getLogger(GetFloatJSONFunctionExtension.class);

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
        if (attributeExpressionExecutors.length == 2) {
            if (attributeExpressionExecutors[0] == null) {
                throw new SiddhiAppValidationException("Invalid input given to first argument 'json' of " +
                        "json:getFloat() function. Input for 'json' argument cannot be null");
            }
            Attribute.Type firstAttributeType = attributeExpressionExecutors[0].getReturnType();
            if (!(firstAttributeType == Attribute.Type.STRING || firstAttributeType == Attribute.Type.OBJECT)) {
                throw new SiddhiAppValidationException("Invalid parameter type found for first argument 'json' of " +
                        "json:getFloat() function, required " + Attribute.Type.STRING + " or " + Attribute.Type
                        .OBJECT + ", but found " + firstAttributeType.toString());
            }

            if (attributeExpressionExecutors[1] == null) {
                throw new SiddhiAppValidationException("Invalid input given to second argument 'path' of " +
                        "json:getFloat() function. Input 'path' argument cannot be null");
            }
            Attribute.Type secondAttributeType = attributeExpressionExecutors[1].getReturnType();
            if (secondAttributeType != Attribute.Type.STRING) {
                throw new SiddhiAppValidationException("Invalid parameter type found for second argument 'path' of " +
                        "json:getFloat() function, required " + Attribute.Type.STRING + ", but found " +
                        secondAttributeType.toString());
            }
        } else {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to json:getFloat() function, "
                    + "required 2, but found " + attributeExpressionExecutors.length);
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
        Object filteredJsonElement = null;
        Float returnValue;
        try {
            filteredJsonElement = JsonPath.read(jsonInput, path);
        } catch (PathNotFoundException e) {
            log.error("Cannot find json element for the path '" + path + "'. Hence returning the default value 'null'");
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
            returnValue = Float.parseFloat(filteredJsonElement.toString());
        } catch (NumberFormatException e) {
            returnValue = null;
            log.error("The value that is retrieved using the given path '" + path + "', is not a valid Float value. " +
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
        return Attribute.Type.FLOAT;
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
     * the element to the same state as if was on a previous point of time.
     *
     * @param state the stateful objects of the processing element as a map.
     *              This is the same map that is created upon calling currentState() method.
     */
    @Override
    public void restoreState(Map<String, Object> state) {

    }
}
