package es.uji.valoraservlets.controlador;

import es.uji.valoraservlets.modelo.GestorValoraciones;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONArray;

import java.io.IOException;

@WebServlet(name = "ServletConsultaValoracionesRestaurante", value = "/ServletConsultaValoracionesRestaurante")
public class ServletConsultaValoracionesRestaurante extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession(false);
        String codcli = (String) session.getAttribute("codcli");
        if (codcli == null) {
            RequestDispatcher vista = request.getRequestDispatcher("index.html");
            vista.forward(request, response);
            return;
        }

        String nomRest = request.getParameter("nombreRestaurante").toString();
        System.out.println("[LOG] Llamada a doGet con " + nomRest);

        ServletContext contexto = getServletContext();
        GestorValoraciones gestorValoraciones = (GestorValoraciones) contexto.getAttribute("gestor");
        boolean operacionExitosa = false;
        if (gestorValoraciones != null) {
            JSONArray valoraciones = gestorValoraciones.consultaValoraciones(nomRest);
            if (valoraciones != null && valoraciones.size() > 0) {
                request.setAttribute("valoraciones", valoraciones);
                operacionExitosa = true;
            }
        }

        request.setAttribute("operacionExitosa", operacionExitosa);
        RequestDispatcher vista = request.getRequestDispatcher("consultaValoracionesResultado.jsp");
        vista.forward(request, response);
    }
}