# API Docs - v2.0.11

!!! Info "Tested Siddhi Core version: *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/">5.1.21</a>*"
    It could also support other Siddhi Core minor versions.

## Json

### group *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#aggregate-function">(Aggregate Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">This function aggregates the JSON elements and returns a JSON object by adding enclosing.element if it is provided. If enclosing.element is not provided it aggregate the JSON elements returns a JSON array.</p>
<p></p>
<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>

```
<OBJECT> json:group(<STRING|OBJECT> json)
<OBJECT> json:group(<STRING|OBJECT> json, <BOOL> distinct)
<OBJECT> json:group(<STRING|OBJECT> json, <STRING> enclosing.element)
<OBJECT> json:group(<STRING|OBJECT> json, <STRING> enclosing.element, <BOOL> distinct)
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON element that needs to be aggregated.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">enclosing.element</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON element used to enclose the aggregated JSON elements.</p></td>
        <td style="vertical-align: top">EMPTY_STRING</td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">Yes</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">distinct</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">This is used to only have distinct JSON elements in the concatenated JSON object/array that is returned.</p></td>
        <td style="vertical-align: top">false</td>
        <td style="vertical-align: top">BOOL</td>
        <td style="vertical-align: top">Yes</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
from InputStream#window.length(5)
select json:group("json") as groupedJSONArray
input OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">When we input events having values for the <code>json</code> as <code>{"date":"2013-11-19","time":"10:30"}</code> and <code>{"date":"2013-11-19","time":"12:20"}</code>, it returns <code>[{"date":"2013-11-19","time":"10:30"}{"date":"2013-11-19","time":"12:20"}]</code> to the 'OutputStream'.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
from InputStream#window.length(5)
select json:group("json", true) as groupedJSONArray
input OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">When we input events having values for the <code>json</code> as <code>{"date":"2013-11-19","time":"10:30"}</code> and <code>{"date":"2013-11-19","time":"10:30"}</code>, it returns <code>[{"date":"2013-11-19","time":"10:30"}]</code> to the 'OutputStream'.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
from InputStream#window.length(5)
select json:group("json", "result") as groupedJSONArray
input OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">When we input events having values for the <code>json</code> as <code>{"date":"2013-11-19","time":"10:30"}</code> and <code>{"date":"2013-11-19","time":"12:20"}</code>, it returns <code>{"result":[{"date":"2013-11-19","time":"10:30"},{"date":"2013-11-19","time":"12:20"}}</code> to the 'OutputStream'.</p>
<p></p>
<span id="example-4" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 4</span>
```
from InputStream#window.length(5)
select json:group("json", "result", true) as groupedJSONArray
input OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">When we input events having values for the <code>json</code> as <code>{"date":"2013-11-19","time":"10:30"}</code> and <code>{"date":"2013-11-19","time":"10:30"}</code>, it returns <code>{"result":[{"date":"2013-11-19","time":"10:30"}]}</code> to the 'OutputStream'.</p>
<p></p>
### groupAsObject *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#aggregate-function">(Aggregate Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">This function aggregates the JSON elements and returns a JSON object by adding enclosing.element if it is provided. If enclosing.element is not provided it aggregate the JSON elements returns a JSON array.</p>
<p></p>
<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>

