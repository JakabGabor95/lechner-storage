package hu.ltk.jakabgabor.store.register.domain;
import hu.ltk.jakabgabor.store.register.interfaces.ApplicationInterface;
import hu.ltk.jakabgabor.store.register.interfaces.PersistenceInterface;
import hu.ltk.jakabgabor.store.register.services.StoreItemFileService;
import hu.ltk.jakabgabor.store.register.services.StoreItemInMemoryService;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SlimStoreRegister implements ApplicationInterface {
    private PersistenceInterface persistenceInterface;

    public void setPersistenceType(StorePersistenceType persistenceType) {
        if (persistenceType.equals(StorePersistenceType.InMemory)) {
            persistenceInterface = new StoreItemInMemoryService();
        }else {
            persistenceInterface = new StoreItemFileService();
        }
    }

    @Override
    public void createProduct(String productName) {
        persistenceInterface.saveItem(new StoreItem(productName, 0));
    }

    @Override
    public void buyProductItem(String productName, int numberOfProduct) {
      persistenceInterface.saveItem(new StoreItem(productName, numberOfProduct));
    }

    @Override
    public int sellProductItem(String productName, int numberOfProduct) {
        StoreItem foundedItem = persistenceInterface.loadItem(productName);

        int salableProducts = foundedItem.getPiece() - numberOfProduct < 0
                ? foundedItem.getPiece() : numberOfProduct;

        persistenceInterface.saveItem(new StoreItem(foundedItem.getProductName(), - salableProducts));
        System.out.println("Sikeresen megvásároltál " + salableProducts + " terméket!");
        return salableProducts;
    }
}
