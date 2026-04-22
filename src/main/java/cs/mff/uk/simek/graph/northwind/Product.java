package cs.mff.uk.simek.graph.northwind;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@NodeEntity
public class Product {

    public Product(Long productId, String name, Double unitPrice) {
        this.productId = productId;
        this.productName = name;
        this.unitPrice = unitPrice;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Index
    private Long productId;

    private String productName;

    private Double unitPrice;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "IS_PRODUCED_BY")
    private Supplier supplier;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = "INCLUDES", direction = Relationship.INCOMING)
    private Set<Order> orders;
}
