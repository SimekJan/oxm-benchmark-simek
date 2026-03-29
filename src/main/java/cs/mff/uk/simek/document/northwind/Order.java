package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    public Order(ObjectId customerId, ObjectId employeeId, List<ObjectId> productIds) {
        this.employee = employeeId;
        this.customer = customerId;
        this.products = productIds;
    }

    ObjectId id;
    ObjectId employee;
    ObjectId customer;
    List<ObjectId> products;
}
