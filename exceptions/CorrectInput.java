/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.exceptions;

import pl.polsl.exceptions.ForbiddenCharactersException;

/**
 * The class that is responsible for the correct input.
 * @author sebastianc01
 */
public class CorrectInput {
    /**
     * It checks the input data, when the input is wrong throws an exception.
     * @param input string
     * @return string
     * @throws ForbiddenCharactersException checks if the string is not empty or blank.
     * It cannot contain "%@", because this is the way how the data are divided inside the file.
     */
    public static String loadCorrectInput(String input) throws ForbiddenCharactersException {
        if(input.isBlank() || input.isEmpty()) {
            throw new ForbiddenCharactersException("Dane wejsciowe sa puste.");
        }
        else if(input.contains("%@") == true) {
            throw new ForbiddenCharactersException("Zakazana sekwencja znajduje sie w danych wejsciowych.");
        }
        else {
            return input;
        }
    }
}
