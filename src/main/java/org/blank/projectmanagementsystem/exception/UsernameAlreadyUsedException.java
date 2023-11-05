package org.blank.projectmanagementsystem.exception;

public class UsernameAlreadyUsedException extends RuntimeException{
    public UsernameAlreadyUsedException(String message) {
        super(message);
    }
}
