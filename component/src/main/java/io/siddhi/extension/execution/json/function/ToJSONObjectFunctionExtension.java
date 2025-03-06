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

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ParameterOverload;
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
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class provides implementation for getting json object from the given json string.
 */
@Extension(
        name = "toObject",
        namespace = "json",
        description = "Function generate JSON object from the given JSON string.",
        parameters = {
                @Parameter(
                        name = "json",
                        description = "A valid JSON string that needs to be converted to a JSON object.",
                        type = {DataType.STRING},
                        dynamic = true),
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"json"})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns the JSON object generated using the given JSON string.",
                type = {DataType.OBJECT}),
        examples = @Example(
                syntax = "json:toJson(json)",
                description = "This returns the JSON object corresponding to the given JSON string."
        )
)
public class ToJSONObjectFunctionExtension extends FunctionExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger(ToJSONObjectFunctionExtension.class);
    private static final JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);

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
            throw new SiddhiAppValidationException("Invalid no of arguments passed to json:toJson() function, "
                    + "required 1, but found " + attributeExpressionExecutors.length);
        }

        if (attributeExpressionExecutors[0] == null) {
            throw new SiddhiAppValidationException("Invalid input given to first argument 'json' of " +
                    "json:toJson() function. Input for 'json' argument cannot be null");
        }
        Attribute.Type firstAttributeType = attributeExpressionExecutors[0].getReturnType();
        if (!(firstAttributeType == Attribute.Type.STRING)) {
            throw new SiddhiAppValidationException("Invalid parameter type found for first argument 'json' of " +
                    "json:toJson() function, required " + Attribute.Type.STRING + ", but found " + firstAttributeType
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
        Object returnValue = null;
        try {
            returnValue = jsonParser.parse(data.toString());
        } catch (ParseException e) {
            log.error("{}:{}: Cannot parse the given string into JSON. Hence returning null",
                    siddhiQueryContext.getSiddhiAppContext().getName(), siddhiQueryContext.getName());
        }
        return returnValue;
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

