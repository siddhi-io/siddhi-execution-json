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

package org.wso2.extension.siddhi.execution.json;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.log4j.Logger;
import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.event.ComplexEventChunk;
import org.wso2.siddhi.core.event.stream.StreamEvent;
import org.wso2.siddhi.core.event.stream.StreamEventCloner;
import org.wso2.siddhi.core.event.stream.populater.ComplexEventPopulater;
import org.wso2.siddhi.core.executor.ConstantExpressionExecutor;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.query.processor.Processor;
import org.wso2.siddhi.core.query.processor.stream.StreamProcessor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.AbstractDefinition;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides implementation for tokenizing the given json based on a specific path.
 */
@Extension(
        name = "tokenizeAsObject",
        namespace = "json",
        description = "This will tokenize the given json according the path provided and return the response as object",
        parameters = {
                @Parameter(
                        name = "json",
                        description = "The input json which should be tokenized using the given path.",
                        type = {DataType.STRING, DataType.OBJECT}),
                @Parameter(
                        name = "path",
                        description = "The path which is used to tokenize the given json",
                        type = {DataType.STRING}),
                @Parameter(
                        name = "fail.on.missing.attribute",
                        description = "This can either have value true or false. By default it will be true. If the " +
                                "specified path does not provide any json, it will return null. In this scenario " +
                                "users can drop or keep the event with 'null' value using this attribute. If this " +
                                "parameter is 'true', it will generate events with the 'null' value",
                        type = {DataType.BOOL},
                        optional = true,
                        defaultValue = "true")
        },
        returnAttributes = {
                @ReturnAttribute(
                        name = "jsonElement",
                        description = "The json element retrieved based on the given path and the json",
                        type = {DataType.OBJECT})},
        examples = @Example(
                syntax = "define stream InputStream (json string,path string);\n" +
                        "@info(name = 'query1')\n" +
                        "from InputStream#json:tokenizeAsObject(json, path)\n" +
                        "select jsonElement\n" +
                        "insert into OutputStream;",
                description = "This query performs tokenization for the given json using the path specified. If the " +
                        "specified path provides json array, it will generate events for each elements in specified " +
                        "json array by adding additional attribute as the 'jsonElement' into the stream\n`" +
                        "Eg:-\n jsonInput - {name:\"John\",enrolledSubjects:[\"Mathematics\",\"Physics\"]}, \n path -" +
                        " \"$.enrolledSubjects\"\n`\nIt we use configuration like above example, it will generate " +
                        "two events with the attributes \"Mathematics\", \"Physics\".\nIf the specified path provides" +
                        " a single json element, it will add the specified json element as a additional attribute " +
                        "named 'jsonElement' into the stream \n`\n Eg:-\n jsonInput - {name:\"John\",age:25}, \n " +
                        "path - \"$.age\"\n`\n")
)
public class JsonTokenizerAsObjectStreamProcessorFunction extends StreamProcessor {
    private static final Logger log = Logger.getLogger(JsonTokenizerAsObjectStreamProcessorFunction.class);
    private boolean failOnMissingAttribute = true;

    @Override
    protected void process(ComplexEventChunk<StreamEvent> streamEventChunk, Processor nextProcessor,
                           StreamEventCloner streamEventCloner, ComplexEventPopulater complexEventPopulater) {
        while (streamEventChunk.hasNext()) {
            StreamEvent streamEvent = streamEventChunk.next();
            Object jsonInput = attributeExpressionExecutors[0].execute(streamEvent);
            String path = (String) attributeExpressionExecutors[1].execute(streamEvent);
            Object filteredJsonElements;
            try {
                if (jsonInput instanceof String) {
                    filteredJsonElements = JsonPath.read((String) jsonInput, path);
                } else {
                    filteredJsonElements = JsonPath.read(jsonInput, path);
                }
            } catch (PathNotFoundException e) {
                filteredJsonElements = null;
                log.warn("Cannot find json element for the path '" + path + "' in the input json : " + jsonInput);
            }
            if (filteredJsonElements instanceof List) {
                List filteredJsonElementsList = (List) filteredJsonElements;
                if (((List) filteredJsonElements).size() == 0 && !failOnMissingAttribute) {
                    Object[] data = {null};
                    sendEvents(streamEvent, data, streamEventChunk);
                }
                for (Object filteredJsonElement : filteredJsonElementsList) {
                    Object[] data = {filteredJsonElement};
                    sendEvents(streamEvent, data, streamEventChunk);
                }
            } else if (filteredJsonElements instanceof Map) {
                Object[] data = {filteredJsonElements};
                sendEvents(streamEvent, data, streamEventChunk);
            } else if (filteredJsonElements instanceof String || filteredJsonElements == null) {
                if (!failOnMissingAttribute || filteredJsonElements != null) {
                    Object[] data = {filteredJsonElements};
                    sendEvents(streamEvent, data, streamEventChunk);
                }
            }
        }
    }

