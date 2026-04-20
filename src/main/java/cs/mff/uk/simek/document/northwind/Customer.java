package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Customer {

    public Customer(Long customerId, String companyName, String city) {
        this.customerId = customerId;
        this.companyName = companyName;
        this.city = city;
    }

    private ObjectId id;

    private Long customerId;
    private String companyName;
    private String city;
}
