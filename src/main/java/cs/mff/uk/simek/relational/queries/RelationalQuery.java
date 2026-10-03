package cs.mff.uk.simek.relational.queries;

import cs.mff.uk.simek.relational.HibernateSessionManager;
import org.hibernate.Session;

public interface RelationalQuery<P> {

    Session session = HibernateSessionManager.getSession();

    void run(P params);
}
