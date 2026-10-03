package cs.mff.uk.simek.relational.queries_legacy;

import org.hibernate.Session;

public interface Query {
    public void perform(Session session);
}
