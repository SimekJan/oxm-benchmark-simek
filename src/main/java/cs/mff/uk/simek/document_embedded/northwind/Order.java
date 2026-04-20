package cs.mff.uk.simek.document_embedded.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    public Order(ObjectId customerId, LocalDate orderDate, List<Product> products) {
        this.customer = customerId;
        this.orderDate = orderDate;
        this.products = products;
    }

    private ObjectId id;
    private ObjectId customer;
    private LocalDate orderDate;

    private List<Product> products; // TODO ??
}
