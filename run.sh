#!/usr/bin/env bash
set -euo pipefail

DBS=()

mapfile -t DBS < <(
    awk '
        /^databaseToUse:/ { in_db=1; next }
        in_db && /^[^[:space:]-]/ { exit }
        in_db && /^[[:space:]]*-[[:space:]]*/ {
            sub(/^[[:space:]]*-[[:space:]]*/, "")
            sub(/\r$/, "")
            print
        }
    ' oxm_config.yaml
)

if [ ${#DBS[@]} -eq 0 ]; then
    echo "No databases specified in oxm_config.yaml"
    exit 1
fi

echo "Resetting Docker environment..."
docker compose down -v --remove-orphans

echo "Starting databases: ${DBS[*]}"
docker compose up -d "${DBS[@]}"

echo "Waiting for databases..."

for db in "${DBS[@]}"; do
    echo "Waiting for $db..."

    while true; do
        status=$(docker compose ps -q "$db" |
            xargs -r docker inspect -f '{{.State.Health.Status}}')

        if [[ "$status" == "healthy" ]]; then
            break
        fi

        sleep 2
    done

    echo "$db is ready"
done

echo "All databases are ready."

docker compose up --build \
    generator \
    importer \
    runner