package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    public Order(ObjectId customerId, ObjectId employeeId, List<ObjectId> productIds) {
        this.employee = employeeId;
        this.customer = customerId;
        this.products = productIds;
    }

    private ObjectId id;
    private ObjectId employee;
    private ObjectId customer;
    private List<ObjectId> products;
}
