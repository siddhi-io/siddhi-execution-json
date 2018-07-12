Siddhi Execution Extension - Json
======================================

The **siddhi-execution-json extension** is an extension to <a target="_blank" href="https://wso2.github.io/siddhi">Siddhi</a> that provides a rich set of capabilities to retrieve data from JSONs and inserting/modifying elements in JSONs using Siddhi streams. Please find the high-level overview of the available functions.

* tokenize() and tokenizeAsObject – These methods will tokenize the given JSON with a specified path. If the specified path provides JSON array, it will generate events for each and every elements in JSON array by adding these elements as a additional attribute ‘jsonElement’.

* getBool, getInt, getFloat, getDouble, getLong, getString, getObject – These methods can be used to retrieved data from a JSON based on a given path in desired format. Users can select a method according to their preference and get the response in desired format by passing the JSON and path.

* toString and toObject – These methods can be used to convert given JSON into an object or string type.

* setElement – This method is used to insert/modify the JSONs by providing the path and the new value. Users can insert new JSON elements, modify existing JSON elements and add JSON elements into existing JSON arrays using this function.

Find some useful links below:

* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-json">Source code</a>
* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-json/releases">Releases</a>
* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-json/issues">Issue tracker</a>

## Latest API Docs 


## How to use 

**Using the extension in <a target="_blank" href="https://github.com/wso2/product-sp">WSO2 Stream Processor</a>**

* You can use this extension in the latest <a target="_blank" href="https://github.com/wso2/product-sp/releases">WSO2 Stream Processor</a> that is a part of <a target="_blank" href="http://wso2.com/analytics?utm_source=gitanalytics&utm_campaign=gitanalytics_Jul17">WSO2 Analytics</a> offering, with editor, debugger and simulation support. 

* This extension is shipped by default with WSO2 Stream Processor, if you wish to use an alternative version of this extension you can replace the component <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-json/releases">jar</a> that can be found in the `<STREAM_PROCESSOR_HOME>/lib` 
directory.

**Using the extension as a <a target="_blank" href="https://wso2.github.io/siddhi/documentation/running-as-a-java-library">java library</a>**

* This extension can be added as a maven dependency along with other Siddhi dependencies to your project.

```
     <dependency>
        <groupId>org.wso2.extension.siddhi.execution.json</groupId>
        <artifactId>siddhi-execution-json</artifactId>
        <version>x.x.x</version>
     </dependency>
```

## Jenkins Build Status

---

|  Branch | Build Status |
| :------ |:------------ | 
| master  | [![Build Status](https://wso2.org/jenkins/view/All%20Builds/job/siddhi/job/siddhi-execution-json/badge/icon)](https://wso2.org/jenkins/view/All%20Builds/job/siddhi/job/siddhi-execution-json/) |

---

## Features

## How to Contribute
 
  * Please report issues at <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-json/issues">GitHub Issue Tracker</a>.
  
  * Send your contributions as pull requests to <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-json/tree/master">master branch</a>. 
 
## Contact us 

 * Post your questions with the <a target="_blank" href="http://stackoverflow.com/search?q=siddhi">"Siddhi"</a> tag in <a target="_blank" href="http://stackoverflow.com/search?q=siddhi">Stackoverflow</a>. 
 
 * Siddhi developers can be contacted via the mailing lists:
 
    Developers List   : [dev@wso2.org](mailto:dev@wso2.org)
    
    Architecture List : [architecture@wso2.org](mailto:architecture@wso2.org)
 
## Support 

* We are committed to ensuring support for this extension in production. Our unique approach ensures that all support leverages our open development methodology and is provided by the very same engineers who build the technology. 

* For more details and to take advantage of this unique opportunity contact us via <a target="_blank" href="http://wso2.com/support?utm_source=gitanalytics&utm_campaign=gitanalytics_Jul17">http://wso2.com/support/</a>.
