db = db.getSiblingDB('admin');

db.auth ("USER", "PASS")

db.grantRolesToUser("otus", [ { role: "executeFunctions", db: "otus" } ]);