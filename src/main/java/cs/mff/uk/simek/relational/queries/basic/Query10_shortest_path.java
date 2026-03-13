package cs.mff.uk.simek.relational.queries.basic;

import cs.mff.uk.simek.relational.queries.Query;
import org.hibernate.Session;

/*
    Shortest path between two employees
    (lowest common manager)
 */
public class Query10_shortest_path implements Query {
    @Override
    public void perform(Session session) {

    }
}

/*
    TODO: jde o strom, stačí to?
    Ne -> viz. Subcontracts v novém schéma
 */
