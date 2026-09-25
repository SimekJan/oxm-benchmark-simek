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

# Run to build the whole benchmark
# the --no-cache option ensures the images are rebuild even after local changes
docker compose build --no-cache

# Run to start the benchmark
docker compose up

# Run to end the benchmark, stop the databases if in Docker mode
# and remove the generated data from the dbs images
docker compose down -v
```

## Connecting to the DBs

- All ports for the databases are moved from the standard ports to avoid clash 
  with local instances during development and benchmark usage. 

### MongoDB

- From (e.g. Mongo Compass) connect to `mongodb://localhost:27018/`.
- Authentication **TBD**, there is none right now

### Neo4j

- From (e.g. Neo4j Desktop) connect to `bolt://localhost:7688`.
- Use username `neo4j` and password `oxm_password`.

### PostgreSQL

- From (e.g. DBeaver) connect to `jdbc:postgresql://localhost:5433/oxm_benchmark`.
- Use username `postgre` and password `oxm_password`.

## Config options

All config can be changed in `oxm_config.yaml` in root of the project.

### Mongo

To use local instance of MongoDB instead of container change: 

// TODO: add authentication

```yaml
importer:
  mongo:
    connection: mongodb://host.docker.internal:27017
```
