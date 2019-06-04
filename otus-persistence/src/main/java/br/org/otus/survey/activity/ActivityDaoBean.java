package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import com.google.gson.GsonBuilder;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.model.survey.activity.dto.CheckerUpdatedDTO;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.permissions.service.user.group.UserPermission;
import org.ccem.otus.persistence.ActivityDao;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.fields;

@Stateless
public class ActivityDaoBean extends MongoGenericDao<Document> implements ActivityDao {

    public static final String COLLECTION_NAME = "activity";
    public static final String ACRONYM_PATH = "surveyForm.acronym";
    public static final String VERSION_PATH = "surveyForm.version";
    public static final String DISCARDED_PATH = "isDiscarded";
    public static final String TEMPLATE_PATH = "surveyForm.surveyTemplate";
    public static final String RECRUITMENT_NUMBER_PATH = "participantData.recruitmentNumber";
    public static final String CATEGORY_NAME_PATH = "category.name";
    public static final String CATEGORY_LABEL_PATH = "category.label";

    public ActivityDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }

    /**
     * Return activities considering that they were not discarded
     *
     * @param rn
     * @return
     */
    @Override
    @UserPermission
    public List<SurveyActivity> find(List<String> permittedSurveys, String userEmail, long rn) {
        ArrayList<SurveyActivity> activities = new ArrayList<SurveyActivity>();
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(new Document("$match",new Document(RECRUITMENT_NUMBER_PATH,rn).append(DISCARDED_PATH,false).append(ACRONYM_PATH,new Document("$in",permittedSurveys))));
//        pipeline.add(parseQuery("{\"$lookup\":{\"from\":\"survey\",\"let\":{\"acronym\":\"$surveyForm.acronym\",\"version\":\"$surveyForm.version\"},\"pipeline\":[{\"$match\":{\"$expr\":{\"$and\":[{\"$eq\":[\"$surveyTemplate.identity.acronym\",\"$$acronym\"]},{\"$eq\":[\"$version\",\"$$version\"]}]}}},{\"$replaceRoot\":{\"newRoot\":\"$surveyTemplate\"}}],\"as\":\"surveyForm.surveyTemplate\"}}"));
//        pipeline.add(parseQuery("{\"$addFields\":{\"surveyForm.surveyTemplate\":{$arrayElemAt: [\"$surveyForm.surveyTemplate\",0]}}}"));
        AggregateIterable<Document> result = collection.aggregate(pipeline);
        
        result.forEach((Block<Document>) document -> {
            activities.add(SurveyActivity.deserialize(document.toJson()));
        });

        return activities;
    }
