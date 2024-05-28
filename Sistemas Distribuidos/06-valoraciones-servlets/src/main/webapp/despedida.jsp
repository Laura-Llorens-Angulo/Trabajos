<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>ValoraRest</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>

        <%
            String codcli = (String) request.getAttribute("codcli");

            if (codcli == null) {
                response.sendRedirect("index.html");
            }
        %>

        <h2>Hasta luego <%= codcli %></h2>
        <h2 class="sin-separacion-superior">Te estaremos esperando para conocer tus valoraciones</h2>

    </body>
</html>
