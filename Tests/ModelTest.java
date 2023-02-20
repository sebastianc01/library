/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tests;
import static exceptions.CorrectDate.isNumeric;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import model.Item;
import model.Model;
import static model.Model.lookForSpaces;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
/**
 *
 * @author Sebastian
 */
public class ModelTest {
    private Model model;
    
    @BeforeEach
    public void setUp() {
        model = new Model();
    }
    
    /**
     * Tests addItem method.
     */
    @Test
    public void testAddItem() {
        Item item = new Item();
        item.setName("123");
        item.setAuthor("Adam");
        item.setDate("20.04.1995");
        item.setCurrentAmount(1);
        item.setTotalAmount(1);
        model.addItem(item);
        ArrayList<Item> temp = model.getData();
        assertEquals(item, temp.get(0));
    }
    
    /**
     * Tests addItem with multiple arguments.
     * @param name name of the book
     * @param author author of the book
     * @param date release date
     */
    @ParameterizedTest
    @CsvSource(value={"abc:Adam:1995", "Kubus Puchatek:Robert Kubica:10.12.1992", "123:XYZ:03.05.996"}, delimiter=':')
    public void testAddItemMoreArguments(String name, String author, String date) {
        Item item = new Item();
        item.setName("123");
        item.setAuthor("Adam");
        item.setDate("20.04.1995");
        model.addItem(item, item, item, item);
        ArrayList<Item> temp = model.getData();
        assertEquals(temp.get(0).getCurrentAmount(), 4);
        assertEquals(temp.get(0).getTotalAmount(), 4);
    }
    
