package it.epicode.feste.services.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super("Entity with id " + id + " not found.");
    }
}
