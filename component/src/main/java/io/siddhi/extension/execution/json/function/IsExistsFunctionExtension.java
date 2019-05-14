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


/**
 * This class provides implementation for checking whether there is any json element in the given path or not.
 */
@Extension(
        name = "isExists",
        namespace = "json",
        description = "This method checks whether there is a JSON element present in the given path or not." +
                "If there is a valid JSON element in the given path, it returns 'true'. If there is no valid JSON " +
                "element, it returns 'false'",
        parameters = {
                @Parameter(
                        name = "json",
                        description = "The JSON input in a given path, on which the function performs the search for" +
                                "JSON elements.",
                        type = {DataType.STRING, DataType.OBJECT}),
                @Parameter(
                        name = "path",
                        description = "The path that contains the input JSON on which the function " +
                                "performs the search.",
                        type = {DataType.STRING})
        },
        returnAttributes = @ReturnAttribute(
                description = "If there is a valid JSON element in the given path, it returns 'true'. If there is " +
                        "no valid JSON element, it returns 'false'.",
                type = {DataType.BOOL}),
        examples = @Example(

                syntax = "define stream InputStream(json string);\n" +
                        "from InputStream\n" +
                        "select json:isExists(json,\"$.name\") as name\n" +
                        "insert into OutputStream;",
                description = "This returns either true or false based on the existence of a JSON element in a " +
                        "given path. The results are directed to the 'OutputStream' stream.")
)
public class IsExistsFunctionExtension extends FunctionExecutor {
    private static final Logger log = Logger.getLogger(IsExistsFunctionExtension.class);
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
                        "json:isExists() function. Input for 'json' argument cannot be null");
            }
            Attribute.Type inputJsonAttributeType = attributeExpressionExecutors[0].getReturnType();
            if (!(inputJsonAttributeType == Attribute.Type.STRING || inputJsonAttributeType == Attribute.Type.OBJECT)) {
                throw new SiddhiAppValidationException("Invalid parameter type found for first argument 'json' of " +
                        "json:isExists() function, required " + Attribute.Type.STRING + " or " + Attribute.Type
                        .OBJECT + ", but found " + inputJsonAttributeType.toString());
            }
            if (attributeExpressionExecutors[1] == null) {
                throw new SiddhiAppValidationException("Invalid input given to second argument 'path' of " +
                        "json:isExists() function. Input 'path' argument cannot be null");
            }
            Attribute.Type pathAttributeType = attributeExpressionExecutors[1].getReturnType();
            if (pathAttributeType != Attribute.Type.STRING) {
                throw new SiddhiAppValidationException("Invalid parameter type found for second argument 'path' of " +
                        "json:isExists() function, required " + Attribute.Type.STRING + ", but found " +
                        pathAttributeType.toString());
            }
        } else {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to json:insertIntoJson() function, "
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
        boolean isExists;
        try {
            JsonPath.read(jsonInput, path);
            isExists = true;
        } catch (PathNotFoundException e) {
            isExists = false;
        } catch (InvalidJsonException e) {
            throw new SiddhiAppRuntimeException("The input JSON is not a valid JSON. Input JSON - " + jsonInput, e);
        }
        return isExists;
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
        return Attribute.Type.BOOL;
    }

}
