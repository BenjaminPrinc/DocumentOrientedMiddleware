package headquarter.model;

import org.springframework.data.annotation.Id;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WarehouseData {
	@Id
	private String warehouseID;

	private String warehouseName;
	private String timestamp;
	private String warehouseAddress;
	private int warehousePostalCode;
	private String warehouseCity;
	private String warehouseCountry;
	private ProductData[] productData;

	public WarehouseData() {}

	/**
	 * Constructor
	 */
	public WarehouseData(String warehouseID, String warehouseName, String timestamp, String warehouseAddress, int warehousePostalCode, String warehouseCity, String warehouseCountry, ProductData[] productData) {
		this.warehouseID = warehouseID;
		this.warehouseName = warehouseName;
		this.timestamp = timestamp;
		this.warehouseAddress = warehouseAddress;
		this.warehousePostalCode = warehousePostalCode;
		this.warehouseCity = warehouseCity;
		this.warehouseCountry = warehouseCountry;
		this.productData = productData;

	}
	
	/**
	 * Setter and Getter Methods
	 */
	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	public void setWarehouseCity(String warehouseCity) {
		this.warehouseCity = warehouseCity;
	}

	public void setWarehouseCountry(String warehouseCountry) {
		this.warehouseCountry = warehouseCountry;
	}

	public void setWarehousePostalCode(int warehousePostalCode) {
		this.warehousePostalCode = warehousePostalCode;
	}

	public String getWarehouseAddress() {
		return warehouseAddress;
	}

	public String getWarehouseCity() {
		return warehouseCity;
	}

	public String getWarehouseCountry() {
		return warehouseCountry;
	}

	public int getWarehousePostalCode() {
		return warehousePostalCode;
	}

	public void setProductData(ProductData[] products) {
		this.productData = products;
	}

	public ProductData[] getProductData() {
		return productData;
	}

	/**
	 * Methods
	 */
	@Override
	public String toString() {
		String info = String.format("Warehouse Info: ID = %s, timestamp = %s, name = %s, address = %s, postalCode = %d, city = %s, country = %s, existingProducts = %s", warehouseID, timestamp, warehouseName, warehouseAddress, warehousePostalCode, warehouseCity, warehouseCountry, productIDs());
		return info;
	}

	private String productIDs() {
		String products = "";
		for (int i = 0; i < productData.length; i++) {
			products += productData[i].getProductID() + "("+productData[i].getProductQuantity()+"), ";
		}
		return products;
	}
}