    /**
     * The initialization method for {@link StreamProcessor}, which will be called before other methods and validate
     * the all configuration and getting the initial values.
     *
     * @param attributeExpressionExecutors are the executors of each attributes in the Function
     * @param configReader                 this hold the {@link StreamProcessor} extensions configuration reader.
     * @param siddhiAppContext             Siddhi app runtime context
     */
    @Override
    protected List<Attribute> init(AbstractDefinition inputDefinition,
                                   ExpressionExecutor[] attributeExpressionExecutors, ConfigReader configReader,
                                   SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length == 2 || attributeExpressionExecutors.length == 3) {
            if (attributeExpressionExecutors[0] == null) {
                throw new SiddhiAppValidationException("Invalid input given to first argument 'json' of " +
                        "json:tokenizeAsObject() function. Input for 'json' argument cannot be null");
            }
            Attribute.Type firstAttributeType = attributeExpressionExecutors[0].getReturnType();
            if (!(firstAttributeType == Attribute.Type.STRING || firstAttributeType == Attribute.Type.OBJECT)) {
                throw new SiddhiAppValidationException("Invalid parameter type found for first argument 'json' of " +
                        "json:tokenizeAsObject() function, required " + Attribute.Type.STRING + " or " + Attribute.Type
                        .OBJECT + ", but found " + firstAttributeType.toString());
            }
            if (attributeExpressionExecutors[1] == null) {
                throw new SiddhiAppValidationException("Invalid input given to second argument 'path' of " +
                        "json:tokenizeAsObject() function. Input 'path' argument cannot be null");
            }
            Attribute.Type secondAttributeType = attributeExpressionExecutors[1].getReturnType();
            if (secondAttributeType != Attribute.Type.STRING) {
                throw new SiddhiAppValidationException("Invalid parameter type found for second argument 'path' of " +
                        "json:tokenizeAsObject() function, required " + Attribute.Type.STRING + ", but found " +
                        secondAttributeType.toString());
            }
            if (attributeExpressionExecutors.length == 3) {
                if (attributeExpressionExecutors[2].getReturnType() == Attribute.Type.BOOL) {
                    this.failOnMissingAttribute = (Boolean) ((ConstantExpressionExecutor)
                            attributeExpressionExecutors[2]).getValue();
                } else {
                    throw new SiddhiAppValidationException("Invalid parameter type found for second argument 'path' " +
                            "of json:tokenizeAsObject() function, required " + Attribute.Type.BOOL + ", but found " +
                            attributeExpressionExecutors[2].getReturnType());
                }
            }
        } else {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to json:tokenizeAsObject() function,"
                    + "required 2, but found " + attributeExpressionExecutors.length);
        }
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("jsonElement", Attribute.Type.OBJECT));
        return attributes;
    }

    /**
     * This will be called only once and this can be used to acquire
     * required resources for the processing element.
     * This will be called after initializing the system and before
     * starting to process the events.
     */
    @Override
    public void start() {
    }

    /**
     * This will be called only once and this can be used to release
     * the acquired resources for processing.
     * This will be called before shutting down the system.
     */
    @Override
    public void stop() {
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

    private void sendEvents(StreamEvent streamEvent, Object[] data, ComplexEventChunk<StreamEvent> streamEventChunk) {
        complexEventPopulater.populateComplexEvent(streamEvent, data);
        nextProcessor.process(streamEventChunk);
    }
}
