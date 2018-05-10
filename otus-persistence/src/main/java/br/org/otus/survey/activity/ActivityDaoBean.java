package br.org.otus.survey.activity;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.persistence.ActivityDao;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;

@Stateless
public class ActivityDaoBean extends MongoGenericDao<Document> implements ActivityDao {

	private static final String COLLECTION_NAME = "activity";

	public ActivityDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	/**
	 * Return activities considering that they were not discarded
	 * 
	 * @param rn
	 * @return
	 */
	@Override
	public List<SurveyActivity> find(long rn) {
		ArrayList<SurveyActivity> activities = new ArrayList<SurveyActivity>();

		FindIterable<Document> result = collection.find(and(eq("participantData.recruitmentNumber", rn), eq("isDiscarded", false)));
		result.forEach((Block<Document>) document -> {
			activities.add(SurveyActivity.deserialize(document.toJson()));
		});

		return activities;
	}

	@Override
	public ObjectId persist(SurveyActivity surveyActivity) {
		Document parsed = Document.parse(SurveyActivity.serialize(surveyActivity));
		parsed.remove("_id");
		collection.insertOne(parsed);

		return parsed.getObjectId("_id");
	}

	@Override
	public SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException {
		Document parsed = Document.parse(SurveyActivity.serialize(surveyActivity));
		parsed.remove("_id");
		UpdateResult updateOne = collection.updateOne(eq("_id", surveyActivity.getActivityID()), new Document("$set", parsed), new UpdateOptions().upsert(false));

		if (updateOne.getMatchedCount() == 0) {
			throw new DataNotFoundException(new Throwable("OID {" + surveyActivity.getActivityID().toString() + "} not found."));
		}

		return surveyActivity;
	}

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
	public List<SurveyActivity> getActivitiesToExtraction(String id) throws DataNotFoundException {
		ArrayList<SurveyActivity> activities = new ArrayList<>();
		ArrayList<Document> query = new ArrayList<>();

		query.add(this.buildQueryToExtraction(id));

		AggregateIterable<Document> result = collection.aggregate(query);

		result.forEach((Block<Document>) document -> activities.add(SurveyActivity.deserialize(document.toJson())));
		if (activities.size() == 0) {
			throw new DataNotFoundException(new Throwable("OID {" + id + "} not found."));
		}

		return activities;
	}
	
	@Override
	public List<SurveyActivity> findAllByIDWithVersion(String id, Integer version) throws DataNotFoundException {
		ArrayList<SurveyActivity> activities = new ArrayList<>();

//		FindIterable<Document> result = collection.find(eq("surveyForm.surveyTemplate.identity.acronym", id));

		Document query = new Document();
		query.put("surveyForm.surveyTemplate.identity.acronym", id);
		query.put("surveyForm.version", version);
		
		Bson projection = fields(exclude("surveyForm.surveyTemplate"));


		FindIterable<Document> result = collection.find(query).projection(projection);

		result.forEach((Block<Document>) document -> activities.add(SurveyActivity.deserialize(document.toJson())));
		if (activities.size()== 0) {
			throw new DataNotFoundException(
					new Throwable("OID {" + id + "} not found."));
		}

		return activities;
	}

	private Document buildQueryToExtraction(String id) {
		return new Document("$match", new Document("surveyForm.surveyTemplate.identity.acronym", id).append("isDiscarded", Boolean.FALSE));
	}

	@Override
	public List<SurveyActivity> findByCategory(String categoryName) {
		ArrayList<SurveyActivity> activities = new ArrayList<>();

		FindIterable<Document> result = collection.find(eq("category.name", categoryName));

		result.forEach((Block<Document>) document -> activities.add(SurveyActivity.deserialize(document.toJson())));
		return activities;
	}

	@Override
	public void updateCategory(ActivityCategory activityCategory) {
		Document query = new Document();
		query.put("category.name", activityCategory.getName());

		UpdateResult updateResult = collection.updateOne(query, new Document("$set", new Document("category.label", activityCategory.getLabel())), new UpdateOptions().upsert(false));

	}

}
