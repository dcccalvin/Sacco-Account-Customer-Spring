package ke.co.emtechhouse.accounts_service.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerClient {

    private final RestTemplate restTemplate;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    public CustomerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isMemberExists(String memberNumber) {
        String url = customerServiceUrl + "/customer/exists/" + memberNumber;

        try {
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(response);  // Safe null check
        } catch (Exception e) {
            System.out.println("Error contacting customer-service: " + e.getMessage());
            return false;
        }
    }
}
