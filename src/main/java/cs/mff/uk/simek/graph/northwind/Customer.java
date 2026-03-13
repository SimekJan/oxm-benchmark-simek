package cs.mff.uk.simek.graph.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@Data
@NoArgsConstructor
@NodeEntity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String companyName;

    private String city;

    @ToString.Exclude
    @Relationship(type = "IS_CUSTOMERS_ORDER", direction = Relationship.INCOMING)
    private List<Order> orders;
}
