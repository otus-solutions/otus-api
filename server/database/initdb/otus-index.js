db = db.getSiblingDB('otus');

db.auth ("USER", "PASS")

db.getCollection("activity").createIndex({ "surveyForm.surveyTemplate.identity.acronym": 1 });
db.getCollection("activity").createIndex({ "participantData.recruitmentNumber": 1 });
db.getCollection("activity").createIndex({ "participantData.fieldCenter.acronym": 1 });
db.getCollection("activity").createIndex({ "isDiscarded": 1 });
db.getCollection("activity").createIndex({ "category": 1 });
db.getCollection("activity").createIndex({ "isDiscarded": 1, "participantData.fieldCenter.acronym": 1 });

db.getCollection("aliquot").createIndex({ "code": 1 });
db.getCollection("aliquot").createIndex({ "transportationLotId": 1 });
db.getCollection("aliquot").createIndex({ "examLotId": 1 });

db.getCollection("exam_result").createIndex({ "recruitmentNumber": 1 });
db.getCollection("exam_result").createIndex({ "examSendingLotId": 1 });
db.getCollection("exam_result").createIndex({ "objectType": 1 });
db.getCollection("exam_result").createIndex({ "examName": 1 });
db.getCollection("exam_result").createIndex({ "recruitmentNumber": 1 });
db.getCollection("exam_result").createIndex({ "aliquotCode": 1 });

db.getCollection("participant").createIndex({ "recruitmentNumber": 1 });

db.getCollection("participant_laboratory").createIndex({ "recruitmentNumber": 1 });

db.getCollection("filestore.files").createIndex({ "filename": 1, "uploadDate": 1 });

db.getCollection("fs.files").createIndex({ "filename": 1 });