//
//    @Override
//    @UserPermission
//    public SurveyActivity findActivityFull(String acronym, String userEmail, long rn){
//        SurveyActivity activity = new SurveyActivity();
//        List<Bson> pipeline = new ArrayList<>();
//        pipeline.add(parseQuery(" {\n" +
//                "        $match:{\n" +
//                "            \""+RECRUITMENT_NUMBER_PATH+,"\":"+rn+",\n" +
//                "            \""+DISCARDED_PATH+"\":false,\n" +
//                "            \""+ACRONYM_PATH+"\": \""+acronym+"\"\n" +
//                "        }\n" +
//                "            \n" +
//                "    }"));
//        pipeline.add(parseQuery("{ \n" +
//                "        $lookup:{\n" +
//                "                \"from\":\"survey\",\n" +
//                "                let:{\n" +
//                "                    \"acronym\":\"$surveyForm.acronym\",\n" +
//                "                    \"version\":\"$surveyForm.version\"},\n" +
//                "                    \"pipeline\":[\n" +
//                "                        {\"$match\":{\n" +
//                "                            \"$expr\":{\n" +
//                "                                \"$and\":[\n" +
//                "                                    {\n" +
//                "                                        \"$eq\":[\"$surveyTemplate.identity.acronym\",\"$$acronym\"]\n" +
//                "                                        \n" +
//                "                                    },\n" +
//                "                                        {\n" +
//                "                                            \"$eq\":[\"$version\",\"$$version\"]\n" +
//                "                                            \n" +
//                "                                        }]\n" +
//                "                                \n" +
//                "                            }}},\n" +
//                "                                        {\n" +
//                "                                            \"$replaceRoot\":{\n" +
//                "                                                \"newRoot\":\"$surveyTemplate\"\n" +
//                "                                                \n" +
//                "                                            }\n" +
//                "                                            \n" +
//                "                                        }\n" +
//                "                                        ],\n" +
//                "                                        \"as\":\"surveyForm.surveyTemplate\"\n" +
//                "                \n" +
//                "            }\n" +
//                "        }"));
//        pipeline.add(parseQuery("{\"$addFields\":{\"surveyForm.surveyTemplate\":{$arrayElemAt: [\"$surveyForm.surveyTemplate\",0]}}}"));
////        AggregateIterable<Document> result = collection.aggregate(pipeline);
//
//        Document result = collection.aggregate(pipeline).first();
//
//        if(result != null){
//            activity = SurveyActivity.deserialize(result.toJson());
//        }
//
////        result.forEach((Block<Document>) document -> {
////            activities.add(SurveyActivity.deserialize(document.toJson()));
////        });
//
//        return activity;
//    }

    @Override
    public ObjectId persist(SurveyActivity surveyActivity) {
        Document parsed = Document.parse(SurveyActivity.serialize(surveyActivity));
        removeOids(parsed);
        collection.insertOne(parsed);

        return parsed.getObjectId("_id");
    }

    @Override
    public SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException {
        Document parsed = Document.parse(SurveyActivity.serialize(surveyActivity));
        removeOids(parsed);
        UpdateResult updateOne = collection.updateOne(eq("_id", surveyActivity.getActivityID()), new Document("$set", parsed), new UpdateOptions().upsert(false));

        if (updateOne.getMatchedCount() == 0) {
            throw new DataNotFoundException(new Throwable("OID {" + surveyActivity.getActivityID().toString() + "} not found."));
        }

        return surveyActivity;
    }

    /**
     * This method return specific activity.
     *
     * @param id database id (_id)
     * @return
     * @throws DataNotFoundException
     */
    @Override
    public SurveyActivity findByID(String id) throws DataNotFoundException {
        ObjectId oid = new ObjectId(id);
        Document result = collection.find(eq("_id", oid)).first();

        if (result == null) {
            throw new DataNotFoundException(new Throwable("OID {" + id + "} not found."));
        }

        return SurveyActivity.deserialize(result.toJson());
    }

	@Override
	public List<SurveyActivity> getUndiscarded(String acronym, Integer version)
			throws DataNotFoundException, MemoryExcededException {
		Document query = new Document();
		query.put(ACRONYM_PATH, acronym);
		query.put(VERSION_PATH, version);
		query.put(DISCARDED_PATH, Boolean.FALSE);
		Bson projection = fields(exclude(TEMPLATE_PATH));

		FindIterable<Document> documents = collection.find(query).projection(projection);
		MongoCursor<Document> iterator = documents.iterator();
		ArrayList<SurveyActivity> activities = new ArrayList<>();

		while (iterator.hasNext()) {
			try {
				activities.add(SurveyActivity.deserialize(iterator.next().toJson()));
			} catch (OutOfMemoryError e) {
				activities.clear();
				activities = null;
				throw new MemoryExcededException("Extraction {" + acronym + "} exceded memory used.");
			}
		}

		if (activities.isEmpty()) {
			throw new DataNotFoundException(new Throwable("OID {" + acronym + "} not found."));
		}

        return activities;
    }

    @Override
    public List<SurveyActivity> findByCategory(String categoryName) {
        ArrayList<SurveyActivity> activities = new ArrayList<>();

        FindIterable<Document> result = collection.find(eq(CATEGORY_NAME_PATH, categoryName));

        result.forEach((Block<Document>) document -> activities.add(SurveyActivity.deserialize(document.toJson())));
        return activities;
    }

    @Override
    public void updateCategory(ActivityCategory activityCategory) {
        Document query = new Document();
        query.put("category.name", activityCategory.getName());

        UpdateResult updateResult = collection.updateOne(query, new Document("$set", new Document(CATEGORY_LABEL_PATH, activityCategory.getLabel())), new UpdateOptions().upsert(false));
    }

    @Override
    public boolean updateCheckerActivity(CheckerUpdatedDTO checkerUpdatedDTO) throws DataNotFoundException {
        String checkerUpdateJson = ActivityStatus.serialize(checkerUpdatedDTO.getActivityStatus());
        Document parsed = Document.parse(checkerUpdateJson);
        Document checkerUpdate = (Document) parsed.get("user");
        String dateUpdated = (String) parsed.get("date");

        UpdateResult updateResult = collection.updateOne(
                and(eq("_id", new ObjectId(checkerUpdatedDTO.getId())),
                    eq("statusHistory.name", checkerUpdatedDTO.getActivityStatus().getName())),
                new Document("$set", new Document("statusHistory.$.user", checkerUpdate).append("statusHistory.$.date",dateUpdated)));

        if (updateResult.getMatchedCount() == 0) {
            throw new DataNotFoundException(new Throwable("Activity of Participant not found"));
        }

        return true;
    }

	private void removeOids(Document parsedActivity){
        parsedActivity.remove("_id");
        ((Document) parsedActivity.get("surveyForm")).remove("_id"); //todo: remove when this id becomes standard
    }

}
