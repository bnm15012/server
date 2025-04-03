#!/bin/bash
echo "Waiting for MySQL to be ready..."
until mysql -h 127.0.0.1 -uroot -prootpassword -e "SELECT 1"; do
  sleep 2
done

echo "Checking if the database is empty..."
DB_EXISTS=$(mysql -uroot -prootpassword -e "USE studio; SHOW TABLES;" | wc -l)

if [ "$DB_EXISTS" -eq 0 ]; then
  echo "Database is empty. Importing data.sql..."
  mysql -uroot -prootpassword studio < /docker-entrypoint-initdb.d/data.sql
else
  echo "Database already has data. Skipping import."
fi

echo "MySQL is ready!"
exec docker-entrypoint.sh mysqld  # Continue default MySQL startup
