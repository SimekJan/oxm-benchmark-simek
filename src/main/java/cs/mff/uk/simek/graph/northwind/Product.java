package cs.mff.uk.simek.graph.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@NoArgsConstructor
@NodeEntity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String productName;

    private Integer unitPrice;

    @Relationship(type = "IS_PRODUCED_BY")
    private Supplier supplier;

    @ToString.Exclude
    @Relationship(type = "INCLUDES", direction = Relationship.INCOMING)
    private Set<Order> orders;
}
