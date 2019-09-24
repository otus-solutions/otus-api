db = db.getSiblingDB('otus-domain');

db.createUser({
  user: "domain",
  pwd: "domain",
  roles: [{
    role: "dbOwner",
    db: "otus-domain"
  }]
});
