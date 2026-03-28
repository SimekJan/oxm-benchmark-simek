package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Product {

    ObjectId id;
    String productName;
    Integer unitPrice;
    ObjectId supplier;
}
