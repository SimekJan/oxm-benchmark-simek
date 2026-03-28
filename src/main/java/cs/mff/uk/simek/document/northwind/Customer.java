package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Customer {
    private ObjectId id;
    private String companyName;

    public Customer(String companyName) {
        this.companyName = companyName;
    }
}
