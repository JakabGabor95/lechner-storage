package hu.ltk.jakabgabor.store.register.exceptions;

public class ItemNotAvailableException extends RuntimeException{
    public ItemNotAvailableException() {
        System.out.println("A termék nem elérhető");
    }
}
