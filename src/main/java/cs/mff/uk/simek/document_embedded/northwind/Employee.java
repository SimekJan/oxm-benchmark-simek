package cs.mff.uk.simek.document_embedded.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Employee {

    public Employee(String firstName, String lastName, LocalDate birthDate, LocalDate hireDate, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.city = city;
    }

    private ObjectId id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private String city;
    private ObjectId reportsTo; // TODO ??

    private List<Order> orders;
}
