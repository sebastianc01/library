/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tests;

import model.Item;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Sebastian
 */
public class ItemTest {
    
    /**
     * Tests all set and get methods of class Item.
     * @param name name of the book
     * @param author author of the book
     * @param date release date
     * @param currentAmount how many copies currently available
     * @param totalAmount total amount of copies
     */
    @ParameterizedTest
    @CsvSource(value={"Kordian:Slowacki:2500:1:1", "Lalka:Boleslaw Prus:10.12.2100:2:5"}, delimiter=':')
    void testSetAndGet(String name, String author, String date, int currentAmount, int totalAmount) {
        Item item2 = new Item();
        item2.setName(name);
        item2.setAuthor(author);
        item2.setDate(date);
        item2.setCurrentAmount(currentAmount);
        item2.setTotalAmount(totalAmount);
        assertEquals(item2.getName(), name);
        assertEquals(item2.getAuthor(), author);
        assertEquals(item2.getDate(), date);
        assertEquals(item2.getTotalAmount(), totalAmount);
        assertEquals(item2.getCurrentAmount(), currentAmount);
    }
    
    /**
     * Tests method "equals" of the class Item.
     * @param name name of the book
     * @param author author of the book
     * @param date release date
     * @param currentAmount how many copies currently available
     * @param totalAmount total amount of copies
     */
    @ParameterizedTest
    @CsvSource(value={"Kordian:Slowacki:2500:1:1", "Lalka:Boleslaw Prus:10.12.2100:2:5"}, delimiter=':')
    void testEquals(String name, String author, String date, int currentAmount, int totalAmount) {
        Item item = new Item(name, author, date, currentAmount, totalAmount);
        Item item2 = new Item();
        item2.setName(name);
        item2.setAuthor(author);
        item2.setDate(date);
        item2.setCurrentAmount(currentAmount);
        item2.setTotalAmount(totalAmount);
        assertTrue(item.equals(item2));
        item2.setAuthor(author + author);
        assertFalse(item.equals(item2));
    }
    /**
     * Tests method "hash" of the class Item.
     * @param name name of the book
     * @param author author of the book
     * @param date release date
     * @param currentAmount how many copies currently available
     * @param totalAmount total amount of copies
     */
    @ParameterizedTest
    @CsvSource(value={"Kordian:Slowacki:2500:1:1", "Lalka:Boleslaw Prus:10.12.2100:2:5"}, delimiter=':')
    void testHash(String name, String author, String date, int currentAmount, int totalAmount) {
        Item item = new Item(name, author, date, currentAmount, totalAmount);
        Item item2 = new Item(name, author, date, currentAmount, totalAmount);
        assertTrue(item.hashCode() == item2.hashCode());
        item2.setAuthor(author + author);
        assertFalse(item.hashCode() == item2.hashCode());
    }
}
