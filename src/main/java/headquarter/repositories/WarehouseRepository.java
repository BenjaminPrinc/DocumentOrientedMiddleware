package headquarter.repositories;

import headquarter.model.WarehouseData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WarehouseRepository extends MongoRepository<WarehouseData, String> {

    Optional<WarehouseData> findByWarehouseID(String warehouseID);

}
