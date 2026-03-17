package cs.mff.uk.simek.graph.northwind;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;
import java.util.HashSet;

@Data
@NoArgsConstructor
@NodeEntity
public class Supplier {

    public Supplier(String name, String city) {
        this.companyName = name;
        this.city = city;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String companyName;

    private String city;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "IS_PRODUCED_BY", direction = Relationship.INCOMING)
    private Set<Product> products;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "SUPPLIES_TO")
    private Set<Supplier> suppliesTo;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "SUPPLIES_TO", direction = Relationship.INCOMING)
    private Set<Supplier> suppliedBy;

    public void addProduct(Product p) {
        if (products == null) products = new HashSet<>();
        products.add(p);
        p.setSupplier(this);
    }

    public void addSuppliesTo(Supplier s) {
        if (suppliesTo == null) suppliesTo = new HashSet<>();
        suppliesTo.add(s);
    }
}
