package Tests;


import exceptions.CorrectInput;
import static exceptions.CorrectInput.loadCorrectInput;
import exceptions.ForbiddenCharactersException;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sebastian
 */
public class ForbiddenCharactersExceptionTest {
    @Test
    public void testBlank() {
        String blankString = "    ";
        try {
        String result = loadCorrectInput(blankString);
        fail("String cannot be blank.");
        } catch(ForbiddenCharactersException e) {
        }
    }
    
    @Test
    public void testEmpty() {
        String emptyString = new String();
        try{
            loadCorrectInput(emptyString);
            fail("String cannot be empty.");
        } catch(ForbiddenCharactersException e) {
            
        }
    }
    
    @Test
    public void testForbiddenCharacters() {
        String wrongCharactersString = "sdsdf%@fhbhdg";
        try {
            loadCorrectInput(wrongCharactersString);
            fail("Forbidden sequence.");
        } catch(ForbiddenCharactersException e) {
            
        }
    }
}
