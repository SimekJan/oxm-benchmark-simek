package cs.mff.uk.simek.relational.queries;

import cs.mff.uk.simek.relational.HibernateSessionManager;
import cs.mff.uk.simek.runner.Query;
import org.hibernate.Session;

public interface RelationalQuery<P> extends Query<P> {

    Session session = HibernateSessionManager.getSession();
}
