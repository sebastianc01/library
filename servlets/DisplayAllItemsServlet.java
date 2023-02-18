/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.servlets;

import pl.polsl.lab.model.Item;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pl.polsl.lab.model.Model;
import pl.polsl.lab.model.Modeldb;
/**
 * Servlet presenting all items.
 *
 * @author Sebastian Cyra
 * @version 1.0
 */
//@WebServlet("/allitems")
public class DisplayAllItemsServlet extends HttpServlet {
//    @Override
//    public void init() {
//        model = new Model();
//        model.loadFile("database.txt");
//    }
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
        String name = request.getParameter("name");       
       //response.setContentType("text/html; charset=ISO-8859-2");
       response.setContentType("text/html;charset=UTF-8");
       
        boolean showAll = name == null || name.length() == 0;
        Modeldb model;
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("sesja");
        if (obj == null) {
        model = new Modeldb();
        } else {
        model = (Modeldb)obj;
        }
        session.setAttribute("sesja", model);
        PrintWriter out = response.getWriter();
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Items");
            // Przeglądamy otrzymane wyniki
            while (rs.next()) {
                if(showAll /*|| item.getName().contains(name)*/){
                out.println("<tr>");
                out.println("<td>");
                out.println(rs.getString("name"));
                out.println("</td>");
                out.println("<td>");
                out.println(rs.getString("author"));
                out.println("</td>");            
                out.println("<td>");
                out.println(rs.getDate("date"));
                out.println("</td>");
                out.println("<td>");
                out.println(rs.getInt("currentAmount"));
                out.println("</td>");
                out.println("<td>");
                out.println(rs.getInt("totalAmount"));
                out.println("</td>");
                out.println("</tr>");  
            }}
            System.out.println("-----------------------------------");
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }     
        }
    
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
