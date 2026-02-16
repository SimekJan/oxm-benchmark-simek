package cs.mff.uk.simek;

import cs.mff.uk.simek.queries.*;
import cs.mff.uk.simek.queries.basic_queries.Query0;
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
        Query q = new Query0();
        q.perform(session);

        // End session
        tx.commit();
        session.close();
        factory.close();
    }
}