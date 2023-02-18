/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.exceptions;

/**
 * Exception class, used when the user attempts to use incorrect data.
 * @author sebastianc01
 */
public class ForbiddenCharactersException extends Exception {
    /**
     * Constructor
     * @param msg message
     */
    public ForbiddenCharactersException(String msg) {
        super(msg);
    }
}
