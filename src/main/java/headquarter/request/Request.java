package headquarter.request;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Request {
    private final RestTemplate restTemplate;

    public Request(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    public String fetchSingleWarehouse() {
        String url = "http://localhost:8080/warehouse/001/data";
        return restTemplate.getForObject(url, String.class);
    }

    public String fetchMultipleWarehouses() {
        String url = "http://localhost:8080/warehouses/data";
        return restTemplate.getForObject(url, String.class);
    }
}
