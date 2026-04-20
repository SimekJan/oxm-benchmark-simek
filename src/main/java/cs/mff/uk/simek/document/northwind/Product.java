package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Product {

    public Product(Long productId, String productName, Integer unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    private ObjectId id;

    private Long productId;
    private String productName;
    private Integer unitPrice;
    private ObjectId supplier;
}
