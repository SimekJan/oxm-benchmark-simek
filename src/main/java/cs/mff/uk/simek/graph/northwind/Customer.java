package cs.mff.uk.simek.graph.northwind;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@NodeEntity
public class Customer {

    public Customer(Long customerId, String name, String city) {
        this.customerId = customerId;
        this.companyName = name;
        this.city = city;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Index
    private Long customerId;

    private String companyName;

    private String city;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "IS_CUSTOMERS_ORDER", direction = Relationship.INCOMING)
    private List<Order> orders;

    public void addOrder(Order o) {
        if (orders == null) orders = new ArrayList<>();
        orders.add(o);
        o.setCustomer(this);
    }
}
