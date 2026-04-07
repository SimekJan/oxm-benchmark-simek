package cs.mff.uk.simek.document_embedded.northwind;

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

    private ObjectId id;
    private String productName;
    private Integer unitPrice;
}
