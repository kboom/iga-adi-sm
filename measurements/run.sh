#!/usr/bin/env bash
export JAVA_OPTS="-Xms8G -Xmx32G" && ../dist/iga-adi-sm/bin/iga-adi-sm --problem-size $1 --steps 1 --problem heat