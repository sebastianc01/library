/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.exceptions;

/**
 * Exception class, used when the user attempts to use incorrect date.
 * @author sebastianc01
 */
public class IncorrectDateException extends Exception{
    public IncorrectDateException(String str) {
        super(str);
    }
}
