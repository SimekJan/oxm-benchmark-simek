package cs.mff.uk.simek.graph.northwind;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.typeconversion.LocalDateStringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@NodeEntity
public class Employee {

    public Employee(Long employeeId, String firstName, String lastName, LocalDate birthDate, LocalDate hireDate, String city) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.city = city;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Index
    private Long employeeId;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private LocalDate hireDate;

    private String city;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "REPORTS_TO")
    private Employee reportsTo;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "REPORTS_TO", direction = Relationship.INCOMING)
    private Set<Employee> subordinates;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "IS_RESPONSIBLE_FOR")
    private List<Order> orders;

    public void addOrder(Order o) {
        if (orders == null) orders = new ArrayList<>();
        orders.add(o);
        o.setEmployee(this);
    }

    public void addSubordinate(Employee e) {
        if (subordinates == null) subordinates = new HashSet<>();
        subordinates.add(e);
        e.setReportsTo(this);
    }
}