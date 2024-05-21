#!/bin/bash

for((i = 0; i <= 6; i++))
do
    for((j = i; j <= 6; j++))
    do
        cp Domino.png "di$i$j.png"
    done
done

for((i = 0; i <= 5; i++))
do
    for((j = i; j <= 5; j++))
    do
        for((k = j; k <= 5; k++))
        do
            cp TriominoEjemplo.png "tri$i$j$k.png"
        done
    done
done
