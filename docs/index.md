Siddhi Execution Json Extension
======================================

The **siddhi-execution-json extension** is an extension to <a target="_blank" href="https://wso2.github.io/siddhi">Siddhi</a> that provides a rich set of capabilities to retrieve data from JSONs and inserting/modifying elements in JSONs using Siddhi streams.

Find some useful links below:

* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-json">Source code</a>
* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-json/releases">Releases</a>
* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-json/issues">Issue tracker</a>

## Latest API Docs 

Latest API Docs is <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0">2.0.0</a>.

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

* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#getbool-function">getBool</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This method returns a 'boolean' value, either 'true' or 'false', based on the valuespecified against the JSON element present in the given path.In case there is no valid boolean value found in the given path, the method still returns 'false'.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#getdouble-function">getDouble</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This method returns the double value of the JSON element present in the given path. If there is no valid double value in the given path, the method returns 'null'.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#getfloat-function">getFloat</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This method returns the float value of the JSON element present in the given path.If there is no valid float value in the given path, the method returns 'null'.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#getint-function">getInt</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This method returns the integer value of the JSON element present in the given path. If there is no valid integer value in the given path, the method returns 'null'.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#getlong-function">getLong</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the long value of the JSON element present in the given path. Ifthere is no valid long value in the given path, the method returns 'null'.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#getobject-function">getObject</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the object of the JSON element present in the given path.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#getstring-function">getString</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the string value of the JSON element present in the given path.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#isexists-function">isExists</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This method checks whether there is a JSON element present in the given path or not.If there is a valid JSON element in the given path, it returns 'true'. If there is no valid JSON element, it returns 'false'</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#setelement-function">setElement</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This method allows to insert elements into a given JSON present in a specific path. If there is no valid path given, it returns the original JSON. Otherwise, it returns the new JSON.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#toobject-function">toObject</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This method returns the JSON object related to a given JSON string.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#tostring-function">toString</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This method returns the JSON string corresponding to a given JSON object.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#tokenize-stream-processor">tokenize</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#stream-processor">(Stream Processor)</a>*<br><div style="padding-left: 1em;"><p>This tokenizes the given json according the path provided</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-json/api/2.0.0/#tokenizeasobject-stream-processor">tokenizeAsObject</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#stream-processor">(Stream Processor)</a>*<br><div style="padding-left: 1em;"><p>This tokenizes the given JSON based on the path provided and returns the response as an object.</p></div>

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
