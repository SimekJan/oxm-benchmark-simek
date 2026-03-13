package cs.mff.uk.simek.graph;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4jSessionManager {

    private static final String DOMAIN_PACKAGE = "cs.mff.uk.simek.graph.northwind";
    private static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration.Builder()
                .uri("bolt://localhost:7687")
                .credentials("neo4j", "adminadmin")
                .build();

        sessionFactory = new SessionFactory(configuration, DOMAIN_PACKAGE);
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}