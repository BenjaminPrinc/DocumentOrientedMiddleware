package headquarter;

import headquarter.model.ProductData;
import headquarter.model.WarehouseData;
import headquarter.processing.DataProcessingService;
import headquarter.repositories.ProductRepository;
import headquarter.repositories.WarehouseRepository;
import headquarter.request.Request;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final Request request;
    private final DataProcessingService dataProcessingService;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public Application(Request request, DataProcessingService dataProcessingService, WarehouseRepository warehouseRepository, ProductRepository productRepository) {
        this.request = request;
        this.dataProcessingService = dataProcessingService;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String ...args) {
        warehouseRepository.deleteAll();
        productRepository.deleteAll();
        //dataProcessingService.processSingleData(request.fetchSingleWarehouse());
        dataProcessingService.processMultipleData(request.fetchMultipleWarehouses());

        System.out.println("WarehouseData found with findAll():");
        System.out.println("-------------------------------");
        for (WarehouseData warehouseData : warehouseRepository.findAll()) {
            System.out.println(warehouseData);
        }
        System.out.println();

        System.out.println("ProductData found with findAll():");
        System.out.println("-------------------------------");
        for (ProductData productData : productRepository.findAll()) {
            System.out.println(productData);
        }
        System.out.println();

        System.out.println("Products in category \"Backwaren\" in warehouse1");
        System.out.println("-------------------------------");
        for (ProductData productData : productRepository.findByWarehouseIDAndProductCategory("001","Backwaren")) {
            System.out.println(productData);
        }
    }
}
