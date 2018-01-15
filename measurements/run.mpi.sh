#!/usr/bin/env bash
export IGA_ADI_SM_OPTS="-Xmx12000m" && mpirun -np 1 -host node13:64 ./run.batch.sh