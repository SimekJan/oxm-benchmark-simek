package cs.mff.uk.simek.document_embedded.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    public Order(ObjectId customerId, List<Product> products) {
        this.customer = customerId;
        this.products = products;
    }

    private ObjectId id;
    private ObjectId customer;
    private List<Product> products; // ??
}
