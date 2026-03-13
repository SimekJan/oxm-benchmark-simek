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
public class Supplier {

    @Id
    @GeneratedValue
    private Long id;

    private String companyName;

    private String city;

    @ToString.Exclude
    @Relationship(type = "SUPPLIES_TO")
    private Set<Supplier> suppliesTo;

    @ToString.Exclude
    @Relationship(type = "SUPPLIES_TO", direction = Relationship.INCOMING)
    private Set<Supplier> suppliedBy;
}
