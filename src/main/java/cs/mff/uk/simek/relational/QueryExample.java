package cs.mff.uk.simek.relational;

import cs.mff.uk.simek.relational.queries.Query;
import cs.mff.uk.simek.relational.queries.basic.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class QueryExample {
    public static void main(String[] args) {
        // Prepare session
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // Queries
        Query q = new Query13_intersect();
        q.perform(session);

        // End session
        tx.commit();
        session.close();
        factory.close();
    }
}