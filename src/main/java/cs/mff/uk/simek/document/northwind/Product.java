package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Product {

    public Product(Long productId, String productName, Float unitPrice, String category, ObjectId supplierId) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.category = category;
        this.supplier = supplierId;
    }

    private ObjectId id;

    private Long productId;
    private String productName;
    private Float unitPrice;
    private String category;
    private ObjectId supplier;
}
