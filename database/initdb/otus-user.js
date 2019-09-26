db = db.getSiblingDB('otus');

db.createUser({
  user: USER,
  pwd: PASS,
  roles: [{
    role: "dbOwner",
    db: "otus"
  }]
});

db.grantRolesToUser(USER, [ { role: "executeFunctions", db: "admin" } ]);