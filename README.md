# OXM-benchmark 

```
 ~ 7/2026
```

## Filtering by Regex

- **PostgreSQL**: LIKE, SIMILAR TO, POSIX Regular Expressions

- **MongoDB**: $regex operator, Regex expression object directly in query

- **Neo4j**: STARTS WITH, Regex (=~)

### Differences

- Non-indexed columns -> Sequential (probably not interesting)

- Indexed -> differs (**Starts with** vs. **General regex**)

- **Starts with** -> possibly better optimization with MongoDB

- **General Regex** -> all full scan, PostgreSQL improvement using *pg_trgm* (filters results before regex is applied using 3-character chunks)

## Northwind generated sources sizes

- Suppliers < Employees < Products < Customers < Orders

## Multi-join query

- Optimized so smaller collections are joined first (to avoid pottential issues with inner oprimization differences)

- Added filtering so the join is not too large when using larger generated data

- For chosen document embedded model this query is not possible to be optimized, so the query was deleted 

## Generating Northwind data

- TBD

## Dockerization
```bash
# first run this to compile JAR file, or after change in Java code
mvn clean package

# this then runs the generator and saves generated data based on chosen config  
docker compose run --build --rm generator
```
