package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    public Order(Long orderId, Long customerId, Long employeeId, LocalDate orderDate) {
        this.orderId = orderId;
        this.employee = employeeId;
        this.customer = customerId;
        this.orderDate = orderDate;
        this.products = new ArrayList<>();
    }

    @BsonId
    private ObjectId id;

    private Long orderId;
    private Long employee;
    private Long customer;
    private LocalDate orderDate;
    private List<Long> products;
}
