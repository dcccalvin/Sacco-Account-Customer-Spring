package ke.co.emtechhouse.accounts_service.Account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String accountNumber;
    @Column(nullable = false)
    private String memberNumber;
    @Column(nullable = false)
    private double accountBalance;
    @Column(nullable = false)
    private String productCode;


}
