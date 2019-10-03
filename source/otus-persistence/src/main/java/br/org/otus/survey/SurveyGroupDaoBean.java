package br.org.otus.survey;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.group.SurveyGroup;
import org.ccem.otus.persistence.SurveyGroupDao;

import com.google.gson.GsonBuilder;
import com.mongodb.Block;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;

import javax.inject.Inject;

public class SurveyGroupDaoBean extends MongoGenericDao<Document> implements SurveyGroupDao {
    private static final String COLLECTION_NAME = "survey_group";

    @Inject
    private SurveyDaoBean surveyDaoBean;

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

    @Override
    public List<SurveyGroup> getSurveyGroupsByUser(Set<String> userGroups) {
        List<SurveyGroup> surveyGroups = new ArrayList<>();
        collection.find(in("name",userGroups)).forEach((Block<Document>) document -> {
            surveyGroups.add(SurveyGroup.deserialize(document.toJson()));
        });
        return surveyGroups;
    }

    @Override
    public List<String> getOrphanSurveys() {
        List<String> acronyms = new ArrayList<>();
        List<String> acronymsInGroups = getAcronymsInGroups();
        if(acronymsInGroups != null) {
            List<Bson> pipeline = new ArrayList<>();
            pipeline.add(parseQuery("{\n" +
                    "        $lookup:{\n" +
                    "            from:\"survey\",\n" +
                    "            let:{inGroupAcronyms:" + acronymsInGroups + "},\n" +
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
            if (first.size() > 0)
                acronyms = (List<String>) first.get("orphanSurveys");
        } else {
            acronyms = surveyDaoBean.listAcronyms();
        }
        return acronyms;
    }

    private List<String> getAcronymsInGroups(){
        List<String> acronymsInGroups = null;
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(parseQuery("{\"$unwind\":{\"path\":\"$surveyAcronyms\",\"preserveNullAndEmptyArrays\":true}}"));
        pipeline.add(parseQuery("{$group:{_id:\"$surveyAcronyms\"}}"));
        pipeline.add(parseQuery("{$group:{_id:{},surveyAcronyms:{$push:\"$_id\"}}}"));

        Document first = collection.aggregate(pipeline).first();
        if(first != null) {
            acronymsInGroups = (List<String>) first.get("surveyAcronyms");
        }
        return acronymsInGroups;
    }

    @Override
    public List<String> getUserPermittedSurveys(Set<String> surveyGroups) {
        List<String> acronyms = new ArrayList<>();
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(new Document("$match",new Document("name",new Document("$in",surveyGroups))));
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