    /**
     * Tests method loadFile.
     * @param name name of the book
     * @param author author of the book
     * @param date release date
     * @param currentAmount how many copies currently available
     * @param totalAmount total amount of copies
     */
    @ParameterizedTest
    @CsvSource(value={"abc:Adam:1995:1:1", "Kubus Puchatek:Robert Kubica:10.12.1992:1:2", "123:XYZ:03.05.996:4:4"}, delimiter=':')
    public void testLoadFile(String name, String author, String date, int currentAmount, int totalAmount) {
        String file = "testLoadFile.txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String item = name + "%@";
            item = item + author + "%@";
            item = item + date + "%@";
            item = item + currentAmount + "%@";
            item = item + totalAmount + "\n";
            writer.write(item);
            writer.close();
        } catch(IOException e) {
            fail("Blad");
        }
        model.loadFile(file);
        ArrayList<Item> temp = model.getData();
        try{
        assertEquals(temp.get(0).getDate(), date);
        assertEquals(temp.get(0).getName(), name);
        assertEquals(temp.get(0).getAuthor(), author);
        assertEquals(temp.get(0).getCurrentAmount(), currentAmount);
        assertEquals(temp.get(0).getTotalAmount(), totalAmount);
        } catch(IndexOutOfBoundsException e) {
            fail(e);
        }
    }
    
    /**
     * Tests save method.
     * @param name name of the book
     * @param author author of the book
     * @param date release date
     */
    @ParameterizedTest
    @CsvSource(value={"abc:Adam:1995", "Kubus Puchatek:Robert Kubica:10.12.1992", "123:XYZ:03.05.996"}, delimiter=':')
    public void testSave(String name, String author, String date) {
        Item item = new Item(name, author, date, 1, 1);
        //item.setCurrentAmount(1);
        //item.setTotalAmount(1);
        Item item2 = item;
        String s = author+author;
        fail(s);
        System.out.printf("%s+%s", item, item2);
        item2.setAuthor(author + author);
        model.addItem(item);
        model.addItem(item2);
        model.saveData("testfile.txt");
        model.loadFile("testfile.txt");
        assertEquals(model.getData().get(0), item);
        assertEquals(model.getData().get(1), item);
    }
    
    /**
     * Tests removeItem method
     */
    @Test
    public void testRemoveItem() {
        Item item = new Item();
        item.setName("123");
        item.setAuthor("Adam");
        item.setDate("20.04.1995");
        item.setCurrentAmount(1);
        item.setTotalAmount(1);
        model.addItem(item);
        ArrayList<Item> temp = model.getData();
        assertTrue(temp.size() == 1);
        model.removeItem(item);
        assertTrue(temp.isEmpty());
        model.addItem(item);
        model.addItem(item);
        model.removeItem(item);
        temp = model.getData();
        assertTrue(temp.size() == 1);
        Item item2 = new Item();
        item2.setAuthor("asd");
        item2.setDate("20.04.1995");
        item2.setCurrentAmount(1);
        item2.setTotalAmount(1);
        item2.setName("123");
        model.removeItem(item2);
        temp = model.getData();
        assertTrue(temp.size() == 1);
        Item item3 = null;
        model.removeItem(item3);
        assertTrue(temp.size() == 1);
    }
    
    /**
     * Tests borrowBook method
     */
    @Test
    public void testBorrowBook() {
        Item item = new Item();
        item.setName("123");
        item.setAuthor("Adam");
        item.setDate("20.04.1995");
        item.setCurrentAmount(1);
        item.setTotalAmount(1);
        model.addItem(item);
        assertTrue(model.borrowBook(item));
        ArrayList<Item> temp = model.getData();
        assertTrue(temp.get(0).getCurrentAmount() == 0);
        assertFalse(model.borrowBook(item));
        item.setName("asd");
        assertFalse(model.borrowBook(item));
        Item item2 = null;
        assertFalse(model.borrowBook(item));
        
    }
    
    /**
     * Tests returnBook method
     */
    @Test
    public void testReturnBook() {
        Item item = new Item();
        item.setName("123");
        item.setAuthor("Adam");
        item.setDate("20.04.1995");
        item.setCurrentAmount(1);
        item.setTotalAmount(1);
        model.addItem(item);
        assertTrue(model.borrowBook(item));
        ArrayList<Item> temp = model.getData();
        assertTrue(temp.get(0).getCurrentAmount() == 0);
        assertFalse(model.borrowBook(item));
        item.setName("asd");
        assertFalse(model.borrowBook(item));
    }
    
    /**
     * Tests method saveFile.
     * @param name name of the book
     * @param author author of the book
     * @param date release date
     */
    @ParameterizedTest
    @CsvSource(value={"abc:Adam:1995", "Kubus Puchatek:Robert Kubica:10.12.1992", "123:XYZ:03.05.996"}, delimiter=':')
    public void testSaveData(String name, String author, String date) {
        Item item = new Item(name, author, date, 1, 1);
        model.addItem(item);
        String fileS = "testSaveData.txt";
        model.saveData(fileS);
        try {
        File file = new File(fileS);
        Scanner read = new Scanner(file);
            while(read.hasNext()) {
                String[] element = read.nextLine().split("%@");
                assertEquals(date, element[2]);
                assertEquals(name, element[0]);
                assertEquals(author, element[1]);
                assertEquals(1, Integer.parseInt(element[3]));
                assertEquals(1, Integer.parseInt(element[4]));
            }
        } catch(FileNotFoundException | NullPointerException | IndexOutOfBoundsException e) {
            fail(e);
        }
    }
    
    /**
     * Tests loadItemDataFromString method.
     */
    @Test
    public void testLoadItemDataFromStrings() {
        String[] temp = {"", "Politechnika", "Arkadiusz M.", "10.12.2021"}; //it is called from the command line, first argument does not matter in the test
        Item i = model.loadItemDataFromStrings(temp);
        assertEquals(temp[1],i.getName());
        assertEquals(temp[2],i.getAuthor());
        assertEquals(temp[3],i.getDate());
        String[] temp2 = {"", "Politechnika", "Arkadiusz M.", "00.00.00"};
        i = model.loadItemDataFromStrings(temp2);
        assertNull(i);
    }
    
    /**
     * Tests 5 possible date using isNumeric method.
     */
    @Test
    public void testIsNumeric() {
        String stringNumber = "15";
        if(isNumeric(stringNumber) == false) {
            fail("Blad, 15 to liczba.");
        }
        stringNumber = "0";
        if(isNumeric(stringNumber) == false) {
            fail("Blad, zero to liczba.");
        }
        stringNumber = "asd";
        if(isNumeric(stringNumber) == true) {
            fail("Blad, dane to nie liczba.");
        }
        stringNumber = "";
        if(isNumeric(stringNumber) == true) {
            fail("Blad, pusty string to nie liczba.");
        }
        stringNumber = " ";
        if(isNumeric(stringNumber) == true) {
            fail("Blad, spacja to nie liczba.");
        }
    }
    
    /**
     * Tests lookForSpaces method
     * @param input input string
     * @param expected expected result
     */
    @ParameterizedTest
    @CsvSource(value={"aaa_aaa:aaa aaa", "a__a:a  a", "aa\\_a:aa_a"}, delimiter=':')
    public void testLookForSpaces(String input, String expected) {
        if(lookForSpaces(input).equals(expected) == false) {
            fail("Podane napisy nie sa sobie rowne.");
        }
    }
}
