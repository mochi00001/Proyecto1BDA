Sunny.12
mongo --host localhost --port 27017
0
exit
12345
1234
password
up
start
exit
mongo --host localhost --port 27017
 --host localhost --port 27017
show dbs
apt-get update
apt-get install -y mongodb-clients
cat /etc/os-release
wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | apt-key add -
apt-get update
apt-get install -y wget gnupg
wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | apt-key add -
wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | apt-key add -
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/8.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-8.0.list
apt-get update
apt-get install -y mongodb-org-shell
mongo
find / -name "mongo"
apt-get remove --purge mongodb-org-shell
apt-get install -y mongodb-org-shell
which mongo
ps aux | grep mongod
mongosh --host localhost --port 27017
exit
