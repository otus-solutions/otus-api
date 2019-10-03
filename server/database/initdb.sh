cd /initdb

mongo --eval "var USER='$MONGO_INITDB_ROOT_USERNAME'; var PASS='$MONGO_INITDB_ROOT_PASSWORD';" otus-roles.js

mongo --eval "var USER='$OTUS_USER'; var PASS='$OTUS_PASS';" otus-user.js

mongo --eval "var USER='$DOMAIN_USER'; var PASS='$DOMAIN_PASS';" domain-user.js

mongo --eval "var USER='$OTUS_USER'; var PASS='$OTUS_PASS';" otus-collections.js

mongo --eval "var USER='$OTUS_USER'; var PASS='$OTUS_PASS';" otus-index.js

mongo --eval "var USER='$OTUS_USER'; var PASS='$OTUS_PASS';" otus-data.js