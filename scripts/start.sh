#!/usr/bin/env bash
set -euo pipefail

systemctl enable ems-backend.service
systemctl restart ems-backend.service
