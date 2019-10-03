db = db.getSiblingDB('admin');
db.auth (USER, PASS)
db.createRole( { role: "executeFunctions", privileges: [ { resource: { anyResource: true }, actions: [ "anyAction" ] } ], roles: [] } );