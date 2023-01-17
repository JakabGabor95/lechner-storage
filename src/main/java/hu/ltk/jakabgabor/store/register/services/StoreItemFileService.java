package hu.ltk.jakabgabor.store.register.services;

import hu.ltk.jakabgabor.store.register.domain.StoreItem;
import hu.ltk.jakabgabor.store.register.exceptions.ItemNotAvailableException;
import hu.ltk.jakabgabor.store.register.interfaces.PersistenceInterface;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StoreItemFileService implements PersistenceInterface {
    private Path storageFile = Paths.get("files/storage.csv");
    private List<StoreItem> storageList;

    public StoreItemFileService() {
        this.storageList = new ArrayList<>();
    }

    @Override
    public StoreItem loadItem(String productName) {
        StoreItem buyableProduct = checkProductIsBuyable(productName);
        if (buyableProduct == null) {
            throw new ItemNotAvailableException();
        }
        return buyableProduct;
    }

    @Override
    public void saveItem(StoreItem item) {
        StoreItem buyableProduct = checkProductIsBuyable(item.getProductName());
        if(buyableProduct == null) {
            storageList.add(item);
            writeToFile();
            return;
        }

        buyableProduct.setPiece(buyableProduct.getPiece() + item.getPiece());
        writeToFile();
    }

    public void writeToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter((storageFile))) {
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
    }

    private StoreItem checkProductIsBuyable(String productName) {
        try {
            storageList = Files.readAllLines(storageFile).stream().map(storageString -> {
                String[] storeData = storageString.split(",");
                StoreItem newStoreItem = new StoreItem(storeData[0], Integer.parseInt(storeData[1]));
                return newStoreItem;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

       return storageList.stream().filter(storeItem -> {
            return storeItem.getProductName()
                    .equals(productName);
        }).findFirst().orElse(null);
    }



}
