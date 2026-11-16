#!/usr/bin/env bash
set -euo pipefail

if systemctl cat ems-backend.service >/dev/null 2>&1; then
    systemctl stop ems-backend.service
fi
