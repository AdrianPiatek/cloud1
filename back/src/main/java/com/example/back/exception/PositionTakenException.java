package com.example.back.exception;

public class PositionTakenException extends Exception{
    public PositionTakenException() {
        super("Move cannot be made position already taken");
    }
}
