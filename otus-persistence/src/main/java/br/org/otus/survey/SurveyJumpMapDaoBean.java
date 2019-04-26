package br.org.otus.survey;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;
import org.ccem.otus.persistence.SurveyJumpMapDao;

public class SurveyJumpMapDaoBean extends MongoGenericDao<Document> implements SurveyJumpMapDao {

    private static final String COLLECTION_NAME = "survey_jump_map";

    public SurveyJumpMapDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void persist(SurveyJumpMap surveyJumpMap) {
        Document parsed = Document.parse(SurveyJumpMap.serialize(surveyJumpMap));
        collection.insertOne(parsed);
    }

    @Override
    public SurveyJumpMap get(String acronym, Integer version) throws DataNotFoundException {
        Document surveyJumpMapDocument = collection.find(new Document("surveyAcronym",acronym).append("surveyVersion",version)).first();
        if (surveyJumpMapDocument != null){
            return SurveyJumpMap.deserialize(surveyJumpMapDocument.toJson());
        } else {
            throw new DataNotFoundException("SurveyJumpMap not found for acronym {"+acronym+"} and version {"+version+"}");
        }
    }


}
