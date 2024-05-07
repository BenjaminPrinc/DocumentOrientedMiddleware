package headquarter.processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import headquarter.model.ProductData;
import headquarter.model.WarehouseData;
import headquarter.repositories.ProductRepository;
import headquarter.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataProcessingService {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductRepository productRepository;

    public void processSingleData(String rawData){
        try {
            ObjectMapper mapper = new ObjectMapper();
            WarehouseData warehouseRaw = mapper.readValue(rawData, WarehouseData.class);

            WarehouseData warehouse = new WarehouseData(
                    warehouseRaw.getWarehouseID(), warehouseRaw.getWarehouseName(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                    warehouseRaw.getWarehouseAddress(), warehouseRaw.getWarehousePostalCode(),
                    warehouseRaw.getWarehouseCity(), warehouseRaw.getWarehouseCountry(),
                    warehouseRaw.getProductData());

            warehouseRepository.save(warehouse);

            ProductData[] productDataArray = warehouseRaw.getProductData();
            List<ProductData> productDataList = Arrays.asList(productDataArray);
            processProducts(productDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processMultipleData(String rawData){
        try {
            ObjectMapper mapper = new ObjectMapper();
            WarehouseData[] warehousesRaw = mapper.readValue(rawData, WarehouseData[].class);
            for (WarehouseData wd : warehousesRaw) {
                WarehouseData warehouse = new WarehouseData(
                        wd.getWarehouseID(), wd.getWarehouseName(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                        wd.getWarehouseAddress(), wd.getWarehousePostalCode(),
                        wd.getWarehouseCity(), wd.getWarehouseCountry(),
                        wd.getProductData());

                warehouseRepository.save(warehouse);

                ProductData[] productDataArray = wd.getProductData();
                List<ProductData> productDataList = Arrays.asList(productDataArray);
                processProducts(productDataList);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processContinuous(String rawData){
        try {
            ObjectMapper mapper = new ObjectMapper();
            WarehouseData[] warehousesRaw = mapper.readValue(rawData, WarehouseData[].class);
            for (WarehouseData wd : warehousesRaw) {
                Optional<WarehouseData> opWarehouse = warehouseRepository.findByWarehouseID(wd.getWarehouseID());
                if (opWarehouse.isPresent()) {
                    WarehouseData oldWarehouse = opWarehouse.get();
                    oldWarehouse.setWarehouseName(wd.getWarehouseName());
                    oldWarehouse.setWarehouseAddress(wd.getWarehouseAddress());
                    oldWarehouse.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
                    oldWarehouse.setWarehousePostalCode(wd.getWarehousePostalCode());
                    oldWarehouse.setWarehouseCity(wd.getWarehouseCity());
                    oldWarehouse.setWarehouseCountry(wd.getWarehouseCountry());
                    oldWarehouse.setProductData(wd.getProductData());
                    warehouseRepository.save(oldWarehouse);

                    ProductData[] productDataArray = wd.getProductData();
                    List<ProductData> productDataList = Arrays.asList(productDataArray);
                    processProducts(productDataList);
                } else {
                    WarehouseData warehouse = new WarehouseData(
                            wd.getWarehouseID(), wd.getWarehouseName(),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                            wd.getWarehouseAddress(), wd.getWarehousePostalCode(),
                            wd.getWarehouseCity(), wd.getWarehouseCountry(),
                            wd.getProductData());

                    warehouseRepository.save(warehouse);

                    ProductData[] productDataArray = wd.getProductData();
                    List<ProductData> productDataList = Arrays.asList(productDataArray);
                    processProducts(productDataList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processProducts(List<ProductData> productDataList){
        for(ProductData productData : productDataList){
            Optional<ProductData> exsistingProduct = productRepository.findByProductID(productData.getProductID());
            if(exsistingProduct.isPresent()){
                ProductData pTmp = exsistingProduct.get();
                pTmp.setProductName(productData.getProductName());
                pTmp.setWarehouseID(productData.getWarehouseID());
                pTmp.setProductCategory(productData.getProductCategory());
                pTmp.setProductUnit(productData.getProductUnit());
                pTmp.setProductQuantity(productData.getProductQuantity());
                productRepository.save(pTmp);
            } else {
                ProductData newProduct = new ProductData(productData.getProductID(), productData.getWarehouseID(), productData.getProductName(),
                        productData.getProductCategory(), productData.getProductQuantity(), productData.getProductUnit());
                productRepository.save(newProduct);
            }
        }
    }
}
