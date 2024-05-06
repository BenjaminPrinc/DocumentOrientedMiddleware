package headquarter.repositories;

import headquarter.model.ProductData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductData, String> {
    Optional<ProductData> findByProductID(String productID);
    List<ProductData> findByWarehouseID(String warehouseID);
    List<ProductData> findByWarehouseIDAndProductCategory(String warehouseID, String productCategory);
}
