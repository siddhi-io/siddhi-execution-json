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

public class ToJSONFunctionTestCase {
    private static final Logger log = Logger.getLogger(ToJSONFunctionTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private static final String JSON_INPUT = "{name:\"John\", age:25, citizen:false, " +
            "bar:[{barName:\"barName\"},{barName:\"barName2\"}]}";

    @BeforeMethod
    public void init() {
        count.set(0);
    }

    @Test
    public void testToJSONObjectFunction() throws InterruptedException, ParseException {
        log.info("ToJSONFunctionTestCase - testToJSONObjectFunction");
        SiddhiManager siddhiManager = new SiddhiManager();
        String stream = "define stream InputStream(json string);\n";
        String query = ("@info(name = 'query1')\n" +
                "from InputStream\n" +
                "select json:toObject(json) as json\n" +
                "insert into OutputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(stream + query);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(JSON_INPUT);
        siddhiAppRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents,
                                Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    switch (count.get()) {
                        case 1:
                            AssertJUnit.assertEquals(jsonObject, event.getData(0));
                            break;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("InputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{JSON_INPUT});
        siddhiAppRuntime.shutdown();
    }
}
