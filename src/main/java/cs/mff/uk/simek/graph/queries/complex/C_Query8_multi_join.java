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
                    MATCH (o:Order)-[:INCLUDES]->(p:Product)
                    MATCH (p)-[:IS_PRODUCED_BY]->(s:Supplier)
                    MATCH (o)-[:IS_CUSTOMERS_ORDER]->(c:Customer)
                    MATCH (e:Employee)-[:IS_RESPONSIBLE_FOR]->(o)
                    RETURN\s
                        c.companyName AS customerName,
                        e.firstName AS employeeFirstName,
                        o.orderId AS orderId,
                        p.productName AS productName,
                        s.companyName AS supplierName
                """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            String customerName = (String) row.get("customerName");
            String employeeFirstName = (String) row.get("employeeFirstName");
            Long orderId = (Long) row.get("orderId");
            String productName = (String) row.get("productName");
            String supplierName = (String) row.get("supplierName");
            System.out.println(customerName + " " + employeeFirstName + " " + orderId + " " + productName + " " + supplierName);
        }
    }
}
