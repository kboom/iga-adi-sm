#!/usr/bin/env bash
export IGA_ADI_SM_OPTS="-Xmx12000m" && mpirun -np 1 -host node13:10 ../dist/iga-adi-sm/bin/iga-adi-sm --problem-size 768 --problem flood --steps 500 --delta 0.00001 --terrain-file /home/stud/ggurgul/iga-adi-sm/terrain/malopolskie.txt --terrain-x-offset 560000 --terrain-y-offset 180000 --terrain-scale 100 -w --store-file /home/stud/ggurgul/iga-mf-results/flood.768.500
