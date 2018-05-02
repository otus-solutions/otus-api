package br.org.otus.survey;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.Block;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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
        list().forEach((Block<Document>) document -> {
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

    public boolean updateSurveyFormType(String acronym, String surveyFormType) {
        UpdateResult updateOne = collection.updateOne(eq("surveyTemplate.identity.acronym", acronym.toUpperCase()),
                new Document("$set", new Document("surveyFormType", surveyFormType)));

        return updateOne.getModifiedCount() > 0;
    }

    public boolean deleteByAcronym(String acronym) {
        DeleteResult deleteResult = collection.deleteOne(eq("surveyTemplate.identity.acronym", acronym.toUpperCase()));

        return deleteResult.getDeletedCount() > 0;
    }

    public Integer getLastVersionByAcronym(String acronym) throws DataNotFoundException {
        Document query = new Document();
        Document sorting = new Document();

        query.put("surveyTemplate.identity.acronym", acronym.toUpperCase());
        sorting.put("version", "-1");

        //todo: check sorting
        Document higherVersionDocument = collection.find(query).sort(descending("version")).first();

        if (higherVersionDocument == null) {
            throw new DataNotFoundException("No survey found for the given acronym");
        }
        return (Integer) higherVersionDocument.get("version");

    }

}
