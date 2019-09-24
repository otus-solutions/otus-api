cd /initdb
mongo --eval "var USER='$OTUS_USER'; var PASS='$OTUS_PASS';" otus-user.js

mongo --eval "var USER='$DOMAIN_USER'; var PASS='$DOMAIN_PASS';" domain-user.js
