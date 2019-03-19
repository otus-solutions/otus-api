package br.org.otus.survey;

import br.org.mongodb.MongoGenericDao;
import com.google.gson.GsonBuilder;
import com.mongodb.Block;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.persistence.SurveyGroupDao;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class SurveyGroupDaoBean extends MongoGenericDao<Document> implements SurveyGroupDao {
    private static final String COLLECTION_NAME = "survey_group";

    public SurveyGroupDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public List<SurveyGroup> getListOfSurveyGroups() {
        List<SurveyGroup> surveyGroups = new ArrayList<>();
        collection.find().forEach((Block<Document>) document -> {
            surveyGroups.add(SurveyGroup.deserialize(document.toJson()));
        });
        return surveyGroups;
    }

    @Override
    public Document findSurveyGroupByName(String surveyGroupName) throws DataNotFoundException {
        Document document = collection.find(eq("name", surveyGroupName)).first();
        if (document == null) throw new DataNotFoundException(new Throwable("SurveyGroup not found"));
        return document;
    }

    @Override
    public void findSurveyGroupNameConflits(String surveyGroupName) throws ValidationException {
        Document document = collection.find(eq("name", surveyGroupName)).first();
        if (document != null) throw new ValidationException(new Throwable("SurveyGroupName already in use"));
    }

    @Override
    public ObjectId persist(SurveyGroup surveyGroup) {
        Document parsed = Document.parse(SurveyGroup.serialize(surveyGroup));
        parsed.remove("_id");
        super.persist(parsed);
        return (ObjectId) parsed.get("_id");
    }

    @Override
    public String updateSurveyGroupAcronyms(SurveyGroup surveyGroup) {
        Bson filter = new Document("name", surveyGroup.getName());
        Bson updates = new Document("surveyAcronyms", surveyGroup.getSurveyAcronyms());
        Bson updateOperationDocument = new Document("$set", updates);
        UpdateResult result = collection.updateOne(filter, updateOperationDocument);
        return String.valueOf(result.getModifiedCount());
    }

    @Override
    public String updateSurveyGroupName(String originalName, String updateName) {
        Bson filter = new Document("name", originalName);
        Bson updates = new Document("name", updateName);
        Bson updateOperationDocument = new Document("$set", updates);
        UpdateResult result = collection.updateOne(filter, updateOperationDocument);
        return String.valueOf(result.getModifiedCount());
    }

    @Override
    public DeleteResult deleteSurveyGroup(String surveyGroupName) {
        Bson filter = new Document("name", surveyGroupName);
        return collection.deleteOne(filter);
    }

    //TODO: userEmail parameter waiting for user validation implementation in Otus.
    @Override
    public List<SurveyGroup> getSurveyGroupsByUser(String userEmail) {
        List<SurveyGroup> surveyGroups = new ArrayList<>();
        collection.find().forEach((Block<Document>) document -> {
            surveyGroups.add(SurveyGroup.deserialize(document.toJson()));
        });
        return surveyGroups;
    }

    @Override
    public List<String> getOrphanSurveys() {
        List<String> acronyms = new ArrayList<>();
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(parseQuery("{$unwind:\"$surveyAcronyms\"}"));
        pipeline.add(parseQuery("{$group:{_id:\"$surveyAcronyms\"}}"));
        pipeline.add(parseQuery("{$group:{_id:{},surveyAcronyms:{$push:\"$_id\"}}}"));
        pipeline.add(parseQuery("{\n" +
                "        $lookup:{\n" +
                "            from:\"survey\",\n" +
                "            let:{inGroupAcronyms:\"$surveyAcronyms\"},\n" +
                "            pipeline:[\n" +
                "                {\n" +
                "                    $match:{\n" +
                "                        $expr:{\n" +
                "                            $not:{$in:[\"$surveyTemplate.identity.acronym\",\"$$inGroupAcronyms\"]}\n" +
                "                        }\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    $group:{\n" +
                "                        _id:\"$surveyTemplate.identity.acronym\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    $group:{_id:{},orphans:{$push:\"$_id\"}}\n" +
                "                }\n" +
                "            ],\n" +
                "            as:\"orphan\"\n" +
                "        }\n" +
                "    }"));
        pipeline.add(parseQuery("{$replaceRoot:{newRoot:{orphanSurveys:{$arrayElemAt:[\"$orphan.orphans\",0]}}}}"));

        Document first = collection.aggregate(pipeline).first();
        if(first != null)
            acronyms = (List<String>) first.get("orphanSurveys");
        return acronyms;
    }

    @Override
    public List<String> getUserPermittedSurveys(List<String> surveyGroups) {
        List<String> acronyms = new ArrayList<>();
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(parseQuery("{$match:{\"name\":{$in:"+surveyGroups+"}}}"));
        pipeline.add(parseQuery("{$unwind:\"$surveyAcronyms\"}"));
        pipeline.add(parseQuery("{$group:{_id:\"$surveyAcronyms\"}}"));
        pipeline.add(parseQuery("{$group:{_id:{},userPermittedSurveys:{$push:\"$_id\"}}}"));

        Document first = collection.aggregate(pipeline).first();
        if(first != null)
            acronyms = (List<String>) first.get("userPermittedSurveys");
        return acronyms;
    }

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }

}