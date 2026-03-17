package cs.mff.uk.simek.graph.queries;

import org.neo4j.ogm.session.Session;

public interface Query {
    public void perform(Session session);
}
