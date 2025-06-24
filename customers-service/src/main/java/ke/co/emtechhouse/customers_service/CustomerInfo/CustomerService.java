package ke.co.emtechhouse.customers_service.CustomerInfo;

import ke.co.emtechhouse.customers_service.Utills.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    CustomerKycRepository customerKycRepository;

    public EntityResponse addCustomer(CustomerKyc customerKyc){
        EntityResponse response = new EntityResponse<>();
        try{
            log.info("Payload: "+ customerKyc);

            response.setEntity(customerKycRepository.save(customerKyc));
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("Created successfully");
            return response;
        }catch (Exception e){
            log.info("Error: "+e.getMessage());
            response.setEntity("");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Error: "+e.getMessage());
            return  response;
        }
    }

    public EntityResponse modifyCustomer(CustomerKyc customerKyc){
        EntityResponse response = new EntityResponse<>();
        try {
            Optional<CustomerKyc> checkId=customerKycRepository.findById(customerKyc.getId());
            if(checkId.isEmpty()){
                log.error("Id Not Found"+ customerKyc.getId());
                response.setEntity("Not Found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Check Id ");
                return response;
            }
            log.info("Modified successfully");
            response.setEntity(customerKycRepository.save(customerKyc));
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("c");
            return response;
        }catch (Exception e){
            log.info("Error: "+e.getMessage());
            response.setEntity("");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Error: "+e.getMessage());
            return  response;
        }
    }

    public EntityResponse getCustomers(){
        EntityResponse response = new EntityResponse<>();
        try {
            if(customerKycRepository.findAll().isEmpty()){
                log.info("Records Could Not Be found");
                response.setStatusCode(HttpStatus.NO_CONTENT.value());
                response.setMessage("Records Were Not found");
                response.setEntity(customerKycRepository.findAll());
            }
            log.info("Customers Found");
            response.setEntity(customerKycRepository.findAll());
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Records Found");
            return response;
        }catch (Exception e){
            log.info("Error: "+e.getMessage());
            response.setEntity("");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Error: "+e.getMessage());
            return  response;
        }
    }

    public EntityResponse deleteCustomer(Long id){
        EntityResponse response = new EntityResponse<>();
        try {
            Optional<CustomerKyc> checkId = customerKycRepository.findById(id);
            if (checkId.isEmpty()){
                log.error("Id Not Found"+ id);
                response.setEntity("Not Found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Check Id ");
                return response;
            }
            log.info("Customer deleted successfully");
            customerKycRepository.delete(checkId.get());
            response.setEntity("Customer deleted successfully");
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Deleted successfully");
            return response;
        }catch (Exception e){
            log.info("Error: "+e.getMessage());
            response.setEntity("");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Error: "+e.getMessage());
            return  response;
        }
    }



}
