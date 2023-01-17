package hu.ltk.jakabgabor.store.register.interfaces;

import hu.ltk.jakabgabor.store.register.domain.StoreItem;

public interface PersistenceInterface {
    StoreItem loadItem(String productName);
    void saveItem(StoreItem item);
}
