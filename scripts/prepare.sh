#!/usr/bin/env bash
set -euo pipefail

if ! id -u ems-backend >/dev/null 2>&1; then
    useradd --system --home-dir /opt/ems-backend --shell /sbin/nologin ems-backend
fi

install -d -o ems-backend -g ems-backend -m 0750 /opt/ems-backend
if [[ ! -r /etc/ems-backend/ems-backend.env ]]; then
    echo "Missing readable runtime environment file: /etc/ems-backend/ems-backend.env" >&2
    exit 1
fi
