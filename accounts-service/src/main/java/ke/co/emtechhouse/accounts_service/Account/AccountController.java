package ke.co.emtechhouse.accounts_service.Account;

import ke.co.emtechhouse.accounts_service.Utils.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.ok(createdAccount);
        } catch (IllegalArgumentException e) {
            log.warn("Validation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
        }
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<?> getAllAccounts() {
        try {
            EntityResponse res = accountService.getAllAccounts();
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            EntityResponse response = new EntityResponse<>();
            response.setEntity("");
            response.setStatuscode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/getAllCustomerAccounts")
    public ResponseEntity<?> getAllCustomerAccounts(@RequestParam String memberNumber) {
        try {
            EntityResponse res = accountService.getAllCustomerAccounts(memberNumber);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            EntityResponse response = new EntityResponse<>();
            response.setEntity("");
            response.setStatuscode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyAccount(@RequestBody Account account){
        try {
            EntityResponse res = accountService.modifyAccountDetails(account);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            EntityResponse response = new EntityResponse<>();
            response.setEntity("");
            response.setStatuscode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@RequestParam Long id){
        try{
            EntityResponse res = accountService.deleteAccountById(id);
            return  ResponseEntity.ok(res);
        }
        catch (Exception e){
            EntityResponse response = new EntityResponse<>();
            response.setEntity("");
            response.setStatuscode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }


    }




}