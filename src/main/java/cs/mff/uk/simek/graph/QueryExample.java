package cs.mff.uk.simek.graph;

import cs.mff.uk.simek.graph.northwind.Supplier;
import org.neo4j.ogm.session.Session;

import java.util.Collection;
import java.util.Set;

public class QueryExample {

    public static void main(String[] args) {

        Session session = Neo4jSessionManager.getSession();

        // Create two suppliers
        Supplier acme = new Supplier();
        acme.setCompanyName("ACME Corp");
        acme.setCity("Prague");

        Supplier beta = new Supplier();
        beta.setCompanyName("Beta Ltd");
        beta.setCity("Brno");

        // Link ACME to Beta
        acme.setSuppliesTo(Set.of(beta));
        session.save(acme);

        // Query all suppliers
        Collection<Supplier> allSuppliers = session.loadAll(Supplier.class);
        allSuppliers.forEach(System.out::println);
    }
}
