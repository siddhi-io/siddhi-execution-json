# API Docs - v2.0.1

## Json

### getBool *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This method returns a 'boolean' value, either 'true' or 'false', based on the valuespecified against the JSON element present in the given path.In case there is no valid boolean value found in the given path, the method still returns 'false'.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<BOOL> json:getBool(<STRING|OBJECT> json, <STRING> path)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON input that holds the boolean value in the given path.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path of the input JSON from which the 'getBool' function fetches theboolean value.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:getBool(json,"$.name") as name
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the boolean value of the JSON input in the given path. The results are directed to the 'OutputStream' stream.</p>

### getDouble *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This method returns the double value of the JSON element present in the given path. If there is no valid double value in the given path, the method returns 'null'.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<DOUBLE> json:getDouble(<STRING|OBJECT> json, <STRING> path)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON input that holds the value in the given path.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path of the input JSON from which the 'getDouble' function fetches thedouble value.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:getDouble(json,"$.name") as name
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the double value of the given path. The results aredirected to the 'OutputStream' stream.</p>

### getFloat *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This method returns the float value of the JSON element present in the given path.If there is no valid float value in the given path, the method returns 'null'.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<FLOAT> json:getFloat(<STRING|OBJECT> json, <STRING> path)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON input that holds the value in the given path.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path of the input JSON from which the 'getFloat' function fetches thevalue.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:getFloat(json,"$.name") as name
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the float value of the JSON input in the given path. The results aredirected to the 'OutputStream' stream.</p>

### getInt *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This method returns the integer value of the JSON element present in the given path. If there is no valid integer value in the given path, the method returns 'null'.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<INT> json:getInt(<STRING|OBJECT> json, <STRING> path)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON input that holds the value in the given path.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path of the input JSON from which the 'getInt' function fetches theinteger value.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:getInt(json,"$.name") as name
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the integer value of the JSON input in the given path. The resultsare directed to the 'OutputStream' stream.</p>

### getLong *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the long value of the JSON element present in the given path. Ifthere is no valid long value in the given path, the method returns 'null'.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<LONG> json:getLong(<STRING|OBJECT> json, <STRING> path)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON input that holds the value in the given path.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path of the JSON element from which the 'getLong' functionfetches the long value.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:getLong(json,"$.name") as name
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the long value of the JSON input in the given path. The results aredirected to 'OutputStream' stream.</p>

### getObject *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the object of the JSON element present in the given path.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> json:getObject(<STRING|OBJECT> json, <STRING> path)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON input that holds the value in the given path.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path of the input JSON from which the 'getObject' function fetches theobject.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:getObject(json,"$.name") as name
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the object of the JSON input in the given path. The results are directed to the 'OutputStream' stream.</p>

### getString *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the string value of the JSON element present in the given path.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<STRING> json:getString(<STRING|OBJECT> json, <STRING> path)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON input that holds the value in the given path.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path of the JSON input from which the 'getString' function fetches  the string value.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:getString(json,"$.name") as name
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the string value of the JSON input in the given path. The results are directed to the 'OutputStream' stream.</p>

### isExists *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This method checks whether there is a JSON element present in the given path or not.If there is a valid JSON element in the given path, it returns 'true'. If there is no valid JSON element, it returns 'false'</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<BOOL> json:isExists(<STRING|OBJECT> json, <STRING> path)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON input in a given path, on which the function performs the search forJSON elements.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path that contains the input JSON on which the function performs the search.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:isExists(json,"$.name") as name
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns either true or false based on the existence of a JSON element in a given path. The results are directed to the 'OutputStream' stream.</p>

### setElement *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This method allows to insert elements into a given JSON present in a specific path. If there is no valid path given, it returns the original JSON. Otherwise, it returns the new JSON.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> json:setElement(<STRING|OBJECT> json, <STRING> path, <STRING|BOOL|DOUBLE|FLOAT|INT|LONG|OBJECT> jsonelement, <STRING> key)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON input into which is this function inserts the new value.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path on the JSON input which is used to insert the given element.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">jsonelement</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON element which is inserted by the function into the input JSON.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>BOOL<br>DOUBLE<br>FLOAT<br>INT<br>LONG<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">key</td>
        <td style="vertical-align: top; word-wrap: break-word">The key which is used to insert the given element into the input JSON.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:setElement(json,"$.name") as name
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the JSON object present in the given path with the newly inserted JSONelement. The results are directed to the 'OutputStream' stream.</p>

### toObject *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This method returns the JSON object related to a given JSON string.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> json:toObject(<STRING> json)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">A valid JSON string from which the function generates the JSON object.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:toJson(json) as jsonObject
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the JSON object corresponding to the given JSON string.The results aredirected to the 'OutputStream' stream.</p>

