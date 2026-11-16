#!/usr/bin/env bash
set -euo pipefail

for attempt in $(seq 1 24); do
    if curl --fail --silent --show-error http://127.0.0.1:8080/actuator/health >/dev/null; then
        exit 0
    fi
    sleep 5
done

journalctl -u ems-backend.service --no-pager -n 100
exit 1
