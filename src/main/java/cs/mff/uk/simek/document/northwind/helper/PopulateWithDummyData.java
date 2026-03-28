package cs.mff.uk.simek.document.northwind.helper;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cs.mff.uk.simek.document.northwind.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import java.util.Arrays;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class PopulateWithDummyData {

    public static void main(String[] args) {

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .codecRegistry(pojoCodecRegistry)
                        .build()
        );

        MongoDatabase db = mongoClient.getDatabase("testdb");

        MongoCollection<Customer> customers = db.getCollection("Customers", Customer.class);
        MongoCollection<Product> products = db.getCollection("Products", Product.class);
        MongoCollection<Order> orders = db.getCollection("Orders", Order.class);
        MongoCollection<Employee> employees = db.getCollection("Employees", Employee.class);
        MongoCollection<Supplier> suppliers = db.getCollection("Suppliers", Supplier.class);

        // Customers
        Customer c1 = new Customer("Karlovy Vary");
        Customer c2 = new Customer("Praha");

        customers.insertMany(Arrays.asList(c1, c2));

        // TODO: This works -> relationships
        System.out.println(c1.getId());
    }
}
