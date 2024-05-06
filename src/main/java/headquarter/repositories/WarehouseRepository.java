package headquarter.repositories;

import headquarter.model.WarehouseData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarehouseRepository extends MongoRepository<WarehouseData, String> {

    WarehouseData findByWarehouseID(String warehouseID);

}
