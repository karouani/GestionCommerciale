package com.sdzee.forms;

public class FormValidationException extends Exception {
    private static final long serialVersionUID = 1L;

    /*
     * Constructeur
     */
    public FormValidationException( String message ) {
        super( message );
    }
}