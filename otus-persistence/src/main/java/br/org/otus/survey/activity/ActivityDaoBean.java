package br.org.otus.survey.activity;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.persistence.ActivityDao;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;

@Stateless
public class ActivityDaoBean extends MongoGenericDao implements ActivityDao {

	private static final String COLLECTION_NAME = "Activity";

	public ActivityDaoBean() {
		super(COLLECTION_NAME);
	}

	@Override
	public List<SurveyActivity> find(long rn) {
		ArrayList<SurveyActivity> activities = new ArrayList<SurveyActivity>();

		FindIterable<Document> result = collection.find(eq("participantData.recruitmentNumber", rn));
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

	@Override
	public SurveyActivity findByID(String id) throws DataNotFoundException {
		ObjectId oid = new ObjectId(id);
		Document result = collection.find(eq("_id", oid)).first();

		if (result == null) {
			throw new DataNotFoundException(
					new Throwable("OID {" + id + "} not found."));
		}

		return SurveyActivity.deserialize(result.toJson());
	}

}
