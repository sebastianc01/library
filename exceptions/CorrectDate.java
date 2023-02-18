/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.exceptions;

import pl.polsl.exceptions.IncorrectDateException;

/**
 * The class that is responsible for the correct date input.
 * @author sebastianc01
 */
public class CorrectDate {
    /**
     * Takes string as a parameter. If it is correct, method returns the same string, otherwise throws an exception.
     * @param date string, contains a possible date
     * @return correct date
     * @throws IncorrectDateException is thrown when input is not correct
     */
    public static String loadCorrectDate(String date) throws IncorrectDateException {
        if(date.contains(".") == false) {
            if(isNumeric(date) == true && Integer.parseInt(date) >= 0) {
                return date;
            }
            else {
                throw new IncorrectDateException("Data jest nieprawidlowa.");
            }
        }
        else {
            int indexS;
            int indexE = -1;
            int elements = 0;
            String str;
            do {
                indexS = indexE + 1;
                int tempIndex = date.indexOf(".", indexS);
                if(tempIndex != -1) {
                    indexE = tempIndex;
                }
                else {
                    indexE = date.length();
                }
                str = date.substring(indexS, indexE);
                if(elements == 0) {
                    if(isNumeric(str) == false) {
                        throw new IncorrectDateException("Nieprawidlowa data.");
                    }
                    else {
                        int temp = Integer.parseInt(str);
                        if(temp<1 || temp>31) {
                            throw new IncorrectDateException("Nieprawidlowy dzien.");
                        }
                    }
                    ++elements;
                }
                else if(elements == 1) {
                    if(isNumeric(str) == false) {
                        throw new IncorrectDateException("Nieprawidlowa data.");
                    }
                    else {
                        int temp = Integer.parseInt(str);
                        if(temp<1 || temp>12) {
                            throw new IncorrectDateException("Nieprawidlowy miesiac.");
                        }
                    }
                    ++elements;
                }
                else if(elements == 2) {
                    if(isNumeric(str) == false) {
                        throw new IncorrectDateException("Nieprawidlowa data.");
                    }
                    else {
                        int temp = Integer.parseInt(str);
                        if(temp<1 || temp>2022) {
                            throw new IncorrectDateException("Nieprawidlowy rok.");
                        }
                    }
                    ++elements;
                }
                else {
                    throw new IncorrectDateException("Nieprawidlowa data.");
                }
            } while(indexE < date.length()); //date.indexOf(".", indexE) != -1

        }
        return date;
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
                //System.out.printf("\n\"%s\" nie jest liczba.\n", string);
                return false;
            }
            return true;
        }
    }
}
