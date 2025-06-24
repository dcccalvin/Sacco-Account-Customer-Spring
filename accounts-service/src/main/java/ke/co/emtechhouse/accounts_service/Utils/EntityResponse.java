package ke.co.emtechhouse.accounts_service.Utils;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class EntityResponse<T> {
    private String message;
    private T entity;
    private int statuscode;
}
