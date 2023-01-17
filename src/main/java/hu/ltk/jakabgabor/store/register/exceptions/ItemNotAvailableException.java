package hu.ltk.jakabgabor.store.register.exceptions;

public class ItemNotAvailableException extends Throwable{
    public ItemNotAvailableException() {
        System.out.println("A termék nem elérhető");
    }
}
