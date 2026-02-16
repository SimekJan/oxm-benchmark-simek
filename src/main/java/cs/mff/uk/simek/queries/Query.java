package cs.mff.uk.simek.queries;

import org.hibernate.Session;

public interface Query {
    public void perform(Session session);
}
