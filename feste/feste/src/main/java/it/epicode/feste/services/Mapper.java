package it.epicode.feste.services;

public interface Mapper <D, S> {
    S map(D input);
}
