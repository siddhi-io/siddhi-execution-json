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

package io.siddhi.extension.execution.json;

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
import io.siddhi.core.event.ComplexEventChunk;
import io.siddhi.core.event.stream.MetaStreamEvent;
import io.siddhi.core.event.stream.StreamEvent;
import io.siddhi.core.event.stream.StreamEventCloner;
import io.siddhi.core.event.stream.holder.StreamEventClonerHolder;
import io.siddhi.core.event.stream.populater.ComplexEventPopulater;
import io.siddhi.core.exception.SiddhiAppRuntimeException;
import io.siddhi.core.executor.ConstantExpressionExecutor;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.query.processor.ProcessingMode;
import io.siddhi.core.query.processor.Processor;
import io.siddhi.core.query.processor.stream.StreamProcessor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.AbstractDefinition;
import io.siddhi.query.api.definition.Attribute;
import io.siddhi.query.api.exception.SiddhiAppValidationException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides implementation for tokenizing the given json based on a specific path.
 */
@Extension(
        name = "tokenizeAsObject",
        namespace = "json",
        description = "Stream processor tokenizes the given JSON into to multiple JSON object elements and " +
                "sends them as separate events.",
        parameters = {
                @Parameter(
                        name = "json",
                        description = "The input JSON that needs to be tokenized.",
                        type = {DataType.STRING, DataType.OBJECT},
                        dynamic = true),
                @Parameter(
                        name = "path",
                        description = "The path of the set of elements that will be tokenized.",
                        type = {DataType.STRING},
                        dynamic = true),
                @Parameter(
                        name = "fail.on.missing.attribute",
                        description = "If there are no element on the given path, when set to `true` the system " +
                                "will drop the event, and when set to `false` the system will pass 'null' value to " +
                                "the jsonElement output attribute.",
                        type = {DataType.BOOL},
                        optional = true,
                        defaultValue = "true")
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"json", "path"}),
                @ParameterOverload(parameterNames = {"json", "path", "fail.on.missing.attribute"})
        },
        returnAttributes = {
                @ReturnAttribute(
                        name = "jsonElement",
                        description = "The JSON element retrieved based on the given path will be returned " +
                                "as a JSON object. If the 'path' selects a JSON array then the system returns each " +
                                "element in the array as a JSON object via a separate events.",
                        type = {DataType.OBJECT})},
        examples = {
                @Example(
                        syntax = "define stream InputStream (json string, path string);\n\n" +
                                "@info(name = 'query1')\n" +
                                "from InputStream#json:tokenizeAsObject(json, path)\n" +
                                "select path, jsonElement\n" +
                                "insert into OutputStream;",
                        description = "If the input 'json' is `{name:'John', enrolledSubjects:['Mathematics'," +
                                " 'Physics']}`, and the 'path' is passed as `$.enrolledSubjects` then for both the " +
                                "elements in the selected JSON array, it generates it generates events as " +
                                "`('$.enrolledSubjects', 'Mathematics')`, and " +
                                "`('$.enrolledSubjects', 'Physics')`.\n" +
                                "For the same input JSON, if the 'path' is passed as `$.name` then it will only " +
                                "produce one event `('$.name', 'John')` as the 'path' provided a single JSON element."
                ),
                @Example(
                        syntax = "define stream InputStream (json string, path string);\n\n" +
                                "@info(name = 'query1')\n" +
                                "from InputStream#json:tokenizeAsObject(json, path, true)\n" +
                                "select path, jsonElement\n" +
                                "insert into OutputStream;",
                        description = "If the input 'json' is `{name:'John', age:25}`," +
                                "and the 'path' is passed as `$.salary` then the system will produce " +
                                "`('$.salary', null)`, as the 'fail.on.missing.attribute' is `true` and there are " +
                                "no matching element for `$.salary`."
                )
        }
)
public class JsonTokenizerAsObjectStreamProcessorFunction extends StreamProcessor<State> {
    private static final Logger log = Logger.getLogger(JsonTokenizerAsObjectStreamProcessorFunction.class);
    private boolean failOnMissingAttribute = true;

    @Override
    protected void process(ComplexEventChunk<StreamEvent> streamEventChunk, Processor nextProcessor,
                           StreamEventCloner streamEventCloner, ComplexEventPopulater complexEventPopulater,
                           State state) {
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
                log.warn(siddhiQueryContext.getSiddhiAppContext().getName() + ":" + siddhiQueryContext.getName() +
                        ": Cannot find json element for the path '" + path + "' in the input json : " + jsonInput);
            } catch (InvalidJsonException e) {
                throw new SiddhiAppRuntimeException("The input JSON is not a valid JSON. Input JSON - " + jsonInput, e);
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
     * @param metaStreamEvent            the  stream event meta
     * @param abstractDefinition         the incoming stream definition
     * @param expressionExecutors        the executors for the function parameters
     * @param configReader               this hold the Stream Processor configuration reader.
     * @param streamEventClonerHolder    streamEventCloner Holder
     * @param outputExpectsExpiredEvents whether output can be expired events
     * @param findToBeExecuted           find will be executed
     * @param siddhiQueryContext         current siddhi query context
     */
    @Override
    protected StateFactory<State> init(MetaStreamEvent metaStreamEvent, AbstractDefinition abstractDefinition,
                                       ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                                       StreamEventClonerHolder streamEventClonerHolder,
                                       boolean outputExpectsExpiredEvents, boolean findToBeExecuted,
                                       SiddhiQueryContext siddhiQueryContext) {
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

        return null;
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


    private void sendEvents(StreamEvent streamEvent, Object[] data, ComplexEventChunk<StreamEvent> streamEventChunk) {
        complexEventPopulater.populateComplexEvent(streamEvent, data);
        nextProcessor.process(streamEventChunk);
    }

    @Override
    public List<Attribute> getReturnAttributes() {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("jsonElement", Attribute.Type.OBJECT));
        return attributes;
    }

    @Override
    public ProcessingMode getProcessingMode() {
        return ProcessingMode.BATCH;
    }
}
