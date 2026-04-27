package cs.mff.uk.simek.relational;

import cs.mff.uk.simek.relational.queries.Query;
import cs.mff.uk.simek.relational.queries.complex.*;
import cs.mff.uk.simek.relational.queries.basic.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class QueryExample {
    public static void main(String[] args) {
        Session session = HibernateSessionManager.getSession();
        Transaction tx = session.beginTransaction();

        Query q = new C_Query5_filter_join();
        q.perform(session);

        tx.commit();
        session.close();
    }
}