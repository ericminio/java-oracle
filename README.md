[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java7.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)
[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java8.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)
[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java11.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)

# Run the tests
```
docker-compose up java7 
docker-compose up java8 
docker-compose up java11 
```

# Usage

## from console
Study [service/demos/generate-from-*.sh](service/demos).

One way to see it running:
- `docker-compose run java8 bash` 
- `/usr/local/src/service/demos/generate-from-database.sh`
- `/usr/local/src/service/demos/generate-from-files.sh`

## from web
```
docker-compose up web
```
open http://localhost:8015