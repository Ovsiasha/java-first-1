package entity;

public class PopularProducts {
    private String productName;
    private int quantity;

    public PopularProducts(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public PopularProducts() {
    }

    public String getProductName() {
        return productName;
    }

    public PopularProducts setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public PopularProducts setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "PopularProducts{" + "productName=" + productName + ", quantity=" + quantity + '}';
    }
}
