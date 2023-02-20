/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tests;

import static exceptions.CorrectDate.loadCorrectDate;
import exceptions.IncorrectDateException;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Exception class, used when the user attempts to use incorrect date.
 * @author Sebastian
 */
public class IncorrectDateExceptionTest {
    /**
     * Tests possible incorrect dates
     * @param date exemplary date
     */
    @ParameterizedTest
    @CsvSource(value={"-500", "-1", "35.10.1999", "01.15.1999", "-5.10.1999", "02.-10.2000", ".10.1999", "01..1999", "..1999", "00.10.1999", "10.00.1999", "10.10.", "10.10.0", ".."})
    void testIncorrectDates(String date) {
        String temp;
        try {
            temp = loadCorrectDate(date);
            fail("Nieprawidlowa data.");
        } catch (IncorrectDateException e) {
            
        }
    }
    /**
     * Tests possible correct dates
     * @param date exemplary date
     */
    @ParameterizedTest
    @CsvSource(value={"01.01.1995", "31.12.123", "05.06.2008", "1995", "0", "2020"})
    void testCorrectDates(String date) {
        String temp;
        try{
        temp = loadCorrectDate(date);
        } catch (IncorrectDateException e) {
            fail("Odrzucono prawidlowa date.");
        }
    }
}
