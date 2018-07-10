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

import java.util.Map;


/**
 * This class provides implementation for getting json string from the given object.
 */
@Extension(
        name = "toString",
        namespace = "json",
        description = "This method will return the json string related to given json object.",
        parameters = {
                @Parameter(
                        name = "json",
                        description = "A valid json object which is used to generate the json string",
                        type = {DataType.OBJECT}),
        },
        returnAttributes = @ReturnAttribute(
                description = "returns the json string generated using the given json object",
                type = {DataType.STRING}),
        examples = @Example(
                description = "This will return a json string related to given json object",
                syntax = "define stream InputStream(json string);\n" +
                        "from IpStream\n" +
                        "select json:toString(json) as jsonString\n" +
                        "insert into OutputStream;")
)
public class ToJSONStringFunctionExtension extends FunctionExecutor {
    private static final Logger log = Logger.getLogger(ToJSONStringFunctionExtension.class);
    private static final Gson gson = new Gson();

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


        if (attributeExpressionExecutors.length != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to json:toString() function, "
                    + "required 1, but found " + attributeExpressionExecutors.length);
        }

        if (attributeExpressionExecutors[0] == null) {
            throw new SiddhiAppValidationException("Invalid input given to first argument 'json' of " +
                    "json:toString() function. Input for 'json' argument cannot be null");
        }
        Attribute.Type firstAttributeType = attributeExpressionExecutors[0].getReturnType();
        if (!(firstAttributeType == Attribute.Type.OBJECT)) {
            throw new SiddhiAppValidationException("Invalid parameter type found for first argument 'json' of " +
                    "json:toString() function, required " + Attribute.Type.OBJECT + ", but found " + firstAttributeType
                    .toString());
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
        return null;
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
        return gson.toJson(data);
    }

    /**
     * return a Class object that represents the formal return type of the method represented by this Method object.
     *
     * @return the return type for the method this object represents
     */
    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.STRING;
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
