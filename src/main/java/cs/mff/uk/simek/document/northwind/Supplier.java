package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Supplier {

    public Supplier(String companyName, String city) {
        this.id = new ObjectId();
        this.companyName = companyName;
        this.city = city;
        suppliedBy = new ArrayList<>();
    }

    private ObjectId id;
    private String companyName;
    private String city;
    private List<ObjectId> suppliedBy;
}