```
<OBJECT> json:groupAsObject(<STRING|OBJECT> json)
<OBJECT> json:groupAsObject(<STRING|OBJECT> json, <BOOL> distinct)
<OBJECT> json:groupAsObject(<STRING|OBJECT> json, <STRING> enclosing.element)
<OBJECT> json:groupAsObject(<STRING|OBJECT> json, <STRING> enclosing.element, <BOOL> distinct)
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON element that needs to be aggregated.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">enclosing.element</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON element used to enclose the aggregated JSON elements.</p></td>
        <td style="vertical-align: top">EMPTY_STRING</td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">Yes</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">distinct</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">This is used to only have distinct JSON elements in the concatenated JSON object/array that is returned.</p></td>
        <td style="vertical-align: top">false</td>
        <td style="vertical-align: top">BOOL</td>
        <td style="vertical-align: top">Yes</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
from InputStream#window.length(5)
select json:groupAsObject("json") as groupedJSONArray
input OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">When we input events having values for the <code>json</code> as <code>{"date":"2013-11-19","time":"10:30"}</code> and <code>{"date":"2013-11-19","time":"12:20"}</code>, it returns <code>[{"date":"2013-11-19","time":"10:30"}{"date":"2013-11-19","time":"12:20"}]</code> to the 'OutputStream'.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
from InputStream#window.length(5)
select json:groupAsObject("json", true) as groupedJSONArray
input OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">When we input events having values for the <code>json</code> as <code>{"date":"2013-11-19","time":"10:30"}</code> and <code>{"date":"2013-11-19","time":"10:30"}</code>, it returns <code>[{"date":"2013-11-19","time":"10:30"}]</code> to the 'OutputStream'.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
from InputStream#window.length(5)
select json:groupAsObject("json", "result") as groupedJSONArray
input OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">When we input events having values for the <code>json</code> as <code>{"date":"2013-11-19","time":"10:30"}</code> and <code>{"date":"2013-11-19","time":"12:20"}</code>, it returns <code>{"result":[{"date":"2013-11-19","time":"10:30"},{"date":"2013-11-19","time":"12:20"}}</code> to the 'OutputStream'.</p>
<p></p>
<span id="example-4" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 4</span>
```
from InputStream#window.length(5)
select json:groupAsObject("json", "result", true) as groupedJSONArray
input OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">When we input events having values for the <code>json</code> as <code>{"date":"2013-11-19","time":"10:30"}</code> and <code>{"date":"2013-11-19","time":"10:30"}</code>, it returns <code>{"result":[{"date":"2013-11-19","time":"10:30"}]}</code> to the 'OutputStream'.</p>
<p></p>
### getBool *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function retrieves the 'boolean' value specified in the given path of the JSON element.</p>
<p></p>
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON input containing boolean value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON path to fetch the boolean value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:getBool(json,'$.married')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'married' : true}</code>, the function returns <code>true</code> as there is a matching boolean at <code>$.married</code>.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:getBool(json,'$.name')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'married' : true}</code>, the function returns <code>null</code> as there is no matching boolean at <code>$.name</code>.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
json:getBool(json,'$.foo')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'married' : true}</code>, the function returns <code>null</code> as there is no matching element at <code>$.foo</code>.</p>
<p></p>
### getDouble *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function retrieves the 'double' value specified in the given path of the JSON element.</p>
<p></p>
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON input containing double value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON path to fetch the double value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:getDouble(json,'$.salary')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'salary' : 12000.0}</code>, the function returns <code>12000.0</code> as there is a matching double at <code>$.salary</code>.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:getDouble(json,'$.salary')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching element at <code>$.salary</code>.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
json:getDouble(json,'$.name')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching double at <code>$.name</code>.</p>
<p></p>
### getFloat *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function retrieves the 'float' value specified in the given path of the JSON element.</p>
<p></p>
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON input containing float value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON path to fetch the float value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:getFloat(json,'$.salary')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'salary' : 12000.0}</code>, the function returns <code>12000</code> as there is a matching float at <code>$.salary</code>.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:getFloat(json,'$.salary')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching element at <code>$.salary</code>.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
json:getFloat(json,'$.name')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching float at <code>$.name</code>.</p>
<p></p>
### getInt *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function retrieves the 'int' value specified in the given path of the JSON element.</p>
<p></p>
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON input containing int value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON path to fetch the int value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:getInt(json,'$.age')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>23</code> as there is a matching int at <code>$.age</code>.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:getInt(json,'$.salary')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching element at <code>$.salary</code>.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
json:getInt(json,'$.name')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching int at <code>$.name</code>.</p>
<p></p>
### getLong *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function retrieves the 'long' value specified in the given path of the JSON element.</p>
<p></p>
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON input containing long value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON path to fetch the long value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:getLong(json,'$.age')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>23</code> as there is a matching long at <code>$.age</code>.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:getLong(json,'$.salary')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching element at <code>$.salary</code>.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
json:getLong(json,'$.name')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching long at <code>$.name</code>.</p>
<p></p>
### getObject *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function retrieves the object specified in the given path of the JSON element.</p>
<p></p>
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON input containing the object.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON path to fetch the object.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:getObject(json,'$.address')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'address' : {'city' : 'NY', 'country' : 'USA'}}</code>, the function returns <code>{'city' : 'NY', 'country' : 'USA'}</code> as there is a matching object at <code>$.address</code>.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:getObject(json,'$.age')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>23</code> as there is a matching object at <code>$.age</code>.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
json:getObject(json,'$.salary')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching element at <code>$.salary</code>.</p>
<p></p>
### getString *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function retrieves value specified in the given path of the JSON element as a string.</p>
<p></p>
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON input containing value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON path to fetch the value.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:getString(json,'$.name')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>John</code> as there is a matching string at <code>$.name</code>.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:getString(json,'$.salary')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>null</code> as there are no matching element at <code>$.salary</code>.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
json:getString(json,'$.age')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>23</code> as a string as there is a matching element at <code>$.age</code>.</p>
<p></p>
<span id="example-4" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 4</span>
```
json:getString(json,'$.address')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'address' : {'city' : 'NY', 'country' : 'USA'}}</code>, the function returns <code>{'city' : 'NY', 'country' : 'USA'}</code> as a string as there is  a matching element at <code>$.address</code>.</p>
<p></p>
### isExists *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function checks whether there is a JSON element present in the given path or not.</p>
<p></p>
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON input that needs to be searched for an elements.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON path to check for the element.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:isExists(json, '$.name')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>true</code> as there is an element in the given path.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:isExists(json, '$.salary')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'age' : 23}</code>, the function returns <code>false</code> as there is no element in the given path.</p>
<p></p>
### setElement *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function sets JSON element into a given JSON at the specific path.</p>
<p></p>
<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>

```
<OBJECT> json:setElement(<STRING|OBJECT> json, <STRING> path, <STRING|BOOL|DOUBLE|FLOAT|INT|LONG|OBJECT> json.element)
<OBJECT> json:setElement(<STRING|OBJECT> json, <STRING> path, <STRING|BOOL|DOUBLE|FLOAT|INT|LONG|OBJECT> json.element, <STRING> key)
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON to which a JSON element needs to be added/replaced.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON path where the JSON element should be added/replaced.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">json.element</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The JSON element being added.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>BOOL<br>DOUBLE<br>FLOAT<br>INT<br>LONG<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">key</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The key to be used to refer the newly added element in the input JSON.</p></td>
        <td style="vertical-align: top">Assumes the element is added to a JSON array, or the element selected by the JSON path will be updated.</td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">Yes</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:setElement(json, '$', "{'country' : 'USA'}", 'address')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'married' : true}</code>,the function updates the <code>json</code> as <code>{'name' : 'John', 'married' : true, 'address' : {'country' : 'USA'}}</code> by adding 'address' element and returns the updated JSON.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:setElement(json, '$', 40, 'age')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'married' : true}</code>,the function updates the <code>json</code> as <code>{'name' : 'John', 'married' : true, 'age' : 40}</code> by adding 'age' element and returns the updated JSON.</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
