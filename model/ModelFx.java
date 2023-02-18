/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import pl.polsl.exceptions.CorrectInput;
import pl.polsl.exceptions.IncorrectDateException;
import pl.polsl.exceptions.ForbiddenCharactersException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static pl.polsl.exceptions.CorrectDate.loadCorrectDate;

/**
 * The class contains a vector of current data, allows to read data, save and change them, changed for JavaFX.
 * @author sebastianc01
 */
public class ModelFx extends Model {
    private List<ItemFx> data = new ArrayList<ItemFx>();
    /**
     * It returns the whole vector of data
     * @return data
     */
    public List<ItemFx> getDataFx() {
        return data;
    }
    /**
     * Constructor
     */
    public ModelFx() {
    }
    
    /**
     * The method tries to save data from the array of strings with exceptions.
     * @param element array of the strings
     */
    protected void saveElement(String[] element) throws ArrayIndexOutOfBoundsException, IncorrectDateException, NullPointerException {
        ItemFx current = new ItemFx(element[0], element[1], element[2], Integer.parseInt(element[3]), Integer.parseInt(element[4]));
        
        current.setName(element[0]);
        current.setAuthor(element[1]);
        current.setDate(loadCorrectDate(element[2]));
        current.setCurrentAmount(Integer.parseInt(element[3]));
        current.setTotalAmount(Integer.parseInt(element[4]));
        data.add(current);
    }
    
    /**
     * It loads data to the vector from the file with exceptions.
     * @param fileString name of the file
     * @return true when it is successful, otherwise false.
     */
    public boolean loadFileToData(String fileString) throws FileNotFoundException, ArrayIndexOutOfBoundsException, IncorrectDateException, NullPointerException {
        File file = new File(fileString);
        Scanner read = new Scanner(file);
        while(read.hasNext()) {
            String[] element = read.nextLine().split("%@");
            saveElement(element);
            }
        return true;
    }
    
    /**
     * The method adds a new item to the vector, but when it is already inside increments totalAmount and currentAmount of the item passed as an argument.
     * @param item new item
     */
    public void addItem(ItemFx... item) {
        for(ItemFx it:item) {
            boolean found  = false;
            for(int i = 0; i < data.size(); ++i) {
                if(data.get(i).hashCode() == it.hashCode()) {
                    found = true;
                    data.get(i).setTotalAmount(data.get(i).getTotalAmount() + 1);
                    data.get(i).setCurrentAmount(data.get(i).getCurrentAmount() + 1);
                 i = data.size();
                }
            }
            if(Boolean.FALSE.equals(found)) {
                it.setCurrentAmount(1);
                it.setTotalAmount(1);
                data.add(it);
            }
        }
    }
    
    /**
     * The method removes the item from the vector by decrementing totalAmount and currentAmount of the item passed as an argument.
     * When we try to remove the item when totalAmount is equal to 1, the item is completely deleted from the vector.
     * @param item existing item
     */
    public void removeItem(ItemFx item) {
        for(int i = 0; i < data.size(); ++i) {
            if(data.get(i).equals(item)) {
                if(data.get(i).getCurrentAmount() > 0 && data.get(i).getTotalAmount() > 1) {
                    data.get(i).setTotalAmount(data.get(i).getTotalAmount() - 1);
                    data.get(i).setCurrentAmount(data.get(i).getCurrentAmount() - 1);
                }
                else if(data.get(i).getTotalAmount() > 1) {
                    data.get(i).setTotalAmount(data.get(i).getTotalAmount() - 1);
                }
                else {
                    data.remove(i);
                }
                i = data.size();
            }
        }
    }
    
    /**
     * The method tries to decrement the value of currentAmount of the item passed as an argument.
     * @param item existing item
     * @return true when it is successful, otherwise false.
     */
    public boolean borrowBook(ItemFx item) {
        for(int i = 0; i < data.size(); ++i) {
            if(data.get(i).hashCode() == item.hashCode()) {
                if(data.get(i).getCurrentAmount() > 0) {
                    data.get(i).setCurrentAmount(data.get(i).getCurrentAmount() - 1);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    
    /**
     * The method tries to increment the value of currentAmount of the item passed as an argument.
     * @param item existing item
     * @return true when it is successful, otherwise false.
     */
    public boolean returnBook(ItemFx item) {
        for(int i = 0; i < data.size(); ++i) {
            if(data.get(i).hashCode() == item.hashCode()) {
                if(data.get(i).getCurrentAmount() < data.get(i).getTotalAmount()) {
                    data.get(i).setCurrentAmount(data.get(i).getCurrentAmount() + 1);
                    return true;
                }
                return false;
            }
        }
        return false;
                
    }
    
    /**
     * The method loads an item's data from the passed as an argument strings.
     * @param args array of the string
     * @return initialized object of Item
     */
    @Override
    public ItemFx loadItemDataFromStrings(String[] args) {
        ItemFx next = new ItemFx();
        try {
            next.setName(lookForSpaces(CorrectInput.loadCorrectInput(args[1])));
            next.setAuthor(lookForSpaces(CorrectInput.loadCorrectInput(args[2])));
            next.setDate(loadCorrectDate(lookForSpaces(CorrectInput.loadCorrectInput(args[3]))));
        } catch (ForbiddenCharactersException | IncorrectDateException e) {
            return null;
        }
        return next;
    }
    
    /**
     * The method saves data into the file passed as an argument.
     * @param file name of the file
     */
    public void saveDataToFile(String file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for(int i = 0; i < data.size(); ++i) {
            if(!(data.get(i).getName().isBlank()||data.get(i).getName().isEmpty()) ||
               !(data.get(i).getAuthor().isBlank()||data.get(i).getAuthor().isEmpty()) ||
               !(data.get(i).getDate().isBlank()||data.get(i).getDate().isEmpty())
                ){
            String item = data.get(i).getName() + "%@";
            item = item + data.get(i).getAuthor() + "%@";
            item = item + data.get(i).getDate() + "%@";
            item = item + data.get(i).getCurrentAmount() + "%@";
            item = item + data.get(i).getTotalAmount() + "\n";
            writer.write(item);
            }
        }
        writer.close();
    }
    
}
