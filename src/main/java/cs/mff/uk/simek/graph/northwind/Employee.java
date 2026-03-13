package cs.mff.uk.simek.graph.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@NodeEntity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String city;

    @Relationship(type = "REPORTS_TO")
    private Employee reportsTo;

    @ToString.Exclude
    @Relationship(type = "REPORTS_TO", direction = Relationship.INCOMING)
    private List<Employee> subordinates;
}