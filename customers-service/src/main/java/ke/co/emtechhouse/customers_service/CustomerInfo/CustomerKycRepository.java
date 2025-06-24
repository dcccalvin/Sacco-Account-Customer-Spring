package ke.co.emtechhouse.customers_service.CustomerInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerKycRepository extends JpaRepository<CustomerKyc, Long> {

    Optional<CustomerKyc> findByMemberNumber(String memberNumber);
    boolean existsByMemberNumber(String memberNumber);
}
