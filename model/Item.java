/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.model;

import java.util.Objects;

/**
 * The class contains all important attributes of the book and allows to modify it.
 * @author Sebastian Cyra
 */
public class Item {
    protected String name;
    protected String author;
    protected String date;
    protected int currentAmount;
    protected int totalAmount;

    /**
     * Constructor
     */
    public Item() {}
    
    public Item(String name, String author, String date, int currentAmount, int totalAmount) {
        this.name = name;
        this.author = author;
        this.date = date;
        this.currentAmount = currentAmount;
        this.totalAmount = totalAmount;
    }
    /**
     * Constructor with an argument
     * @param item existing object of Item
     */
    public Item(Item item) {
        this.name = item.name;
        this.author = item.author;
        this.date = item.date;
        this.currentAmount = item.currentAmount;
        this.totalAmount = item.totalAmount;
    }
    
    /**
     * Computes the hash values of given input objects.
     * @return the hash value of the input object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.author);
        hash = 19 * hash + Objects.hashCode(this.date);
        return hash;
    }

    /**
     * Compares two objects
     * @param obj object
     * @return true when the objects are the same, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
    /**
     * It sets date string
     * @param date new date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * It sets currentAmount
     * @param currentAmount new currentAmount
     */
    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * It sets totalAmount
     * @param totalAmount It sets totalAmount
     */
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * It returns date
     * @return current date
     */
    public String getDate() {
        return date;
    }

    /**
     * It returns currentAmount
     * @return current currentAmount
     */
    public int getCurrentAmount() {
        return currentAmount;
    }

    /**
     * It returns TotalAmount
     * @return current TotalAmount
     */
    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * It sets Author
     * @param author a new author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * It returns Author
     * @return current author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * It returns book's name
     * @return current name
     */
    public String getName() {
        return name;
    }

    /**
     * It sets a new book's name
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }
   
}
