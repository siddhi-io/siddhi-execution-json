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
import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.executor.function.FunctionExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;
import io.siddhi.query.api.exception.SiddhiAppValidationException;
import org.apache.log4j.Logger;


/**
 * This class provides implementation for getting json string from the given object.
 */
@Extension(
        name = "toString",
        namespace = "json",
        description = "This method returns the JSON string corresponding to a given JSON object.",
        parameters = {
                @Parameter(
                        name = "json",
                        description = "A valid JSON object from which the function generates a JSON string.",
                        type = {DataType.OBJECT}),
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns the JSON string generated using the given JSON object.",
                type = {DataType.STRING}),
        examples = @Example(

                syntax = "define stream InputStream(json string);\n" +
                        "from InputStream\n" +
                        "select json:toString(json) as jsonString\n" +
                        "insert into OutputStream;",
                description = "This returns the JSON string corresponding to a given JSON object. The results are " +
                        "directed to the 'OutputStream' stream.")
)
public class ToJSONStringFunctionExtension extends FunctionExecutor {
    private static final Logger log = Logger.getLogger(ToJSONStringFunctionExtension.class);
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
    protected Object execute(Object data, State state) {
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
}
