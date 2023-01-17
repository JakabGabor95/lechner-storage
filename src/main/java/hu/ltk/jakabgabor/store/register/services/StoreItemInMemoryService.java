package hu.ltk.jakabgabor.store.register.services;

import hu.ltk.jakabgabor.store.register.domain.StoreItem;
import hu.ltk.jakabgabor.store.register.exceptions.ItemNotAvailableException;
import hu.ltk.jakabgabor.store.register.interfaces.PersistenceInterface;

import java.util.ArrayList;
import java.util.List;

public class StoreItemInMemoryService implements PersistenceInterface {
    private List<StoreItem> storageList;

    public StoreItemInMemoryService() {
        storageList = new ArrayList<>();
    }

    @Override
    public StoreItem loadItem(String productName) {
        StoreItem buyableProduct = checkProductIsBuyable(productName);
        if(buyableProduct == null) {
            throw new ItemNotAvailableException();
        };

        return buyableProduct;
    }

    @Override
    public void saveItem(StoreItem item) {
        StoreItem buyableProduct = checkProductIsBuyable(item.getProductName());
        if(buyableProduct == null){
            storageList.add(item);
            return;
        }

        buyableProduct.setPiece(buyableProduct.getPiece() + item.getPiece());
    }

    private StoreItem checkProductIsBuyable(String productName) {
        return storageList.stream()
                .filter(storeItem -> {
                    System.out.println(storeItem.getProductName());
                   return storeItem.getProductName()
                            .equals(productName);
                }).findFirst().orElse(null);
    }
}
