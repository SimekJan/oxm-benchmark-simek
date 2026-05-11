package cs.mff.uk.simek.graph.queries.complex;

import cs.mff.uk.simek.graph.queries.Query;
import org.neo4j.ogm.session.Session;

import java.util.Map;

/**
 *  Create a complex real-world-like report including employee info
 */
public class C_Query10_employee_report implements Query {
    @Override
    public void perform(Session session) {

        String query = """
                    MATCH (e:Employee)
                    WITH e
                
                    OPTIONAL MATCH (e)-[:IS_RESPONSIBLE_FOR]->(o:Order)
                    WITH e, COUNT(o) AS orderCount
                
                    OPTIONAL MATCH (p:Product)<-[:INCLUDES]-(e)-[:IS_RESPONSIBLE_FOR]->(o:Order)
                    WITH e, orderCount, SUM(p.unitPrice) AS totalPrice
                
                    OPTIONAL MATCH (s:Employee)-[:REPORTS_TO]->(e)
                    WITH e, orderCount, totalPrice, COUNT(s) AS subordinateCount
                
                    WITH e, orderCount, totalPrice, subordinateCount,
                         duration.between(date(e.hireDate), date()).years AS yearsWorked,
                         duration.between(date(e.birthDate), date()).years AS age
                
                    RETURN
                        e.employeeId AS employeeId,
                        e.firstName AS firstName,
                        e.lastName AS lastName,
                        orderCount,
                        totalPrice,
                        subordinateCount,
                        yearsWorked,
                        age
                """;

        Iterable<Map<String, Object>> results = session.query(query, Map.of());

        for (Map<String, Object> row : results) {
            Long employeeId = (Long) row.get("employeeId");
            String firstName = (String) row.get("firstName");
            String lastName = (String) row.get("lastName");
            Long numberOfOrders = (Long) row.get("orderCount");
            Long totalPrice = (Long) row.get("totalPrice");
            Long numberOfSubordinates = (Long) row.get("subordinateCount");
            Long yearsSinceHire = (Long) row.get("yearsWorked");
            Long age = (Long) row.get("age");

            System.out.println("Employee: " + employeeId + " " + firstName + " " + lastName);
            System.out.println("Manages " + numberOfOrders + " orders, with total price of " + totalPrice);
            System.out.println("Supervises " + numberOfSubordinates + " employee(s)");
            System.out.println("Works for " + yearsSinceHire + " years. Aged: " + age);
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }
}
