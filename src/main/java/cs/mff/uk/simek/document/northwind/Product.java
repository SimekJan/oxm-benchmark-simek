package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Product {

    public Product(String productName, Integer unitPrice) {
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    ObjectId id;
    String productName;
    Integer unitPrice;
    ObjectId supplier;
}
