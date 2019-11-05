db = db.getSiblingDB('otus');

db.auth ("USER", "PASS")

db.createCollection("activity");
db.createCollection("activity_configuration");
db.createCollection("activity_inapplicability");
db.createCollection("activity_permission");
db.createCollection("activity_revision");
db.createCollection("aliquot");
db.createCollection("auditor");
db.createCollection("datasource");
db.createCollection("datasource_temp");
db.createCollection("exam_lot");
db.createCollection("exam_result");
db.createCollection("exam_sending_lot");
db.createCollection("field_center");
db.createCollection("fs.files");
db.createCollection("laboratory_configuration");
db.createCollection("participant");
db.createCollection("participant_laboratory");
db.createCollection("participant_quality_control");
db.createCollection("password_reset_control");
db.createCollection("project_configuration");
db.createCollection("report");
db.createCollection("survey");
db.createCollection("survey_group");
db.createCollection("system_config");
db.createCollection("transportation_lot");
db.createCollection("unnecessary");
db.createCollection("user");
db.createCollection("user_permission");
db.createCollection("user_permission_profile");
