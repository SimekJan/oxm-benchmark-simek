package cs.mff.uk.simek.graph.queries;

import cs.mff.uk.simek.graph.Neo4jSessionManager;
import cs.mff.uk.simek.runner.Query;
import org.neo4j.ogm.session.Session;

public interface GraphQuery<P> extends Query<P> {

    Session session = Neo4jSessionManager.getSession();
}
