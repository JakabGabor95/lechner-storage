package hu.ltk.jakabgabor.store.register;

import hu.ltk.jakabgabor.store.register.domain.SlimStoreRegister;
import hu.ltk.jakabgabor.store.register.domain.StorageUi;

import java.util.Scanner;

public class StoreRegisterSystemApplication {
    public static void main(String[] args) {
        StorageUi storageUi = new StorageUi(new Scanner(System.in), new SlimStoreRegister());

        storageUi.run();

    }
}
