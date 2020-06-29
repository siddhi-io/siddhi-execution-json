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

import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.event.Event;
import io.siddhi.core.query.output.callback.QueryCallback;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.util.EventPrinter;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ToStringFunctionTestCase {
    private static final Logger log = Logger.getLogger(ToStringFunctionTestCase.class);
    private static final String JSON_INPUT = "{name:\"John\", age:25, citizen:false, " +
            "bar:[{barName:\"barName\"},{barName:\"barName2\"}]}";
    private AtomicInteger count = new AtomicInteger(0);

    @BeforeMethod
    public void init() {
        count.set(0);
    }

    @Test
    public void testToStringFunction() throws InterruptedException, ParseException {
        log.info("ToStringFunctionTestCase - testToStringFunction");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(json object);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:toString(json) as json\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals("{\"citizen\":false,\"bar\":[{\"barName\":\"barName\"}," +
                                    "{\"barName\":\"barName2\"}],\"name\":\"John\",\"age\":25}", event.getData(0));
                            break;
                    }
                }
            }
        });

        JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(JSON_INPUT);
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{jsonObject});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testToStringFunctionWithAllowEscapeTrueSimple() throws InterruptedException, ParseException {
        log.info("ToStringFunctionTestCase - testToStringFunctionWithAllowEscapeTrueSimple");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(data object, allowEscape bool);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:toString(data, allowEscape) as json\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals("\"{\\\"user\\\":\\\"david\\\"}\"", event.getData(0));
                            break;
                    }
                }
            }
        });

        JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONObject jsonObject = (JSONObject) jsonParser.parse("{ \"user\":\"david\"}");
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{jsonObject, true});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testToStringFunctionWithAllowEscapeFalseSimple() throws InterruptedException, ParseException {
        log.info("ToStringFunctionTestCase - testToStringFunctionWithAllowEscapeFalseSimple");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(data object, allowEscape bool);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:toString(data, allowEscape) as json\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals("{\"user\":\"david\"}", event.getData(0));
                            break;
                    }
                }
            }
        });

        JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONObject jsonObject = (JSONObject) jsonParser.parse("{ \"user\":\"david\"}");
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{jsonObject, false});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testToStringFunctionWithAllowEscape() throws InterruptedException, ParseException {
        log.info("ToStringFunctionTestCase - testToStringFunctionWithAllowEscape");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(data object, allowEscape bool);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:toString(data, allowEscape) as json\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals("\"{\\\"citizen\\\":false,\\\"bar\\\":[{\\\"barName\\\":\\\"barName\\\"},{\\\"barName\\\":\\\"barName2\\\"}],\\\"name\\\":\\\"John\\\",\\\"age\\\":25}\"", event.getData(0));
                            break;
                    }
                }
            }
        });

        JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(JSON_INPUT);
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{jsonObject, true});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testToStringFunctionWithAllowEscapeTrue() throws InterruptedException, ParseException {
        log.info("ToStringFunctionTestCase - testToStringFunctionWithAllowEscapeTrue");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(data string, allowEscape bool);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:toString(json:toObject(data), allowEscape) as json\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals("\"{\\\"accounting\\\":[{\\\"firstName\\\":\\\"John\\\",\\\"lastName\\\":\\\"Doe\\\",\\\"age\\\":23},{\\\"firstName\\\":\\\"Mary\\\",\\\"lastName\\\":\\\"Smith\\\",\\\"age\\\":32}],\\\"sales\\\":[{\\\"firstName\\\":\\\"Sally\\\",\\\"lastName\\\":\\\"Green\\\",\\\"age\\\":27},{\\\"firstName\\\":\\\"Jim\\\",\\\"lastName\\\":\\\"Galley\\\",\\\"age\\\":41}]}\"", event.getData(0));
                            break;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"{ \n" +
                "  \"accounting\" : [   \n" +
                "                     { \"firstName\" : \"John\",  \n" +
                "                       \"lastName\"  : \"Doe\",\n" +
                "                       \"age\"       : 23 },\n" +
                "\n" +
                "                     { \"firstName\" : \"Mary\",  \n" +
                "                       \"lastName\"  : \"Smith\",\n" +
                "                        \"age\"      : 32 }\n" +
                "                 ],                            \n" +
                "  \"sales\"      : [ \n" +
                "                     { \"firstName\" : \"Sally\", \n" +
                "                       \"lastName\"  : \"Green\",\n" +
                "                        \"age\"      : 27 },\n" +
                "\n" +
                "                     { \"firstName\" : \"Jim\",   \n" +
                "                       \"lastName\"  : \"Galley\",\n" +
                "                       \"age\"       : 41 }\n" +
                "                 ] \n" +
                "}", true});
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testToStringFunctionWithAllowEscapeFalse() throws InterruptedException, ParseException {
        log.info("ToStringFunctionTestCase - testToStringFunctionWithAllowEscapeFalse");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(data string, allowEscape bool);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:toString(json:toObject(data), allowEscape) as json\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals("{\"accounting\":[{\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":23},{\"firstName\":\"Mary\",\"lastName\":\"Smith\",\"age\":32}],\"sales\":[{\"firstName\":\"Sally\",\"lastName\":\"Green\",\"age\":27},{\"firstName\":\"Jim\",\"lastName\":\"Galley\",\"age\":41}]}", event.getData(0));
                            break;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"{ \n" +
                "  \"accounting\" : [   \n" +
                "                     { \"firstName\" : \"John\",  \n" +
                "                       \"lastName\"  : \"Doe\",\n" +
                "                       \"age\"       : 23 },\n" +
                "\n" +
                "                     { \"firstName\" : \"Mary\",  \n" +
                "                       \"lastName\"  : \"Smith\",\n" +
                "                        \"age\"      : 32 }\n" +
                "                 ],                            \n" +
                "  \"sales\"      : [ \n" +
                "                     { \"firstName\" : \"Sally\", \n" +
                "                       \"lastName\"  : \"Green\",\n" +
                "                        \"age\"      : 27 },\n" +
                "\n" +
                "                     { \"firstName\" : \"Jim\",   \n" +
                "                       \"lastName\"  : \"Galley\",\n" +
                "                       \"age\"       : 41 }\n" +
                "                 ] \n" +
                "}", false});
        siddhiAppRuntime.shutdown();
    }
}
