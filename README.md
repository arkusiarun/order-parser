Input data
1. CSV file. 
Columns: 
Order ID, amount, currency, comment
Example:
1,100,USD,order payment
2,123,EUR,order payment
Note: All columns are required

1. JSON file.
Example:
{&quot;orderId&quot;:3, &quot;amount&quot;:1.23, &quot;currency&quot;: &quot;USD&quot;, &quot;comment&quot;: &quot;order payment&quot;}
{&quot;orderId&quot;:4, &quot;amount&quot;:1.24, &quot;currency&quot;: &quot;EUR&quot;, &quot;comment&quot;: &quot;order payment&quot;}
Note: All fields are required
Output data
{“id”:1,“orderId”:1,”amount”:100,”comment”:”order
payment”,”filename”:”orders.csv”,”line”:1,”result”:”OK”}
{“id”:2,“orderId”:2,”amount”:123,”comment”:”order
payment”,”filename”:”orders.csv”,”line”:2,”result”:”OK”}

{“id”:3,“orderId”:3,”amount”:1.23,”comment”:”order
payment”,”filename”:”orders.json”,”line”:1,”result”:”OK”}
{“id”:4,“orderId”:4,”amount”:1.24,”comment”:”order
payment”,”filename”:”orders.json”,”line”:2,”result”:”OK”}

 id - order identifier
 amount - order amount
 currency - currency of the order amount
 comment - comment on the order
 filename - name of the source file
 line - line number of the source file
 result - result of parsing the source file entry
o OK - if record was converted correctly, 
or error description if the record was not converted correctly