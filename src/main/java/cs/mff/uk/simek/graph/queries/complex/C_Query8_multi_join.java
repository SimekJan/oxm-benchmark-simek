package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/*
 * Join Customers, Employees, Orders, Products and Suppliers.
 */
public class C_Query8_multi_join implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (s:Supplier)<-[:IS_PRODUCED_BY]-(p:Product)
                    MATCH (p)<-[:INCLUDES]-(o:Order)
                    MATCH (o)<-[:IS_RESPONSIBLE_FOR]-(e:Employee)
                    MATCH (o)-[:IS_CUSTOMERS_ORDER]->(c:Customer)
                    WHERE s.companyName = 'BluePeak Industries'
                        AND p.productName = 'Steel Plate'
                        AND e.firstName = 'Jeffrey'
                        AND c.companyName = 'StoneBridge'
                    RETURN
                        c.companyName AS customerName,
                        e.firstName AS employeeFirstName,
                        o.orderDate AS orderDate,
                        p.productName AS productName,
                        s.companyName AS supplierName
                """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String customerName = (String) row.get("customerName");
            String employeeFirstName = (String) row.get("employeeFirstName");
            String orderId = (String) row.get("orderDate");
            String productName = (String) row.get("productName");
            String supplierName = (String) row.get("supplierName");
            System.out.println(customerName + " - " + employeeFirstName + " - " + orderId + " - " + productName + " - " + supplierName);
        }
    }
}
