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
 * The class contains a vector of current data, allows to read data, save and change them.
 * @author sebastianc01
 */
public class Model {
    private List<Item> data = new ArrayList<Item>();
    /**
     * It returns the whole vector of data
     * @return data
     */
    public List<Item> getData() {
        return data;
    }
    
    /**
     * Constructor
     */
    public Model() {}
    
    /**
     * It loads data to the vector from the file
     * @param fileString name of the file
     * @return true when it is successful, otherwise false.
     */
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
    
    /**
     * The method tries to save data from the array of strings
     * @param element array of the strings
     */
    protected void save(String[] element) {
        Item current = new Item();
        try {
        current.setName(element[0]);
        current.setAuthor(element[1]);
        current.setDate(loadCorrectDate(element[2]));
        current.setCurrentAmount(Integer.parseInt(element[3]));
        current.setTotalAmount(Integer.parseInt(element[4]));
        data.add(current);
        } catch(ArrayIndexOutOfBoundsException | IncorrectDateException e) {
            System.err.println(e.getMessage());
        }
        catch(NullPointerException e) {
            System.err.println("Nieprawidlowe dane.");
        }
    }
    
   
    
    /**
     * The method adds a new item to the vector, but when it is already inside increments totalAmount and currentAmount of the item passed as an argument.
     * @param item new item
     */
    public void addItem(Item... item) {
        for(Item it:item) {
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
    public void removeItem(Item item) {
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
    public boolean borrowBook(Item item) {
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
    public boolean returnBook(Item item) {
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
     * The method saves data into the file passed as an argument.
     * @param file name of the file
     * @return true when it is successful, otherwise false.
     */
    public boolean saveData(String file) {
        try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for(int i = 0; i < data.size(); ++i) {
            String item = data.get(i).getName() + "%@";
            item = item + data.get(i).getAuthor() + "%@";
            item = item + data.get(i).getDate() + "%@";
            item = item + data.get(i).getCurrentAmount() + "%@";
            item = item + data.get(i).getTotalAmount() + "\n";
            writer.write(item);
        }
        writer.close();
        } catch(IOException e) {
            System.err.println("Nie udalo sie zapisac danych.");
            return false;
        }
        return true;
    }
    
    /**
     * The method loads an item's data from the passed as an argument strings.
     * @param args array of the string
     * @return initialized object of Item
     */
    public Item loadItemDataFromStrings(String[] args) {
        Item next = new Item();
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
     * The method looks for the number in the string.
     * @param string searched string
     * @return true when it is a number, otherwise false.
     */
    public static boolean isNumeric(String string) {
        if(string.isBlank() || string.isEmpty()) {
            return false;
        }
        else {
            try {
                Integer.parseInt(string);
            } catch(NumberFormatException e) {
                System.out.printf("\n\"%s\" nie jest liczba.\n", string);
                return false;
            }
            return true;
        }
    }
    
    /**
     * The method looks for '_' characters and transforms them into ' '.
     * There is an option to save '_' character and avoid replacing them by space.
     * Before '_' user has to place '\' character, so it should look like this "\_".
     * In the following situation '\' will be removed, but '_' will not be changed.
     * @param string searched string
     * @return transformed string
     */
    public static String lookForSpaces(String string) {
        int index = 0;
        while(string.indexOf("_", index) != -1) {
            int i = 0;
            i = string.indexOf("_", index);
            if(string.charAt(i - 1) == '\\') {
                string = string.substring(0, i - 1) + string.substring(i);
                index = i + 1;
            }
            else {
                string = string.substring(0, i) + ' ' + string.substring(i + 1);
                index = i + 1;
            }
        }
        return string;
    }
}