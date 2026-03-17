package cs.mff.uk.simek.graph.northwind;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@NodeEntity
public class Customer {

    public Customer(String name, String city) {
        this.companyName = name;
        this.city = city;
    }

    @Id
    @GeneratedValue
    private Long id;

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
