package cs.mff.uk.simek.graph.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@NoArgsConstructor
@NodeEntity
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "IS_RESPONSIBLE_FOR")
    private Employee employee;

    @Relationship(type = "IS_CUSTOMERS_ORDER")
    private Customer customer;

    @Relationship(type = "INCLUDES")
    private Set<Product> products;
}
