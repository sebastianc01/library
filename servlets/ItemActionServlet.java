/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.servlets;

import pl.polsl.lab.model.IncorrectDateException;
import pl.polsl.lab.model.ForbiddenCharactersException;
import pl.polsl.lab.model.CorrectInput;
import pl.polsl.lab.model.CorrectDate;
import pl.polsl.lab.model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import pl.polsl.lab.model.Modeldb;

/**
 * Servlet lets user to add, remove, borrow and return a book. It also prints last successfully performed action.
 * @author Sebastian
 */
public class ItemActionServlet extends HttpServlet {
    /**
     * Constructor initiating statistics collection
     */
    public ItemActionServlet() {
        
    }
//    @Override
//    public void init() {
//        model = new Model();
//        model.loadFile(fileString)
//    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Modeldb model;
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("sesja");
        if (obj == null) {
        model = new Modeldb();
        } else {
        model = (Modeldb)obj;
        }
        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();
        // Get parameter values - firstName i lastName
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String date = request.getParameter("date");
        String[] action = request.getParameterValues("action");
        // FirstName or lastName was not given - send error message

//        try {
//        if(action != null) {
//        for(int i = 0; i< action.length;++i) {
//            if(!action[i].isEmpty()) {
//            System.out.println(action[i]);
//            }
//        }
//        }
//        } catch(Exception e) {
//            System.out.println("exception:(");
//        }
        try {
        if (name.length() == 0 || author.length() == 0  || date.length() == 0) {
            response.sendError(response.SC_BAD_REQUEST, "You should give three parameters!");
        }
        else if (action == null) {
            response.sendError(response.SC_BAD_REQUEST, "You have to choose an action!");
        }
        else if (action.length > 1) {
            response.sendError(response.SC_BAD_REQUEST, "You cannot choose more than one action!");
        }
        else if(CorrectInput.loadCorrectInput(CorrectDate.loadCorrectDate(date)).isEmpty() ||
                CorrectInput.loadCorrectInput(CorrectDate.loadCorrectDate(date)).isBlank()) {
            response.sendError(response.SC_BAD_REQUEST, "Date is not correct!");
        }
        else if(CorrectInput.loadCorrectInput(name).isEmpty() || CorrectInput.loadCorrectInput(author).isEmpty() ||
                CorrectInput.loadCorrectInput(name).isBlank() || CorrectInput.loadCorrectInput(author).isBlank()) {
            response.sendError(response.SC_BAD_REQUEST, "Incorrect data!");
        }
        else if(action[0].equals("add") ||
                action[0].equals("remove") ||
                action[0].equals("borrow") ||
                action[0].equals("return")) {
            
            if (action[0].equals("add")) {
                Item item = new Item(CorrectInput.loadCorrectInput(name), CorrectInput.loadCorrectInput(author), CorrectInput.loadCorrectInput(CorrectDate.loadCorrectDate(date)), 1, 1);
                model.addItem(item);
                //model.getData().add(item);
                
                out.println("<html><head><style>");
                out.println(".tab {\n" +
                "        tab-size: 5;\n" +
                "    }");
                out.println("</head></style>");
                out.println("\n<body>\n<h1>Item " + name + ", " + author + ", " + date + " has been added</h1>\n");
                //out.println("</body>\n</html>");
                Cookie[] cookies = request.getCookies();
                   String string = "add%@" + name + "%@"+ author + "%@" + date;
                   Cookie cookie = new Cookie("actions", string);
                   response.addCookie(cookie);
                   for(Cookie c: cookies) {
                       if(c.getName().equals("action")) {
                           String cookieString = c.getValue();
                           c.setValue("");
                           c.setPath("/");
                           c.setMaxAge(0);
                           response.addCookie(cookie);
                           int order = getFirstNumber(cookieString);
                           if(order >=0 && order < 9) {
                               ++order;
                               String temp = String.valueOf(order) + cookieString.substring(1);
                               Cookie tempCookie = new Cookie("actions", temp);
                               response.addCookie(tempCookie);
                           }
                       }
                   }
            }
            else if (action[0].equals("remove")) {
                Item item = new Item(CorrectInput.loadCorrectInput(name), CorrectInput.loadCorrectInput(author), CorrectInput.loadCorrectInput(CorrectDate.loadCorrectDate(date)), 1, 1);
                model.removeItem(item);
                out.println("<html>\n<body>\n<h1>Item " + name + ", " + author + ", " + date + " has been removed</h1>\n");
               // out.println("</body>\n</html>");
                Cookie[] cookies = request.getCookies();
                   String string = "remove%@" + name + "%@"+ author + "%@" + date;
                   Cookie cookie = new Cookie("actions", string);
                   response.addCookie(cookie);
                   for(Cookie c: cookies) {
                       if(c.getName().equals("action")) {
                           String cookieString = c.getValue();
                           c.setValue("");
                           c.setPath("/");
                           c.setMaxAge(0);
                           response.addCookie(cookie);
                           int order = getFirstNumber(cookieString);
                           if(order >=0 && order < 9) {
                               ++order;
                               String temp = String.valueOf(order) + cookieString.substring(1);
                               Cookie tempCookie = new Cookie("actions", temp);
                               response.addCookie(tempCookie);
                           }
                       }
                   }
            }
            else if (action[0].equals("borrow")) {
                Item item = new Item(CorrectInput.loadCorrectInput(name), CorrectInput.loadCorrectInput(author), CorrectInput.loadCorrectInput(CorrectDate.loadCorrectDate(date)), 1, 1);
                if(model.borrowBook(item) == true) {
                    out.println("<html>\n<body>\n<h1>Item " + name + ", " + author + ", " + date + " has been borrowed</h1>\n");
                    //out.println("</body>\n</html>");
                   Cookie[] cookies = request.getCookies();
                   String string = "borrow%@" + name + "%@"+ author + "%@" + date;
                   Cookie cookie = new Cookie("actions", string);
                   response.addCookie(cookie);
                   for(Cookie c: cookies) {
                       if(c.getName().equals("action")) {
                           String cookieString = c.getValue();
                           c.setValue("");
                           c.setPath("/");
                           c.setMaxAge(0);
                           response.addCookie(cookie);
                           int order = getFirstNumber(cookieString);
                           if(order >=0 && order < 9) {
                               ++order;
                               String temp = String.valueOf(order) + cookieString.substring(1);
                               Cookie tempCookie = new Cookie("actions", temp);
                               response.addCookie(tempCookie);
                           }
                       }
                   }
                   System.out.printf("\nAdded cookie: %s\n", string);
                   }
                else {
                    out.println("<html>\n<body>\n<h1>Item " + name + ", " + author + ", " + date + " could not be borrowed</h1>\n");
                    //out.println("</body>\n</html>");
               }
            }
            else if (action[0].equals("return")) {
                Item item = new Item(CorrectInput.loadCorrectInput(name), CorrectInput.loadCorrectInput(author), CorrectInput.loadCorrectInput(CorrectDate.loadCorrectDate(date)), 1, 1);
                if(model.returnBook(item) == true) {
                    out.println("<html>\n<body>\n<h1>Item " + name + ", " + author + ", " + date + " has been returned</h1>\n");
                    //out.println("</body>\n</html>");
                    Cookie[] cookies = request.getCookies();
                    String string = "returned:%@" + name + "%@"+ author + "%@" + date;
                   for(Cookie c: cookies) {
                       if(c.getName().equals("action")) {
                           c.setValue(string);
                           response.addCookie(c);
                       }
                   }
                    
                }
                else {
                    out.println("<html>\n<body>\n<h1>Item " + name + ", " + author + ", " + date + " could not be returned</h1>\n");
                    //out.println("</body>\n</html>");
                }
            }
            Cookie[] cookies = request.getCookies();
            out.println("<br>");
            out.println("\n\n<h3>Last successful actions:</h3>\n");
            if(cookies != null) {
            for(Cookie cookie: cookies) {
                if(cookie.getName().equals("actions")) {
                    String string = cookie.getValue();
                    if(!string.isBlank() || !string.isEmpty()) {
                    out.println("<p>");
                    String temp;
                    int index = 0; 
                    index = string.indexOf("%@", index + 2);
                    temp = string.substring(0, index);
                    out.println(temp);
                    string = string.substring(index + 2);
                    index = 2;
                    index = string.indexOf("%@", index);
                    temp = string.substring(0, index);
                    out.println("<span class=\"tab\"></span>");
                    out.println(temp);
                    string = string.substring(index + 2);
                    index = 2;
                    index = string.indexOf("%@", index);
                    temp = string.substring(0, index);
                    out.println("<span class=\"tab\"></span>");
                    out.println(temp);
                    string = string.substring(index + 2);
                    out.println("<span class=\"tab\"></span>");
                    out.println(string);
                    out.println("</p>");
                }
                }
            }
            }
            out.println("</body>\n</html>");
        }
        else {
        }
        session.setAttribute("sesja", model);
        } catch(IncorrectDateException | ForbiddenCharactersException e) {
            response.sendError(response.SC_BAD_REQUEST, e.getMessage());
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
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/plain; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();

        out.println("Passed parameters:");

        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            out.println(name + " = " + request.getParameter(name));
        }
    }
    int getFirstNumber(String string) {
        return Character.getNumericValue(string.charAt(0));
    }
}