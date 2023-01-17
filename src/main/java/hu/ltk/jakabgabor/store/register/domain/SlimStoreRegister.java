package hu.ltk.jakabgabor.store.register.domain;
import hu.ltk.jakabgabor.store.register.interfaces.ApplicationInterface;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SlimStoreRegister implements ApplicationInterface {
    private StorePersistenceType persistenceType;
    private List<StoreItem> storageList;
    private Path storageFile = Paths.get("files/storage.csv");

    public SlimStoreRegister() {
        storageList = new ArrayList<>();
    }

    public List<StoreItem> getStorageList() {
        return storageList;
    }

    public void setPersistenceType(StorePersistenceType persistenceType) {
        this.persistenceType = persistenceType;
    }

    @Override
    public void createProduct(String productName) {
        if(persistenceType == StorePersistenceType.InMemory) {
            storageList.add(new StoreItem(productName));
            return;
        }

        if(persistenceType == StorePersistenceType.File) {
            storageList.add(new StoreItem(productName));
            System.out.println(storageList);
            try(BufferedWriter writer = Files.newBufferedWriter((storageFile))) {
                for (StoreItem storeItem : storageList) {
                    String itemString = "";
                    itemString += storeItem.getProductName() + ",";
                    itemString += storeItem.getPiece() + ",";


                    writer.write(itemString);
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        System.out.println(getStorageList());
    }

    @Override
    public void buyProductItem(String productName, int numberOfProduct) {
        StoreItem buyableProduct = checkProductIsBuyable(productName);

        if(buyableProduct == null) {
            System.out.println("Nincs ilyen a raktárban.") ;
            return;
        };

       buyableProduct.setPiece(buyableProduct.getPiece() + numberOfProduct);
    }

    @Override
    public int sellProductItem(String productName, int numberOfProduct) {
        StoreItem buyableProduct = checkProductIsBuyable(productName);

        if(buyableProduct == null) {
            System.out.println("Nincs ilyen a raktárban!") ;
            return 0;
        };

        int salableProducts = buyableProduct.getPiece() - numberOfProduct < 0
                ? buyableProduct.getPiece() : numberOfProduct;

        buyableProduct.setPiece(buyableProduct.getPiece() - salableProducts);

        System.out.println("Sikeresen megvásároltál " + salableProducts + " terméket!");
        return salableProducts;
    }

    private StoreItem checkProductIsBuyable(String productName) {
        StoreItem buyableProduct = storageList.stream()
                .filter(storeItem -> storeItem.getProductName()
                        .equals(productName)).findFirst().orElse(null);

        return buyableProduct;
    }

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
        List<String> storageStrings = new ArrayList<>();
        try {
            storageStrings = Files.readAllLines(storageFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        storageList = storageStrings.stream().map(storageString -> {
            String[] storeData = storageString.split(",");
            StoreItem newStoreItem = new StoreItem(storeData[0]);
            newStoreItem.setPiece(Integer.parseInt(storeData[1]));

            return newStoreItem;
        }).collect(Collectors.toList());
    }
}
