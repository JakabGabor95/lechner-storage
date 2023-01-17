package hu.ltk.jakabgabor.store.register.domain;

import java.util.Objects;

public class StoreItem {
    private String productName;
    private Integer piece;

    public StoreItem(String productName) {
        this.productName = productName;
        this.piece = 0;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPiece() {
        return piece;
    }

    public void setPiece(Integer piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        return "StoreItem{" +
                "productName='" + productName + '\'' +
                ", piece=" + piece +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItem storeItem = (StoreItem) o;
        return Objects.equals(productName, storeItem.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }
}
