package cs.mff.uk.simek.relational.queries;

import org.hibernate.Session;

public interface Query {
    public void perform(Session session);
}
