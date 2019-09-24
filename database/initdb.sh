cd /initdb
mongo --eval "var USER='$OTUS_USER'; var PASS='$OTUS_PASS';" otus-user.js
