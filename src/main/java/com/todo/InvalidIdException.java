package com.todo;

class InvalidIdException extends Exception {
    private String exception;

    InvalidIdException(String exception) {
        this.exception = exception;
    }

    public String toString() {
        return exception;
    }
}
