package ke.co.emtechhouse.accounts_service.Account;

import ke.co.emtechhouse.accounts_service.Utils.EntityResponse;
import ke.co.emtechhouse.accounts_service.Utils.RestTemplateConfig;
import ke.co.emtechhouse.accounts_service.clients.CustomerClient;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerClient customerClient;

    public AccountService(AccountRepository accountRepository, CustomerClient customerClient) {
        this.accountRepository = accountRepository;
        this.customerClient = customerClient;
    }

    public Account createAccount(Account account) {
        if (!customerClient.isMemberExists(account.getMemberNumber())) {
            throw new IllegalArgumentException("Member number not found in customer-service");
        }

        String accountNumber = generateAccountNumber(account.getProductCode(), account.getMemberNumber());
        account.setAccountNumber(accountNumber);
        return accountRepository.save(account);
    }

    private String generateAccountNumber(String productCode, String memberNumber) {
        return productCode + "-" + memberNumber;
    }

    public EntityResponse getAllAccounts() {
        EntityResponse response = new EntityResponse();
        try {
            List<Account> checkAccounts = accountRepository.findAll();

            if (checkAccounts.isEmpty()) {
                log.warn("No accounts found in the system.");
                response.setStatuscode(HttpStatus.NOT_FOUND.value());
                response.setMessage("No accounts found.");
                response.setEntity("");
                return response;
            }

            log.info("Retrieved [{}] account(s) from the database.", checkAccounts.size());
            response.setStatuscode(HttpStatus.OK.value());
            response.setMessage("Accounts retrieved successfully.");
            response.setEntity(checkAccounts);
            return response;

        } catch (Exception e) {
            log.error("Error while retrieving accounts: {}", e.getMessage(), e);
            response.setMessage("ERROR: " + e.getMessage());
            response.setEntity("");
            response.setStatuscode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
    }

    public EntityResponse getAllCustomerAccounts(String memberNumber){
        EntityResponse response = new EntityResponse();
        try {
            if (!customerClient.isMemberExists(memberNumber)) {
                response.setMessage(" Member number not found in customer-service: " + memberNumber);
                response.setStatuscode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
                return response;
            }
            List<Account> checkMemberNumber = accountRepository.findByMemberNumber(memberNumber);
            if (checkMemberNumber.isEmpty()){
                response.setMessage("No account belonging to memberNumber"+memberNumber);
                response.setStatuscode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
                return response;
            }
            response.setEntity(checkMemberNumber);
            response.setStatuscode(HttpStatus.OK.value());
            response.setMessage("Accounts for "+memberNumber+" found");
            return response;
        }
        catch (Exception e) {
            log.error("Error while retrieving accounts: {}", e.getMessage(), e);
            response.setMessage("ERROR: " + e.getMessage());
            response.setEntity("");
            response.setStatuscode(HttpStatus.BAD_REQUEST.value());
            return response;
        }

    }

    public EntityResponse modifyAccountDetails(Account account){
        EntityResponse response = new EntityResponse();
        try{
            Optional<Account> checkId = accountRepository.findById(account.getId());
            if (checkId.isEmpty()){
                response.setMessage("Account wih Id : "+account.getId()+" not found");
                response.setStatuscode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
                return response;
            }
            else if (!customerClient.isMemberExists(account.getMemberNumber())) {
                response.setMessage(" Member number not found in customer-service: " + account.getAccountNumber());
                response.setStatuscode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
                return response;
            }
            account.setAccountNumber(generateAccountNumber(account.getProductCode(),account.getMemberNumber()));
            response.setEntity(account);
            response.setMessage("Account modified Succesffully");
            response.setStatuscode(HttpStatus.OK.value());
            return response;


        } catch (Exception e) {
            log.error("Error while retrieving accounts: {}", e.getMessage(), e);
            response.setMessage("ERROR: " + e.getMessage());
            response.setEntity("");
            response.setStatuscode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
    }

    public EntityResponse deleteAccountById(Long id){
        EntityResponse response= new EntityResponse();
        try{
            Optional<Account> checkId=accountRepository.findById(id);
            if(checkId.isEmpty()){
                response.setMessage("Account wih Id "+ id+"cannott be found");
                response.setStatuscode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
            }
            accountRepository.deleteById(id);
            response.setEntity("account");
            response.setStatuscode(HttpStatus.OK.value());
            response.setMessage("Account Deleted Succesfully");
            return response;

        }catch (Exception e) {
            log.error("Error while retrieving accounts: {}", e.getMessage(), e);
            response.setMessage("ERROR: " + e.getMessage());
            response.setEntity("");
            response.setStatuscode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
    }




}
