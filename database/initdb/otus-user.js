db = db.getSiblingDB('otus');

db.createUser({
  user: USER,
  pwd: PASS,
  roles: [{
    role: "dbOwner",
    db: "otus"
  }]
});
//db.grantRolesToUser("otus", [ { role: "executeFunctions", db: "admin" } ]);
