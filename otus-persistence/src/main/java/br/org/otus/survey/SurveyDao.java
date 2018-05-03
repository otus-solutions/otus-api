package br.org.otus.survey;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.Block;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.survey.form.SurveyForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;

public class SurveyDao extends MongoGenericDao<Document> {

    private static final String COLLECTION_NAME = "survey";

    public SurveyDao() {
        super(COLLECTION_NAME, Document.class);
    }

    public List<SurveyForm> find() {
        ArrayList<SurveyForm> surveys = new ArrayList<SurveyForm>();
        Document query = new Document("isDiscarded", false);
        collection.find(query).forEach((Block<Document>) document -> {
            surveys.add(SurveyForm.deserialize(document.toJson()));
        });

        return surveys;
    }

    public List<SurveyForm> findByAcronym(String acronym) {
        ArrayList<SurveyForm> surveys = new ArrayList<>();
        collection.find(eq("surveyTemplate.identity.acronym", acronym)).forEach((Block<Document>) document -> {
            surveys.add(SurveyForm.deserialize(document.toJson()));
        });

        return surveys;
    }

    public List<SurveyForm> findByCustomId(Set<String> ids) {
        ArrayList<SurveyForm> surveys = new ArrayList<>();
        collection
                .find(or(in("surveyTemplate.itemContainer.customID", ids),
                        in("surveyTemplate.itemContainer.options.customOptionID", ids)))
                .forEach((Block<Document>) document -> {
                    surveys.add(SurveyForm.deserialize(document.toJson()));
                });

        return surveys;

    }

    public ObjectId persist(SurveyForm survey) {
        Document parsed = Document.parse(SurveyForm.serialize(survey));
        parsed.remove("_id");
        collection.insertOne(parsed);

        return parsed.getObjectId("_id");
    }

    public boolean updateSurveyFormType(String acronym, String surveyFormType) {
        UpdateResult updateOne = collection.updateOne(eq("surveyTemplate.identity.acronym", acronym.toUpperCase()),
                new Document("$set", new Document("surveyFormType", surveyFormType)));

        return updateOne.getModifiedCount() > 0;
    }

    public boolean deleteLastVersionByAcronym(String acronym) {
        DeleteResult deleteResult = collection.deleteOne(eq("surveyTemplate.identity.acronym", acronym.toUpperCase()));

        return deleteResult.getDeletedCount() > 0;
    }

    public boolean discardSurvey(ObjectId id) throws DataNotFoundException {
        Document query = new Document("_id", id);

        UpdateResult updateResult = collection
                .updateOne(
                        query,
                        new Document("$set", new Document("isDiscarded", true)),
                        new UpdateOptions().upsert(false)
                );

        if(updateResult.getMatchedCount() == 0){
            throw new DataNotFoundException("No survey found to discard");
        }

        return updateResult.getModifiedCount() != 0;
    }

    public SurveyForm getLastVersionByAcronym(String acronym) throws DataNotFoundException {
        Document query = new Document();

        query.put("surveyTemplate.identity.acronym", acronym.toUpperCase());

        //todo: check sorting
        Document higherVersionDocument = collection.find(query).sort(descending("version")).first();

        if (higherVersionDocument == null) {
            return null;
        }
        //todo: test String.valueOf();
        return SurveyForm.deserialize(higherVersionDocument.toJson());

    }

}
