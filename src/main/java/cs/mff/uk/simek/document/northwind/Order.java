package cs.mff.uk.simek.document.northwind;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class Order {

    ObjectId id;
    ObjectId employee;
    ObjectId customer;
    List<ObjectId> products;
}
