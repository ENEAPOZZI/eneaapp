package it.epicode.feste.services.exception;

public class UnauthorizedAccessException extends ServiceException{
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
