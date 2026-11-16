#!/usr/bin/env bash
set -euo pipefail

chown ems-backend:ems-backend /opt/ems-backend/ems-backend.jar
chmod 0640 /opt/ems-backend/ems-backend.jar
chown root:root /etc/systemd/system/ems-backend.service
chmod 0644 /etc/systemd/system/ems-backend.service
systemctl daemon-reload
