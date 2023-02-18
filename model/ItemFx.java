/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class contains all important attributes of the book and allows to modify it, changed for JavaFX.
 * @author sebastianc01
 */
public class ItemFx extends Item {

    protected StringProperty nameP;
    protected StringProperty authorP;
    protected StringProperty dateP;
    protected IntegerProperty currentAmountP;
    protected IntegerProperty totalAmountP;

    public ItemFx(String nameP, String authorP, String dateP, int currentAmountP, int totalAmountP) {
        this.nameP = new SimpleStringProperty(nameP);
        this.authorP = new SimpleStringProperty(authorP);
        this.dateP = new SimpleStringProperty(dateP);
        this.currentAmountP = new SimpleIntegerProperty(currentAmountP);
        this.totalAmountP = new SimpleIntegerProperty(totalAmountP);
    }
    /**
     * Constructor
     */
    public ItemFx() {}
    
     /**
     * Computes the hash values of given input objects.
     * @return the hash value of the input object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nameP);
        hash = 97 * hash + Objects.hashCode(this.authorP);
        hash = 97 * hash + Objects.hashCode(this.dateP);
        return hash;
    }
    
    @Override
    public String getName() {
        return nameP.get();
    }
    
    public StringProperty nameProperty() {
        return nameP;
    }
    @Override
    public void setName(String name) {
        this.nameP.set(name);
    }
    @Override
    public String getAuthor() {
        return authorP.get();
    }
    
    public StringProperty authorProperty() {
        return authorP;
    }
    @Override
    public void setAuthor(String author) {
        this.authorP.set(author);
    }
    @Override
    public String getDate() {
        return dateP.get();
    }
    
    public StringProperty dateProperty() {
        return dateP;
    }
    @Override
    public void setDate(String date) {
        this.dateP.set(date);
    }
    @Override
    public int getCurrentAmount() {
        return currentAmountP.get();
    }
    
    public IntegerProperty currentAmountProperty() {
        return currentAmountP;
    }
    @Override
    public void setCurrentAmount(int currentAmount) {
        this.currentAmountP.set(currentAmount);
    }
    @Override
    public int getTotalAmount() {
        return totalAmountP.get();
    }
    
    public IntegerProperty totalAmountProperty() {
        return totalAmountP;
    }
    @Override
    public void setTotalAmount(int totalAmount) {
        this.totalAmountP.set(totalAmount);
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
        final ItemFx other = (ItemFx) obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getAuthor().equals(other.getAuthor())) {
            return false;
        }
        if (!this.getDate().equals(other.getDate())) {
            return false;
        }
        return true;
    }
}
