package ke.co.emtechhouse.customers_service.CustomerInfo;

import ke.co.emtechhouse.customers_service.Utills.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerKycRepository customerKycRepository;

    @PostMapping("/add")
    public EntityResponse addCusomer(@RequestBody CustomerKyc customerKyc){
        EntityResponse response = new EntityResponse<>();
        try {
            EntityResponse res = customerService.addCustomer(customerKyc);
            return res;
        }catch (Exception e){
            log.info("Error: "+e.getMessage());
            response.setEntity("");
            response.setMessage("Error: "+e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        }
        return  response;

    }

    @PutMapping("/modify")
    public EntityResponse modifyCusomer(@RequestBody CustomerKyc customerKyc){
        EntityResponse response = new EntityResponse<>();
        try {
            EntityResponse res = customerService.modifyCustomer(customerKyc);
            return res;
        }catch (Exception e){
            log.info("Error: "+e.getMessage());
            response.setEntity("");
            response.setMessage("Error: "+e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        }
        return  response;

    }

    @GetMapping ("/all")
    public EntityResponse getCustomers(){
        EntityResponse response = new EntityResponse<>();
        try {
            EntityResponse res =customerService.getCustomers();
            return res;
        }catch (Exception e){
            log.info("Error: "+e.getMessage());
            response.setEntity("");
            response.setMessage("Error: "+e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        }
        return  response;
    }

    @DeleteMapping("/delete/{id}")
    public EntityResponse deleteCustomers(@PathVariable Long id){
        EntityResponse response = new EntityResponse<>();
        try {
            EntityResponse res =customerService.deleteCustomer(id);
            return res;
        }catch (Exception e){
            log.info("Error: "+e.getMessage());
            response.setEntity("");
            response.setMessage("Error: "+e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        }
        return  response;
    }

    @GetMapping("exists/{memberNumber}")
    public ResponseEntity<Boolean> checkIfMemberExists(@PathVariable String memberNumber) {
        boolean exists = customerKycRepository.existsByMemberNumber(memberNumber);

        if (exists) {
            log.info("Member [{}] exists.", memberNumber);
        } else {
            log.warn("Member [{}] does NOT exist.", memberNumber);
        }

        return ResponseEntity.ok(exists);
    }



}


