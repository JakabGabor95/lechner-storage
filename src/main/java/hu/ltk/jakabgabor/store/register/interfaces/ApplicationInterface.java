package hu.ltk.jakabgabor.store.register.interfaces;

import hu.ltk.jakabgabor.store.register.domain.StorePersistenceType;
import hu.ltk.jakabgabor.store.register.exceptions.ItemNotAvailableException;

public interface ApplicationInterface {
    void setPersistenceType(StorePersistenceType storePersistenceType);
      void createProduct(String productName);
    void buyProductItem(String productName, int numberOfProduct);
    int sellProductItem(String productName, int numberOfProduct) throws  ItemNotAvailableException;

}
