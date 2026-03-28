package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class Supplier {

    ObjectId id;
    String companyName;
    String city;
    List<ObjectId> suppliedBy;
}
