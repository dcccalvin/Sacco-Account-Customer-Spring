package ke.co.emtechhouse.accounts_service.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account ,Long> {
    List<Account> findByMemberNumber(String memberNumber);


}
