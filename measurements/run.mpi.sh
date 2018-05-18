#!/usr/bin/env bash
for problem in 12 24 48 96 192 384 768 1536 3072 6144
do

echo "Problem size $problem"
FILE=solutions_${problem}.csv

for threads in 1 2 3 5 8 12 16 20 25 30 35 40 45 50 56
do
     echo "Using $threads threads"
     results=$(mpirun -np 1 -host node13:1 java -jar ../dist/iga-adi-sm.jar --problem heat --problem-size ${problem} --steps 1 --max-threads ${threads})
     csvEntry=$(echo ${results} | awk '{print '${threads}'","$0}')
     echo ${csvEntry} >> ${FILE}
done
done