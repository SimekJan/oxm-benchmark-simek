package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
public class Employee {

    ObjectId id;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String city;
    ObjectId reportsTo;
}
