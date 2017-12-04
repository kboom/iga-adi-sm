#!/usr/bin/env bash
mkdir terrain
curl ftp://91.223.135.109/nmt/malopolskie_grid100.zip -o terrain/malopolskie.zip
unzip terrain/malopolskie.zip -d terrain/
rm terrain/malopolskie.zip