### toString *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This method returns the JSON string corresponding to a given JSON object.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<STRING> json:toString(<OBJECT> json)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">A valid JSON object from which the function generates a JSON string.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream(json string);
from InputStream
select json:toString(json) as jsonString
insert into OutputStream;
```
<p style="word-wrap: break-word">This returns the JSON string corresponding to a given JSON object. The results are directed to the 'OutputStream' stream.</p>

### tokenize *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#stream-processor">(Stream Processor)</a>*

<p style="word-wrap: break-word">This tokenizes the given json according the path provided</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
json:tokenize(<STRING|OBJECT> json, <STRING> path, <BOOL> fail.on.missing.attribute)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The input json that should be tokenized using the given path.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path that is used to tokenize the given json</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">fail.on.missing.attribute</td>
        <td style="vertical-align: top; word-wrap: break-word">If this parameter is set to 'true' and a json is not provided in the given path, the event is dropped. If the parameter is set to 'false', the unavailability of a json in the specified path results in the event being created with a 'null' value for the json element.</td>
        <td style="vertical-align: top">true</td>
        <td style="vertical-align: top">BOOL</td>
        <td style="vertical-align: top">Yes</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>
<span id="extra-return-attributes" class="md-typeset" style="display: block; font-weight: bold;">Extra Return Attributes</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Possible Types</th>
    </tr>
    <tr>
        <td style="vertical-align: top">jsonElement</td>
        <td style="vertical-align: top; word-wrap: break-word">The json element retrieved based on the given path and the json.</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream (json string,path string);
@info(name = 'query1')
from InputStream#json:tokenize(json, path)
select jsonElement
insert into OutputStream;
```
<p style="word-wrap: break-word">This query performs a tokenization for the given json using the path specified. If the specified path provides a json array, it generates events for each element in that array by adding an additional attributes as the 'jsonElement' to the stream.<br><code>e.g.,
 jsonInput - {name:"John",enrolledSubjects:["Mathematics","Physics"]}, 
 path - "$.enrolledSubjects"
</code><br>&nbsp;If we use the configuration in this example, it generates two events with the attributes "Mathematics", "Physics".<br>If the specified path provides a single json element, it adds the specified json element as an additional attribute named 'jsonElement' into the stream. <br><code>
 e.g.,
 jsonInput - {name:"John",age:25}, 
 path - "$.age"
</code><br></p>

### tokenizeAsObject *<a target="_blank" href="https://siddhi.io/en/v4.0/docs/query-guide/#stream-processor">(Stream Processor)</a>*

<p style="word-wrap: break-word">This tokenizes the given JSON based on the path provided and returns the response as an object.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
json:tokenizeAsObject(<STRING|OBJECT> json, <STRING> path, <BOOL> fail.on.missing.attribute)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json</td>
        <td style="vertical-align: top; word-wrap: break-word">The input json that is tokenized using the given path.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word">The path of the input JSON that the function tokenizes.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">fail.on.missing.attribute</td>
        <td style="vertical-align: top; word-wrap: break-word">If this parameter is set to 'true' and a JSON is not provided in the given path, the event is dropped. If the parameter is set to 'false', the unavailability of a JSON in the specified path results in the event being created with a 'null' value for the json element.</td>
        <td style="vertical-align: top">true</td>
        <td style="vertical-align: top">BOOL</td>
        <td style="vertical-align: top">Yes</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>
<span id="extra-return-attributes" class="md-typeset" style="display: block; font-weight: bold;">Extra Return Attributes</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Possible Types</th>
    </tr>
    <tr>
        <td style="vertical-align: top">jsonElement</td>
        <td style="vertical-align: top; word-wrap: break-word">The JSON element retrieved based on the given path and the JSON.</td>
        <td style="vertical-align: top">OBJECT</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream (json string,path string);
@info(name = 'query1')
from InputStream#json:tokenizeAsObject(json, path)
select jsonElement
insert into OutputStream;
```
<p style="word-wrap: break-word">This query performs a tokenization for the given JSON using the path specified. If the specified path provides a JSON array, it generates events for each element in the specified json array by adding an additional attribute as the 'jsonElement' into the stream.<br><code>e.g.,
 jsonInput - {name:"John",enrolledSubjects:["Mathematics","Physics"]}, 
 path - "$.enrolledSubjects"
</code><br>If we use the configuration in the above example, it generates two events with the attributes "Mathematics" and "Physics".<br>If the specified path provides a single json element, it adds the specified json element as an additional attribute named 'jsonElement' into the stream <br><code>
 e.g.,
 jsonInput - {name:"John",age:25}, 
 path - "$.age"
</code><br></p>

