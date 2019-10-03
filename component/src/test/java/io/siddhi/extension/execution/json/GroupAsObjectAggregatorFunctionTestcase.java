/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.siddhi.extension.execution.json;

import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.event.Event;
import io.siddhi.core.query.output.callback.QueryCallback;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.core.util.SiddhiTestHelper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class GroupAsObjectAggregatorFunctionTestcase {

    private static final Logger LOGGER = Logger.getLogger(GroupAsObjectAggregatorFunctionTestcase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean eventArrived;

    @BeforeMethod
    public void init() {
        count.set(0);
        eventArrived = false;
    }

    @Test
    public void testAggregateFunctionExtension1() throws InterruptedException {
        LOGGER.info("TestAggregateFunctionExtension1 TestCase - JSON as String Input");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream inputStream (symbol1 string, symbol2 string, symbol3 string);";
        String query = ("@info(name = 'query1') " +
                "from inputStream " +
                "select json:groupAsObject(symbol1) as concatJSON " +
                "insert into outputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        AssertJUnit.assertEquals(jsonArray, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);
                        AssertJUnit.assertEquals(jsonArray, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");

                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("date", "2013-11-19");
                        jsonObject2.put("time", "12:20");
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);
                        jsonArray.add(jsonObject2);
                        AssertJUnit.assertEquals(jsonArray, event.getData(0));
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{null});
        inputHandler.send(new Object[]{"{\"date\":\"2013-11-19\",\"time\":\"10:30\"}"});
        inputHandler.send(new Object[]{"{\"date\":\"2013-11-19\",\"time\":\"12:20\"}"});
        SiddhiTestHelper.waitForEvents(100, 3, count, 60000);
        AssertJUnit.assertEquals(3, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test(dependsOnMethods = {"testAggregateFunctionExtension1"})
    public void testAggregateFunctionExtension2() throws InterruptedException {
        LOGGER.info("TestAggregateFunctionExtension2 TestCase - JSON as String Input");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream inputStream (symbol1 string, symbol2 string, symbol3 string);";
        String query = ("@info(name = 'query1') " +
                "from inputStream " +
                "select json:groupAsObject(symbol1) as concatJSON " +
                "insert into outputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        AssertJUnit.assertEquals(jsonArray, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);
                        AssertJUnit.assertEquals(jsonArray, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");

                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("date", "2013-11-19");
                        jsonObject2.put("time", "12:20");
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);
                        jsonArray.add(jsonObject2);
                        AssertJUnit.assertEquals(jsonArray, event.getData(0));
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("date", "2013-11-19");
        jsonObject1.put("time", "10:30");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("date", "2013-11-19");
        jsonObject2.put("time", "12:20");

        inputHandler.send(new Object[]{null});
        inputHandler.send(new Object[]{jsonObject1});
        inputHandler.send(new Object[]{jsonObject2});
        SiddhiTestHelper.waitForEvents(100, 3, count, 60000);
        AssertJUnit.assertEquals(3, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test(dependsOnMethods = {"testAggregateFunctionExtension2"})
    public void testAggregateFunctionExtension3() throws InterruptedException {
        LOGGER.info("TestAggregateFunctionExtension3 TestCase - JSON enclosing element");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream inputStream (symbol1 string, symbol2 string, symbol3 string);";
        String query = ("@info(name = 'query1') " +
                "from inputStream#window.length(3) " +
                "select json:groupAsObject(symbol1, 'result') as concatJSON " +
                "insert into outputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");

                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("date", "2013-11-19");
                        jsonObject2.put("time", "12:20");
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);
                        jsonArray.add(jsonObject2);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{null});
        inputHandler.send(new Object[]{"{\"date\":\"2013-11-19\",\"time\":\"10:30\"}"});
        inputHandler.send(new Object[]{"{\"date\":\"2013-11-19\",\"time\":\"12:20\"}"});
        SiddhiTestHelper.waitForEvents(100, 3, count, 60000);
        AssertJUnit.assertEquals(3, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test(dependsOnMethods = {"testAggregateFunctionExtension3"})
    public void testAggregateFunctionExtension4() throws InterruptedException {
        LOGGER.info("TestAggregateFunctionExtension4 TestCase - Distinct");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream inputStream (symbol1 string, symbol2 string, symbol3 string);";
        String query = ("@info(name = 'query1') " +
                "from inputStream#window.length(3) " +
                "select json:groupAsObject(symbol1, true) as concatJSON " +
                "insert into outputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        AssertJUnit.assertEquals(jsonArray, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);
                        AssertJUnit.assertEquals(jsonArray, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);
                        AssertJUnit.assertEquals(jsonArray, event.getData(0));
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{null});
        inputHandler.send(new Object[]{"{\"date\":\"2013-11-19\",\"time\":\"10:30\"}"});
        inputHandler.send(new Object[]{"{\"date\":\"2013-11-19\",\"time\":\"10:30\"}"});
        SiddhiTestHelper.waitForEvents(100, 3, count, 60000);
        AssertJUnit.assertEquals(3, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test(dependsOnMethods = {"testAggregateFunctionExtension4"})
    public void testAggregateFunctionExtension5() throws InterruptedException {
        LOGGER.info("TestAggregateFunctionExtension5 TestCase - Enclosing element & distinct");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream inputStream (symbol1 string, symbol2 string, symbol3 string);";
        String query = ("@info(name = 'query1') " +
                "from inputStream " +
                "select json:groupAsObject(symbol1, 'result', true) as concatJSON " +
                "insert into outputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");

                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");

                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("date", "2013-11-19");
        jsonObject1.put("time", "10:30");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("date", "2013-11-19");
        jsonObject2.put("time", "10:30");

        inputHandler.send(new Object[]{null});
        inputHandler.send(new Object[]{jsonObject1});
        inputHandler.send(new Object[]{jsonObject2});
        SiddhiTestHelper.waitForEvents(100, 3, count, 60000);
        AssertJUnit.assertEquals(3, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test(dependsOnMethods = {"testAggregateFunctionExtension5"})
    public void testAggregateFunctionExtension6() throws InterruptedException {
        LOGGER.info("TestAggregateFunctionExtension5 TestCase - with window expiry");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream inputStream (symbol1 string, symbol2 string, symbol3 string);";
        String query = ("@info(name = 'query1') " +
                "from inputStream#window.length(2) " +
                "select json:groupAsObject(symbol1, 'result', true) as concatJSON " +
                "insert into outputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);

        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");

                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(null);
                        jsonArray.add(jsonObject1);

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");

                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(jsonObject1);

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }

                    if (count.get() == 4) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("date", "2013-11-19");
                        jsonObject1.put("time", "10:30");

                        JSONObject jsonObject3 = new JSONObject();
                        jsonObject3.put("date", "2013-11-19");
                        jsonObject3.put("time", "12:20");

                        JSONArray jsonArray = new JSONArray();
                        jsonArray.add(jsonObject1);
                        jsonArray.add(jsonObject3);

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", jsonArray);
                        AssertJUnit.assertEquals(jsonObject, event.getData(0));
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("date", "2013-11-19");
        jsonObject1.put("time", "10:30");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("date", "2013-11-19");
        jsonObject2.put("time", "10:30");

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("date", "2013-11-19");
        jsonObject3.put("time", "12:20");

        inputHandler.send(new Object[]{null});
        inputHandler.send(new Object[]{jsonObject1});
        inputHandler.send(new Object[]{jsonObject2});
        inputHandler.send(new Object[]{jsonObject3});
        SiddhiTestHelper.waitForEvents(100, 4, count, 60000);
        AssertJUnit.assertEquals(4, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }
}
