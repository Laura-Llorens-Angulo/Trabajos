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
import org.json.simple.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "ServletModificarValoracion", value = "/ServletModificarValoracion")
public class ServletModificarValoracion extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession(false);
        String codcli = (String) session.getAttribute("codcli");
        if (codcli == null) {
            RequestDispatcher vista = request.getRequestDispatcher("index.html");
            vista.forward(request, response);
            return;
        }

        String nomRest = request.getParameter("nombreRestaurante");
        String fechaFormatoISO = request.getParameter("fecha");
        long estrellas = Long.parseLong(request.getParameter("estrellas"));
        String comentario = request.getParameter("comentario");
        System.out.println("[LOG] Llamada a doPost con " + nomRest + " " + estrellas + " " + comentario);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDate localDate = LocalDate.parse(fechaFormatoISO);
        String fecha = dtf.format(localDate);

        ServletContext contexto = getServletContext();
        GestorValoraciones gestorValoraciones = (GestorValoraciones) contexto.getAttribute("gestor");
        boolean operacionExitosa = false;
        if (gestorValoraciones != null) {
            JSONObject valoracion = gestorValoraciones.modificaValoracion(codcli, nomRest, fecha, estrellas, comentario);
            if (valoracion != null && !valoracion.isEmpty()) {
                request.setAttribute("valoracion", valoracion);
                operacionExitosa = true;
            }
        }

        request.setAttribute("operacionExitosa", operacionExitosa);
        RequestDispatcher vista = request.getRequestDispatcher("modificarValoracionResultado.jsp");
        vista.forward(request, response);
    }
}