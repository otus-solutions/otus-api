package br.org.otus.survey.activity;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.fields;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.persistence.ActivityDao;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;

@Stateless
public class ActivityDaoBean extends MongoGenericDao<Document> implements ActivityDao {

	public static final String COLLECTION_NAME = "activity";
	public static final String ACRONYM_PATH = "surveyForm.surveyTemplate.identity.acronym";
	public static final String VERSION_PATH = "surveyForm.version";
	public static final String DISCARDED_PATH = "isDiscarded";
	public static final String TEMPLATE_PATH = "surveyForm.surveyTemplate";
	public static final String RECRUITMENT_NUMBER_PATH = "participantData.recruitmentNumber";
	public static final String CATEGORY_NAME_PATH = "category.name";
	public static final String CATEGORY_LABEL_PATH = "category.label";

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

		FindIterable<Document> result = collection
				.find(and(eq(RECRUITMENT_NUMBER_PATH, rn), eq(DISCARDED_PATH, false)));
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
		UpdateResult updateOne = collection.updateOne(eq("_id", surveyActivity.getActivityID()),
				new Document("$set", parsed), new UpdateOptions().upsert(false));

		if (updateOne.getMatchedCount() == 0) {
			throw new DataNotFoundException(
					new Throwable("OID {" + surveyActivity.getActivityID().toString() + "} not found."));
		}

		return surveyActivity;
	}

	/**
	 * This method return specific activity.
	 *
	 * @param id
	 *            database id (_id)
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

		UpdateResult updateResult = collection.updateOne(query,
				new Document("$set", new Document(CATEGORY_LABEL_PATH, activityCategory.getLabel())),
				new UpdateOptions().upsert(false));

	}

}
