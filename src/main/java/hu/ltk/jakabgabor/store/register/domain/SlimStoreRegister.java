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
        System.out.println(foundedItem);

        int salableProducts = foundedItem.getPiece() - numberOfProduct < 0
                ? foundedItem.getPiece() : numberOfProduct;

        foundedItem.setPiece(foundedItem.getPiece() - salableProducts);
        System.out.println(foundedItem);
        persistenceInterface.saveItem(foundedItem);
        System.out.println("Sikeresen megvásároltál " + salableProducts + " terméket!");
        return salableProducts;
    }

//    private StoreItem checkProductIsBuyable(String productName) {
//        StoreItem buyableProduct = storageList.stream()
//                .filter(storeItem -> storeItem.getProductName()
//                        .equals(productName)).findFirst().orElse(null);
//
//        return buyableProduct;
//    }

    private void initStorageList(StorePersistenceType persistenceType) {
        if(persistenceType == StorePersistenceType.InMemory){
            return;
        }

        if (persistenceType == StorePersistenceType.File) {
            System.out.println("File");

            loadStorageListByFile();
            return;
        }
    }

    private void loadStorageListByFile() {
//        List<String> storageStrings = new ArrayList<>();
//        try {
//            storageStrings = Files.readAllLines(storageFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//        storageList = storageStrings.stream().map(storageString -> {
//            String[] storeData = storageString.split(",");
//            StoreItem newStoreItem = new StoreItem(storeData[0]);
//            newStoreItem.setPiece(Integer.parseInt(storeData[1]));
//
//            return newStoreItem;
//        }).collect(Collectors.toList());
    }
}
