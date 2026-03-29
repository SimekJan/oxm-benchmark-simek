package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Employee {

    public Employee(String firstName, String lastName, LocalDate birthDate, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.city = city;
    }

    ObjectId id;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String city;
    ObjectId reportsTo;
}
