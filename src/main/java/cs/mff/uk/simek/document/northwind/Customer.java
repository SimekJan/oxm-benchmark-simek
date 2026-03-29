package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Customer {

    public Customer(String companyName, String city) {
        this.companyName = companyName;
        this.city = city;
    }

    private ObjectId id;
    private String companyName;
    private String city;
}
