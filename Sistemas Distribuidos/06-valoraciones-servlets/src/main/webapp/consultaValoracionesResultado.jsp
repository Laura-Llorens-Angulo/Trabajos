<%@ page import="org.json.simple.JSONArray" %>
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

            String nomRest = (String) request.getParameter("nombreRestaurante").toString();
            boolean operacionExitosa = (boolean) request.getAttribute("operacionExitosa");
            if (operacionExitosa) {
        %>

            <h2>Valoraciones de '<%= nomRest %>'</h2>

            <table class="lista-valoraciones">
                <thead>
                    <tr>
                        <th>Código cliente</th>
                        <th>Fecha</th>
                        <th>Estrellas</th>
                        <th>Comentario</th>
                    </tr>
                </thead>
                <tbody>

                    <%
                        JSONArray valoraciones = (JSONArray) request.getAttribute("valoraciones");
                        for(int i = 0; i< valoraciones.size(); i++) {
                            JSONObject v = (JSONObject) valoraciones.get(i);
                    %>
                        <tr>
                            <td><%= v.get("codcliente")%></td>
                            <td><%= v.get("fecha")%></td>
                            <td><%= v.get("estrellas")%></td>
                            <td><%= v.get("comentario")%></td>
                        </tr>
                    <%
                        }
                    %>

                </tbody>
            </table>


        <%
            } else {
        %>

            <h2>Lo sentimos, no hay valoraciones para '<%= nomRest %>'</h2>

        <%
            }
        %>

        <a class="retorno-menu" href="menu.jsp">Menú</a>

    </body>
</html>
