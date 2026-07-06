# OXM-benchmark 

## ~ 7/2026

### Filtering by Regex

- **PostgreSQL**: LIKE, SIMILAR TO, POSIX Regular Expressions

- **MongoDB**: $regex operator, Regex expression object directly in query

- **Neo4j**: STARTS WITH, Regex (=~)

#### Non-indexed columns -> Sequential (probably not interesting)

#### Indexed -> differs (**Starts with** vs. **General regex**)

#### **Starts with** -> possibly better optimization with MongoDB

#### **General Regex** -> all full scan, PostgreSQL improvement using *pg_trgm* (filters results before regex is applied using 3-character chunks)