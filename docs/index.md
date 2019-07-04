Siddhi Execution JSON
======================================

  [![Jenkins Build Status](https://wso2.org/jenkins/job/siddhi/job/siddhi-execution-json/badge/icon)](https://wso2.org/jenkins/job/siddhi/job/siddhi-execution-json/)
  [![GitHub (pre-)Release](https://img.shields.io/github/release/siddhi-io/siddhi-execution-json/all.svg)](https://github.com/siddhi-io/siddhi-execution-json/releases)
  [![GitHub (Pre-)Release Date](https://img.shields.io/github/release-date-pre/siddhi-io/siddhi-execution-json.svg)](https://github.com/siddhi-io/siddhi-execution-json/releases)
  [![GitHub Open Issues](https://img.shields.io/github/issues-raw/siddhi-io/siddhi-execution-json.svg)](https://github.com/siddhi-io/siddhi-execution-json/issues)
  [![GitHub Last Commit](https://img.shields.io/github/last-commit/siddhi-io/siddhi-execution-json.svg)](https://github.com/siddhi-io/siddhi-execution-json/commits/master)
  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

The **siddhi-execution-json extension** is a <a target="_blank" href="https://siddhi.io/">Siddhi</a> extension that provides capability to retrieve, insert, and modify JSON elements.

For information on <a target="_blank" href="https://siddhi.io/">Siddhi</a> and it's features refer <a target="_blank" href="https://siddhi.io/redirect/docs.html">Siddhi Documentation</a>. 

## Download

* Versions 1.x and above with group id `io.siddhi.extension.*` from <a target="_blank" href="https://mvnrepository.com/artifact/io.siddhi.extension.execution.json/siddhi-execution-json/">here</a>.
* Versions 2.x and lower with group id `org.wso2.extension.siddhi.*` from <a target="_blank" href="https://mvnrepository.com/artifact/org.wso2.extension.siddhi.execution.json/siddhi-execution-json">here</a>.

## Latest API Docs 

Latest API Docs is <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1">2.0.1</a>.

## Features

* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#getbool-function">getBool</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function retrieves the 'boolean' value specified in the given path of the JSON element.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#getdouble-function">getDouble</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function retrieves the 'double' value specified in the given path of the JSON element.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#getfloat-function">getFloat</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function retrieves the 'float' value specified in the given path of the JSON element.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#getint-function">getInt</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function retrieves the 'int' value specified in the given path of the JSON element.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#getlong-function">getLong</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function retrieves the 'long' value specified in the given path of the JSON element.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#getobject-function">getObject</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function retrieves the object specified in the given path of the JSON element.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#getstring-function">getString</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function retrieves value specified in the given path of the JSON element as a string.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#isexists-function">isExists</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function checks whether there is a JSON element present in the given path or not.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#setelement-function">setElement</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function sets JSON element into a given JSON at the specific path.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#toobject-function">toObject</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function generate JSON object from the given JSON string.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#tostring-function">toString</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function generates a JSON string corresponding to a given JSON object.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#tokenize-stream-processor">tokenize</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#stream-processor">Stream Processor</a>)*<br> <div style="padding-left: 1em;"><p>Stream processor tokenizes the given JSON into to multiple JSON string elements and sends them as separate events.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-json/api/2.0.1/#tokenizeasobject-stream-processor">tokenizeAsObject</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#stream-processor">Stream Processor</a>)*<br> <div style="padding-left: 1em;"><p>Stream processor tokenizes the given JSON into to multiple JSON object elements and sends them as separate events.</p></div>

## Dependencies 

There are no other dependencies needed for this extension. 

## Installation

For installing this extension on various siddhi execution environments refer Siddhi documentation section on <a target="_blank" href="https://siddhi.io/redirect/add-extensions.html">adding extensions</a>.

## Support and Contribution

* We encourage users to ask questions and get support via <a target="_blank" href="https://stackoverflow.com/questions/tagged/siddhi">StackOverflow</a>, make sure to add the `siddhi` tag to the issue for better response.

* If you find any issues related to the extension please report them on <a target="_blank" href="https://github.com/siddhi-io/siddhi-execution-json/issues">the issue tracker</a>.

* For production support and other contribution related information refer <a target="_blank" href="https://siddhi.io/community/">Siddhi Community</a> documentation.
