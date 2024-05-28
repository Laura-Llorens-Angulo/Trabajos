<%@ page import="org.json.simple.JSONArray" %>
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
            String codcli = (String) request.getSession().getAttribute("codcli");
            if (codcli == null)
                response.sendRedirect("index.html");

            boolean operacionExitosa = (boolean) request.getAttribute("operacionExitosa");
            if (operacionExitosa) {
        %>

            <h2>Listado de restaurantes</h2>

            <ul>
                <%
                  JSONArray restaurantes = (JSONArray) request.getAttribute("restaurantes");
                  for(int i = 0; i< restaurantes.size(); i++) {
                    String restaurante = (String) restaurantes.get(i);
                %>
                    <li><%= restaurante %></li>
                <%
                  }
                %>
            </ul>

        <%
            } else {
        %>

            <h2>Lo sentimos, no hay restaurantes</h2>

        <%
            }
        %>

        <a class="retorno-menu" href="menu.jsp">Menú</a>

    </body>
</html>
