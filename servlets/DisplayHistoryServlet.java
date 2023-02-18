/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pl.polsl.lab.model.Modeldb;

/**
 *
 * @author Sebastian Cyra
 */
//@WebServlet("/history")
public class DisplayHistoryServlet extends HttpServlet {
    DisplayHistoryServlet() {}
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
            out.println("<title>Recent actions</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th>Action date</th>");
            out.println("<th>Name</th>");
            out.println("<th>Author</th>");
            out.println("<th>Date</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");
            try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            String string = "SELECT h.date as hDate, i.name as name, i.author as author, i.date as iDate, h.action as action " +
                "FROM ActionHistory as h " +
                "LEFT JOIN Items as i " +
                "ON h.ITEMID=i.ITEMID " +
                "ORDER BY h.date desc";
            ResultSet rs = statement.executeQuery(string);
            // Przeglądamy otrzymane wyniki
            
            while (rs.next()) {
                out.println("<tr>");
                out.println("<th>");
                out.println(rs.getString("hDate"));
                out.println("</th>");
                out.println("<th>");
                out.println(rs.getString("name"));
                out.println("</th>");
                out.println("<th>");
                out.println(rs.getString("author"));
                out.println("</th>");            
                out.println("<th>");
                out.println(rs.getDate("Idate"));
                out.println("</th>");
                out.println("<th>");
                out.println(rs.getString("action"));
                out.println("</th>");
                out.println("</tr>");
            }
            System.out.println("-----------------------------------");
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
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