json:setElement(json, '$', 45, 'age')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'married' : true, 'age' : 40}</code>, the function updates the <code>json</code> as <code>{'name' : 'John', 'married' : true, 'age' : 45}</code> by replacing 'age' element and returns the updated JSON.</p>
<p></p>
<span id="example-4" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 4</span>
```
json:setElement(json, '$.items', 'book')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'Stationary', 'items' : ['pen', 'pencil']}</code>, the function updates the <code>json</code> as <code>{'name' : 'John', 'items' : ['pen', 'pencil', 'book']}</code> by adding 'book' in the items array and returns the updated JSON.</p>
<p></p>
<span id="example-5" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 5</span>
```
json:setElement(json, '$.item', 'book')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'Stationary', 'item' : 'pen'}</code>, the function updates the <code>json</code> as <code>{'name' : 'John', 'item' : 'book'}</code> by replacing 'item' element and returns the updated JSON.</p>
<p></p>
<span id="example-6" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 6</span>
```
json:setElement(json, '$.address', 'city', 'SF')
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the <code>json</code> is the format <code>{'name' : 'John', 'married' : true}</code>,the function will not update, but returns the original JSON as there are no valid path for <code>$.address</code>.</p>
<p></p>
### toObject *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function generate JSON object from the given JSON string.</p>
<p></p>
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">A valid JSON string that needs to be converted to a JSON object.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:toJson(json)
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">This returns the JSON object corresponding to the given JSON string.</p>
<p></p>
### toString *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">(Function)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Function generates a JSON string corresponding to a given JSON object.</p>
<p></p>
<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>

```
<STRING> json:toString(<STRING|OBJECT> json)
<STRING> json:toString(<STRING|OBJECT> json, <BOOL> allow.escape)
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">A valid JSON object to generates a JSON string.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">allow.escape</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">If this is set to true, quotes will be escaped in the resulting string. Otherwise quotes will not be escaped.</p></td>
        <td style="vertical-align: top">false</td>
        <td style="vertical-align: top">BOOL</td>
        <td style="vertical-align: top">Yes</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
