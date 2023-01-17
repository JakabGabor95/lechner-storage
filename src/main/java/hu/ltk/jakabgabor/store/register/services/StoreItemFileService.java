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
            writeToFile(item);
            return;
        }

        buyableProduct.setPiece(buyableProduct.getPiece() + item.getPiece());
        writeToFile(buyableProduct);
    }

    private StoreItem checkProductIsBuyable(String productName) {
        List<String> storageStrings = new ArrayList<>();
        try {
            storageStrings = Files.readAllLines(storageFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

       return storageStrings.stream().map(storageString -> {
            String[] storeData = storageString.split(",");
            StoreItem newStoreItem = new StoreItem(storeData[0], Integer.parseInt(storeData[1]));
            return newStoreItem;
        }).collect(Collectors.toList()).stream().filter(storeItem -> {
            System.out.println(storeItem.getProductName());
            return storeItem.getProductName()
                    .equals(productName);
        }).findFirst().orElse(null);
    }

    private void writeToFile(StoreItem item) {
        try (BufferedWriter writer = Files.newBufferedWriter((storageFile), StandardOpenOption.APPEND)) {
            String itemString = "";
            itemString += item.getProductName() + ",";
            itemString += item.getPiece() + ",";
            writer.write(itemString);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
