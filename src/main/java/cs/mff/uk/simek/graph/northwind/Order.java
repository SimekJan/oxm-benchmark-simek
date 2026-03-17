package cs.mff.uk.simek.graph.northwind;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@NodeEntity
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "IS_RESPONSIBLE_FOR", direction = Relationship.INCOMING)
    private Employee employee;

    @Relationship(type = "IS_CUSTOMERS_ORDER")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "INCLUDES")
    private Set<Product> products;

    public void addProduct(Product p) {
        if (products == null) products = new HashSet<>();
        products.add(p);
    }
}
