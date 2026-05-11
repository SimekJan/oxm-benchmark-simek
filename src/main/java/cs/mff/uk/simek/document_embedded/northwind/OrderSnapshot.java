package cs.mff.uk.simek.document_embedded.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

/**
 * Inserted into Employee and Customer for query optimization.
 * Does not include Products to avoid size explosion.
 */
@Data
@NoArgsConstructor
public class OrderSnapshot {

    public OrderSnapshot(Order order) {
        this.orderId = order.getOrderId();
        this.employee = order.getEmployee();
        this.customer = order.getCustomer();
        this.orderDate = order.getOrderDate();
    }

    private ObjectId id;

    private Long orderId;
    private ObjectId employee;
    private ObjectId customer;
    private LocalDate orderDate;
}
