package pl.polsl.lab.servlets;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pl.polsl.lab.model.Modeldb;
/**
 * Servlet displays available items.
 *
 * @author Sebastian Cyra
 * @version 1.0
 */
public class AvailableItemsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Modeldb model;
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("sesja");
        if (obj == null) {
        model = new Modeldb();
        } else {
        model = (Modeldb)obj;
        }
        session.setAttribute("sesja", model);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>All items</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th>Nazwa</th>");
            out.println("<th>Autor</th>");
            out.println("<th>Data</th>");
            out.println("</tr>");
            try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Items");
            // Przeglądamy otrzymane wyniki
            while (rs.next()) {
                if(rs.getInt("currentAmount") > 0){
                out.println("<tr>");
                out.println("<th>");
                out.println(rs.getString("name"));
                out.println("</th>");
                out.println("<th>");
                out.println(rs.getString("author"));
                out.println("</th>");            
                out.println("<th>");
                out.println(rs.getDate("date"));
                out.println("</th>");
                out.println("</tr>");
            }}
            System.out.println("-----------------------------------");
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            Cookie cookie = new Cookie("lastVisit", new java.util.Date().toString());
            response.addCookie(cookie);

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