json:toString(json)
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">This returns the JSON string corresponding to a given JSON object.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
json:toString(json, true)
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">Assume the json object has the field 'user' with value 'david'. With the allowEscape parameter set to true, this will return the string "{\"user\":\"david\"}"</p>
<p></p>
<span id="example-3" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 3</span>
```
json:toString(json, false)
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">Assume the json object has the field 'user' with value 'david'. With the allowEscape parameter set to false, this will return the string {"user":"david"}</p>
<p></p>
### tokenize *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#stream-processor">(Stream Processor)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Stream processor tokenizes the given JSON into to multiple JSON string elements and sends them as separate events.</p>
<p></p>
<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>

```
json:tokenize(<STRING|OBJECT> json, <STRING> path)
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The input JSON that needs to be tokenized.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The path of the set of elements that will be tokenized.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">fail.on.missing.attribute</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">If there are no element on the given path, when set to <code>true</code> the system will drop the event, and when set to <code>false</code> the system will pass 'null' value to the jsonElement output attribute.</p></td>
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
        <td style="vertical-align: top;"><p style="word-wrap: break-word;margin: 0;">The JSON element retrieved based on the given path will be returned as a JSON string. If the 'path' selects a JSON array then the system returns each element in the array as a JSON string via a separate events.</p></td>
        <td style="vertical-align: top">STRING</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream (json string, path string);

@info(name = 'query1')
from InputStream#json:tokenizeAsObject(json, path)
select path, jsonElement
insert into OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the input 'json' is <code>{name:'John', enrolledSubjects:['Mathematics', 'Physics']}</code>, and the 'path' is passed as <code>$.enrolledSubjects</code> then for both the elements in the selected JSON array, it generates it generates events as <code>('$.enrolledSubjects', 'Mathematics')</code>, and <code>('$.enrolledSubjects', 'Physics')</code>.<br>For the same input JSON, if the 'path' is passed as <code>$.name</code> then it will only produce one event <code>('$.name', 'John')</code> as the 'path' provided a single JSON element.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
define stream InputStream (json string, path string);

@info(name = 'query1')
from InputStream#json:tokenizeAsObject(json, path, true)
select path, jsonElement
insert into OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the input 'json' is <code>{name:'John', age:25}</code>,and the 'path' is passed as <code>$.salary</code> then the system will produce <code>('$.salary', null)</code>, as the 'fail.on.missing.attribute' is <code>true</code> and there are no matching element for <code>$.salary</code>.</p>
<p></p>
### tokenizeAsObject *<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#stream-processor">(Stream Processor)</a>*
<p></p>
<p style="word-wrap: break-word;margin: 0;">Stream processor tokenizes the given JSON into to multiple JSON object elements and sends them as separate events.</p>
<p></p>
<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>

```
json:tokenizeAsObject(<STRING|OBJECT> json, <STRING> path)
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
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The input JSON that needs to be tokenized.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING<br>OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">path</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">The path of the set of elements that will be tokenized.</p></td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">Yes</td>
    </tr>
    <tr>
        <td style="vertical-align: top">fail.on.missing.attribute</td>
        <td style="vertical-align: top; word-wrap: break-word"><p style="word-wrap: break-word;margin: 0;">If there are no element on the given path, when set to <code>true</code> the system will drop the event, and when set to <code>false</code> the system will pass 'null' value to the jsonElement output attribute.</p></td>
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
        <td style="vertical-align: top;"><p style="word-wrap: break-word;margin: 0;">The JSON element retrieved based on the given path will be returned as a JSON object. If the 'path' selects a JSON array then the system returns each element in the array as a JSON object via a separate events.</p></td>
        <td style="vertical-align: top">OBJECT</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream InputStream (json string, path string);

@info(name = 'query1')
from InputStream#json:tokenizeAsObject(json, path)
select path, jsonElement
insert into OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the input 'json' is <code>{name:'John', enrolledSubjects:['Mathematics', 'Physics']}</code>, and the 'path' is passed as <code>$.enrolledSubjects</code> then for both the elements in the selected JSON array, it generates it generates events as <code>('$.enrolledSubjects', 'Mathematics')</code>, and <code>('$.enrolledSubjects', 'Physics')</code>.<br>For the same input JSON, if the 'path' is passed as <code>$.name</code> then it will only produce one event <code>('$.name', 'John')</code> as the 'path' provided a single JSON element.</p>
<p></p>
<span id="example-2" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 2</span>
```
define stream InputStream (json string, path string);

@info(name = 'query1')
from InputStream#json:tokenizeAsObject(json, path, true)
select path, jsonElement
insert into OutputStream;
```
<p></p>
<p style="word-wrap: break-word;margin: 0;">If the input 'json' is <code>{name:'John', age:25}</code>,and the 'path' is passed as <code>$.salary</code> then the system will produce <code>('$.salary', null)</code>, as the 'fail.on.missing.attribute' is <code>true</code> and there are no matching element for <code>$.salary</code>.</p>
<p></p>
