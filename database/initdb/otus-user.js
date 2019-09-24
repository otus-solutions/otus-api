db = db.getSiblingDB('otus');

db.createUser({
  user: "otus",
  pwd: "otus",
  roles: [{
    role: "dbOwner",
    db: "otus"
  }]
});
db.grantRolesToUser("otus", [ { role: "executeFunctions", db: "admin" } ]);
