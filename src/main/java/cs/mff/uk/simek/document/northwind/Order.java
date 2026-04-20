package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    public Order(Long orderId, ObjectId customerId, ObjectId employeeId, LocalDate orderDate, List<ObjectId> productIds) {
        this.orderId = orderId;
        this.employee = employeeId;
        this.customer = customerId;
        this.orderDate = orderDate;
        this.products = productIds;
    }

    private ObjectId id;

    private Long orderId;
    private ObjectId employee;
    private ObjectId customer;
    private LocalDate orderDate;
    private List<ObjectId> products;
}
