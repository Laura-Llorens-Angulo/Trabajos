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

@WebServlet(name = "ServletAccede", value = "/ServletAccede")
public class ServletAccede extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String codcli = request.getParameter("codcli").toString();
        System.out.println("[LOG] Llamada a doGet con " + codcli);

        initializeGestorValoraciones();

        HttpSession session = request.getSession();
        session.setAttribute("codcli", codcli);

        request.setAttribute("ruta", request.getContextPath());
        RequestDispatcher vista = request.getRequestDispatcher("menu.jsp");
        vista.forward(request, response);
    }

    public synchronized void initializeGestorValoraciones() {
        ServletContext contexto = getServletContext();
        GestorValoraciones gestorValoraciones = (GestorValoraciones) contexto.getAttribute("gestor");
        if (gestorValoraciones == null) {
            gestorValoraciones = new GestorValoraciones();
            contexto.setAttribute("gestor", gestorValoraciones);
        }
    }
}