
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="css/theme.css" />

<html>
    <head>
        <title>Find in text</title>
    </head>
    <body>
        <h1>Find your word</h1>
        <div class="container">
          <form action="ReadFileServlet" method="GET">
              <p>Input search word: <input type="text" name="q"></p>
              <p>Limit: <input type="text" name="limit"></p>
              <p>Max string length: <input type="text" name="length"></p>
              <input type="checkbox" name="includeMetaData" value="true"> Meta Data<BR>
              <input type="submit" value="Submit" />
          </form>
        </div>
    </body>
</html>