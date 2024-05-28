<%@ page import="org.json.simple.JSONObject" %>
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
                JSONObject v = (JSONObject) request.getAttribute("valoracion");
        %>

            <h2>Enhorabuena <%= codcli %></h2>
            <h2 class="sin-separacion-superior">Has modificado la siguiente valoración:</h2>

            <ul>
                <li>Restaurante: <%= v.get("nomRest")%></li>
                <li>Fecha: <%= v.get("fecha")%></li>
                <li>Estrellas: <%= v.get("estrellas")%></li>
                <li>Comentario: <%= v.get("comentario")%></li>
            </ul>

        <%
            } else {
        %>

            <h2>Lo sentimos <%= codcli %>, no se ha podido modificar la valoración</h2>

        <%
            }
        %>

        <a class="retorno-menu" href="menu.jsp">Menú</a>

    </body>
</html>
