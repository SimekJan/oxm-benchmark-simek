package cs.mff.uk.simek.query_params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryParams {

    private long seed;
    private long supplierCount;
    private long relationshipCount;
    private long queryCount;
}
