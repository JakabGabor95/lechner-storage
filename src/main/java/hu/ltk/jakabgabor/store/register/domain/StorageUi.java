package hu.ltk.jakabgabor.store.register.domain;

import hu.ltk.jakabgabor.store.register.interfaces.ApplicationInterface;

import java.util.Scanner;

public class StorageUi {
    private Scanner scanner;
    private SlimStoreRegister slimStoreRegister;

    public StorageUi(Scanner scanner, SlimStoreRegister slimStoreRegister) {
        this.scanner = scanner;
        this.slimStoreRegister = slimStoreRegister;
    }

    public void run() {
        boolean isRunning = true;
        chooseDataStorageMode();
        String choosedDataStorageMode = scanner.next();
        while (isRunning) {
            slimStoreRegister.setPersistenceType(choosedDataStorageMode.equals("m")
                    ? StorePersistenceType.InMemory : StorePersistenceType.File);
            printMenu();
            String command = scanner.next();
            switch (command) {
                case "l":
                    System.out.println("Add meg a termék nevét:");
                    String createdProductName = scanner.next();
                    slimStoreRegister.createProduct(createdProductName);
                    break;
                case "f":
                    System.out.println("Add meg a termék nevét:");
                    String boughtProductName = scanner.next();
                    System.out.println("Add meg a darabszámot:");
                    Integer boughtNumberOfProduct = scanner.nextInt();
                    slimStoreRegister.buyProductItem(boughtProductName, boughtNumberOfProduct);
                    break;
                case "v":
                    System.out.println("Add meg a vásárolni kívánt termék nevét:");
                    String soldProductName = scanner.next();
                    System.out.println("Add meg a darabszámot:");
                    Integer soldNumberOfProduct = scanner.nextInt();
                    slimStoreRegister.sellProductItem(soldProductName, soldNumberOfProduct);
                    break;
                default:
                    System.out.println("Érvénytelen parancs!");
                    isRunning = false;
                    break;
            }
            System.out.println("--------------------------------------");
        }
    }

    private void printMenu() {
        System.out.println("Válassz tevékenységet!");
        System.out.println("Létrehozás (l), árufeltöltés (f), vásárlás (v):");
    }

    private void chooseDataStorageMode() {
        System.out.println("Add meg az adat tárolási módot!");
        System.out.println("Fajl alapú(f) vagy memória alapú (m):");
    }

}
