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

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;

import java.util.concurrent.atomic.AtomicInteger;

public class InsertToJSONFunctionTestCase {
    private static final Logger log = Logger.getLogger(JsonTokenizerStreamProcessorFunctionTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private static final String JSON_INPUT = "{name:\"John\", married:true, citizen:false, subjects:[\"Mathematics\"]}";
    private static final String EXPECTED_JSON = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
            "\"subjects\":[\"Mathematics\"],\"key\":{\"key\":\"value\"}}";

    @BeforeMethod
    public void init() {
        count.set(0);
    }

    @Test
    public void testInsertTOJSONWithStringInput() throws InterruptedException, ParseException {
        log.info("InsertToJSONFunctionTestCase - testInsertTOJSONWithStringInput");
        String expectedJson2 = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\"],\"key\":{\"key\":\"value\",\"age\":\"25\",\"boo\":\"false\"}}";
        String expectedJson3 = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\"],\"key\":{\"key\":\"value\",\"age\":25.0,\"boo\":false}}";
        String expectedJson4 = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\"],\"key\":\"25\"}";
        String expectedJson5 = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\"],\"key\":\"false\"}";
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(json string,path string,jsonElement string,key string);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:setElement(json,path,jsonElement,key) as married\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        JSONParser jsonParser = new JSONParser();
        JSONObject expectedJsonObject1 = (JSONObject) jsonParser.parse(EXPECTED_JSON);
        JSONObject expectedJsonObject2 = (JSONObject) jsonParser.parse(expectedJson2);
        JSONObject expectedJsonObject3 = (JSONObject) jsonParser.parse(expectedJson3);
        JSONObject expectedJsonObject4 = (JSONObject) jsonParser.parse(expectedJson4);
        JSONObject expectedJsonObject5 = (JSONObject) jsonParser.parse(expectedJson5);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals(expectedJsonObject1, event.getData(0));
                            break;
                        case 2:
                            AssertJUnit.assertEquals(expectedJsonObject2, event.getData(0));
                            break;
                        case 3:
                            AssertJUnit.assertEquals(expectedJsonObject3, event.getData(0));
                            break;
                        case 4:
                            AssertJUnit.assertEquals(expectedJsonObject4, event.getData(0));
                            break;
                        case 5:
                            AssertJUnit.assertEquals(expectedJsonObject5, event.getData(0));
                            break;
                    }
                }
            }
        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{JSON_INPUT, "$", "{key:\"value\"}", "key"});
        inputHandler.send(new Object[]{JSON_INPUT, "$", "{key:\"value\",age:\"25\",boo:\"false\"}", "key"});
        inputHandler.send(new Object[]{JSON_INPUT, "$", "{key:\"value\",age:25,boo:false}", "key"});
        inputHandler.send(new Object[]{JSON_INPUT, "$", "25", "key"});
        inputHandler.send(new Object[]{JSON_INPUT, "$", "false", "key"});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testInsertTOJSONWithObjectInput() throws InterruptedException, ParseException {
        log.info("InsertToJSONFunctionTestCase - testInsertTOJSONWithObjectInput");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(json object,path string,jsonElement object,key string);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:setElement(json,path,jsonElement,key) as married\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        JSONParser jsonParser = new JSONParser();
        JSONObject expectedJsonObject = (JSONObject) jsonParser.parse(EXPECTED_JSON);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals(expectedJsonObject, event.getData(0));
                            break;
                    }
                }
            }
        });
        JSONObject inputJsonObject = (JSONObject) jsonParser.parse(JSON_INPUT);
        JSONObject jsonObjectToBeInserted = (JSONObject) jsonParser.parse("{key:\"value\"}");
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{inputJsonObject, "$", jsonObjectToBeInserted, "key"});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testModifyPropertyInJSONWithObjectInput() throws InterruptedException, ParseException {
        String expectedJson = "{\"name\":\"Peter\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\"]}";
        String expectedJson2 = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\",\"Biology\"]}";
        String expectedJson3 = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\",\"25\"]}";
        log.info("InsertToJSONFunctionTestCase - testModifyPropertyInJSONWithObjectInput");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(json object,path string,jsonElement object,key string);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:setElement(json,path,jsonElement,key) as married\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        JSONParser jsonParser = new JSONParser();
        JSONObject expectedJsonObject = (JSONObject) jsonParser.parse(expectedJson);
        JSONObject expectedJsonObject2 = (JSONObject) jsonParser.parse(expectedJson2);
        JSONObject expectedJsonObject3 = (JSONObject) jsonParser.parse(expectedJson3);
        JSONObject inputJsonObject = (JSONObject) jsonParser.parse(JSON_INPUT);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals(expectedJsonObject, event.getData(0));
                            break;
                        case 2:
                            AssertJUnit.assertEquals(expectedJsonObject2, event.getData(0));
                            break;
                        case 3:
                            AssertJUnit.assertEquals(inputJsonObject, event.getData(0));
                            break;
                        case 4:
                            AssertJUnit.assertEquals(inputJsonObject, event.getData(0));
                            break;
                        case 5:
                            AssertJUnit.assertEquals(expectedJsonObject3, event.getData(0));
                            break;
                    }
                }
            }
        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{inputJsonObject, "$", "Peter", "name"});
        inputHandler.send(new Object[]{inputJsonObject, "$.subjects", "Biology", ""});
        inputHandler.send(new Object[]{inputJsonObject, "$..name", "Peter", "name"});
        inputHandler.send(new Object[]{inputJsonObject, "$.xyz", "Peter", "name"});
        inputHandler.send(new Object[]{inputJsonObject, "$.subjects", "25", ""});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testModifyPropertyInJSONWithIntInput() throws InterruptedException, ParseException {
        String expectedJson = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\"],\"age\":25}";
        log.info("InsertToJSONFunctionTestCase - testModifyPropertyInJSONWithIntInput");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(json object,path string,jsonElement int,key string);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:setElement(json,path,jsonElement,key) as married\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        JSONParser jsonParser = new JSONParser();
        JSONObject expectedJsonObject = (JSONObject) jsonParser.parse(expectedJson);
        JSONObject inputJsonObject = (JSONObject) jsonParser.parse(JSON_INPUT);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals(expectedJsonObject, event.getData(0));
                            break;
                    }
                }
            }
        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{inputJsonObject, "$", 25, "age"});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testModifyPropertyInJSONWithDoubleInput() throws InterruptedException, ParseException {
        String expectedJson = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\"],\"age\":25.0}";
        log.info("InsertToJSONFunctionTestCase - testModifyPropertyInJSONWithObjectInput");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(json object,path string,jsonElement double,key string);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:setElement(json,path,jsonElement,key) as married\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        JSONParser jsonParser = new JSONParser();
        JSONObject expectedJsonObject = (JSONObject) jsonParser.parse(expectedJson);
        JSONObject inputJsonObject = (JSONObject) jsonParser.parse(JSON_INPUT);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals(expectedJsonObject, event.getData(0));
                            break;
                    }
                }
            }
        });
        double age = 25;
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{inputJsonObject, "$", age, "age"});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testModifyPropertyInJSONWithBoolInput() throws InterruptedException, ParseException {
        String expectedJson = "{\"name\":\"John\",\"married\":true,\"citizen\":false," +
                "\"subjects\":[\"Mathematics\"],\"age\":false}";
        log.info("InsertToJSONFunctionTestCase - testModifyPropertyInJSONWithObjectInput");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(json object,path string,jsonElement bool,key string);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:setElement(json,path,jsonElement,key) as married\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        JSONParser jsonParser = new JSONParser();
        JSONObject expectedJsonObject = (JSONObject) jsonParser.parse(expectedJson);
        JSONObject inputJsonObject = (JSONObject) jsonParser.parse(JSON_INPUT);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals(expectedJsonObject, event.getData(0));
                            break;
                    }
                }
            }
        });
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{inputJsonObject, "$", false, "age"});
        siddhiAppRuntime.shutdown();
    }
}
