/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Sebastian
 */
public final class Modeldb {
    public Modeldb() {
        createTables();
    }
    
     public void createTables() {

        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE Items "
                    + "(itemId INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), name VARCHAR(50), "
                    + "author VARCHAR(50), date DATE, currentAmount INTEGER, totalAmount INTEGER)");
            statement.executeUpdate("CREATE TABLE ActionHistory "
                    + "(hId INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), "
                    + "itemId INTEGER REFERENCES ITEMS(itemId) ON DELETE CASCADE, "
                    + "action VARCHAR(7) NOT NULL,"
                    + "date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            System.out.println("Table created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    
     public boolean loadFile(String fileString) {
        try {
        File file = new File(fileString);
        Scanner read = new Scanner(file);
        while(read.hasNext()) {
            String[] element = read.nextLine().split("%@");
            save(element);
            }
        } catch(FileNotFoundException e) {
            System.err.println("Nie udalo sie wczytac danych z pliku.");
            return false;
        } catch(ArrayIndexOutOfBoundsException e) {
            System.err.println("Dane w pliku zostaly zapisane w niedozwolony sposob.");
            return false;
        }
        return true;
    }
     
     private void save(String[] element) {
         
     }
     
    public void addItem(Item... item) {
        for(Item it:item) {
            try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            String string = "SELECT * FROM Items WHERE name='"+ it.getName() +"' AND author='" + it.getAuthor() +"' AND date='"+ it.getDate() +"'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(string);
            // Przeglądamy otrzymane wyniki
            try{
            rs.next();
            int id = rs.getInt("itemId");
            string = "UPDATE Items " +
                     "SET totalAmount=1+( " +
                     "SELECT totalAmount " +
                     "FROM Items " +
                     "WHERE itemId="+id+"), " +
                     "currentAmount=1+( " +
                     "SELECT currentAmount " +
                     "FROM Items " +
                     "WHERE itemId="+id+")" +
                     "WHERE itemId="+id;
            statement.executeUpdate(string);
            } catch(Exception e) {
                System.out.println(e.getMessage());
                string = "INSERT INTO Items (name, author, date, currentAmount, totalAmount) VALUES ('" + it.getName() +"', '"+it.getAuthor()+"', '"+stringToDate(it.getDate())+"', "+it.getCurrentAmount()+", "+it.getTotalAmount()+")";
                statement.executeUpdate(string);
            }
            finally {
            rs.close();
            string = "SELECT * FROM Items WHERE name='"+ it.getName() +"' AND author='" + it.getAuthor() +"' AND date='"+ it.getDate() +"'";
            Statement statement2 = con.createStatement();
            ResultSet rs2 = statement2.executeQuery(string);
            rs2.next();
            int id = rs2.getInt("itemId");
            string = "INSERT INTO ActionHistory (itemId, action, date) VALUES (" + id +", '"+"add"+"', default)";
            statement2.executeUpdate(string);
            rs2.close();
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
} 
    public boolean removeItem(Item item) {
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            String string = "SELECT * FROM Items WHERE name='"+ item.getName() +"' AND author='" + item.getAuthor() +"' AND date='"+ item.getDate() +"'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(string);
            // Przeglądamy otrzymane wyniki
            rs.next();
            int id = rs.getInt("itemId");
            if(rs.getInt("totalAmount") > 1 && rs.getInt("currentAmount") > 1) {
            string = "UPDATE Items " +
                     "SET totalAmount=-1+( " +
                     "SELECT totalAmount " +
                     "FROM Items " +
                     "WHERE itemId="+id+"), " +
                     "currentAmount=-1+( " +
                     "SELECT currentAmount " +
                     "FROM Items " +
                     "WHERE itemId="+id+")" +
                     "WHERE itemId="+id;
            statement.executeUpdate(string);
            }
            else {
                string = "DELETE  FROM Items "
                        + "WHERE itemId="+id;
            statement.executeUpdate(string);
            }
            rs.close();
            string = "SELECT * FROM Items WHERE name='"+ item.getName() +"' AND author='" + item.getAuthor() +"' AND date='"+ item.getDate() +"'";
            Statement statement2 = con.createStatement();
            ResultSet rs2 = statement2.executeQuery(string);
            rs2.next();
            id = rs2.getInt("itemId");
            string = "INSERT INTO ActionHistory (itemId, action, date) VALUES (" + id +", '"+"remove"+"', default)";
            statement2.executeUpdate(string);
            rs2.close();
            return true;
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return false;
    }
    public boolean borrowBook(Item item) {
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            String string = "SELECT * FROM Items WHERE name='"+ item.getName() +"' AND author='" + item.getAuthor() +"' AND date='"+ item.getDate() +"'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(string);
            // Przeglądamy otrzymane wyniki
            rs.next();
            int id = rs.getInt("itemId");
            if(rs.getInt("currentAmount") > 0) {
            string = "UPDATE Items " +
                     "SET currentAmount=-1+( " +
                     "SELECT currentAmount " +
                     "FROM Items " +
                     "WHERE itemId="+id+") " +
                     "WHERE itemId="+id;
            statement.executeUpdate(string);
            rs.close();
            string = "SELECT * FROM Items WHERE name='"+ item.getName() +"' AND author='" + item.getAuthor() +"' AND date='"+ item.getDate() +"'";
            Statement statement2 = con.createStatement();
            ResultSet rs2 = statement2.executeQuery(string);
            rs2.next();
            id = rs2.getInt("itemId");
            string = "INSERT INTO ActionHistory (itemId, action, date) VALUES (" + id +", '"+"borrow"+"', default)";
            statement2.executeUpdate(string);
            rs2.close();
            return true;
            }
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return false;
    }
    public boolean returnBook(Item item) {
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            String string = "SELECT * FROM Items WHERE name='"+ item.getName() +"' AND author='" + item.getAuthor() +"' AND date='"+ item.getDate() +"'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(string);
            // Przeglądamy otrzymane wyniki
            rs.next();
            int id = rs.getInt("itemId");
            if(rs.getInt("currentAmount") < rs.getInt("totalAmount")) {
            string = "UPDATE Items " +
                     "SET currentAmount=1+( " +
                     "SELECT currentAmount " +
                     "FROM Items " +
                     "WHERE itemId="+id+") " +
                     "WHERE itemId="+id;
            statement.executeUpdate(string);
            rs.close();
            string = "SELECT * FROM Items WHERE name='"+ item.getName() +"' AND author='" + item.getAuthor() +"' AND date='"+ item.getDate() +"'";
            Statement statement2 = con.createStatement();
            ResultSet rs2 = statement2.executeQuery(string);
            rs2.next();
            id = rs2.getInt("itemId");
            string = "INSERT INTO ActionHistory (itemId, action, date) VALUES (" + id +", '"+"return"+"', default)";
            statement2.executeUpdate(string);
            rs2.close();
            return true;
            }
            else {
                
            }
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return false;
    }
    
    public String stringToDate(String date) {
        int indexS = 0;
        int indexE = date.indexOf('.', indexS);
        String temp = date.substring(indexS, indexE);
        indexS = indexE + 1;
        indexE = date.indexOf('.', indexS);
        temp = date.substring(indexS, indexE) +"-"+ temp;
        indexS = indexE + 1;
        temp = date.substring(indexS) +"-"+ temp;
        return temp;
    }
    
    public String dateToString(String date) {
        int indexS = 0;
        int indexE = date.indexOf('.', indexS);
        String temp = date.substring(indexS, indexE);
        indexS = indexE + 1;
        indexE = date.indexOf('.', indexS);
        temp = date.substring(indexS, indexE) +"."+ temp;
        indexS = indexE + 1;
        temp = date.substring(indexS) +"."+ temp;
        return temp;
    }
}