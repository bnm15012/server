#!/bin/bash
echo "Exporting MySQL database before shutdown..."
docker exec mysql-db mysqldump -uroot -prootpassword studio > data/data.sql
echo "Database export completed!"
