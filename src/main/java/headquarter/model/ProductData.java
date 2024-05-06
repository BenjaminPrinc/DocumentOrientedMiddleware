package headquarter.model;

import org.springframework.data.annotation.Id;

public class ProductData {
    @Id
    private String productID;
    private String warehouseID;

    private String productName;
    private String productCategory;
    private int productQuantity;
    private String productUnit;

    public ProductData(){}

    public ProductData(String productID, String warehouseID, String productName, String productCategory, int productQuantity, String productUnit) {
        this.productID = productID;
        this.warehouseID = warehouseID;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productQuantity = productQuantity;
        this.productUnit = productUnit;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getWarehouseID() {return warehouseID;}
    public void setWarehouseID(String warehouseID) {this.warehouseID = warehouseID;}

    @Override
    public String toString() {
        String info = String.format("Product Info: ProductID = %s, Warehouse: WarehouseID = %s, ProductName = %s, ProductCategory = %s, ProductQuantity = %d, ProductUnit = %s",
                productID, warehouseID, productName, productCategory, productQuantity, productUnit );
        return info;
    }
}
