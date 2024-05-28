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

import java.io.IOException;

@WebServlet(name = "ServletSalir", value = "/ServletSalir")
public class ServletSalir extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        System.out.println("[LOG] Llamada a doGet para salir");

        HttpSession session = request.getSession(false);
        String codcli = (String) session.getAttribute("codcli");
        if (codcli == null) {
            RequestDispatcher vista = request.getRequestDispatcher("index.html");
            vista.forward(request, response);
            return;
        }

        ServletContext contexto = getServletContext();
        GestorValoraciones gestorValoraciones = (GestorValoraciones) contexto.getAttribute("gestor");
        if (gestorValoraciones != null)
            gestorValoraciones.guardaDatos();

        session.invalidate();

        request.setAttribute("codcli", codcli);

        RequestDispatcher vista = request.getRequestDispatcher("despedida.jsp");
        vista.forward(request, response);
    }
}