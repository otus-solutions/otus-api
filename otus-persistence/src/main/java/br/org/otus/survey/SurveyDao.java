package br.org.otus.survey;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.or;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.survey.form.SurveyForm;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;

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

	public SurveyForm get(String acronym, Integer version) throws DataNotFoundException {
		Document query = new Document();
		ArrayList<SurveyForm> surveys = new ArrayList<>();
		query.put("surveyTemplate.identity.acronym", acronym.toUpperCase());
		query.put("version", version);

		FindIterable<Document> result = collection.find(query);

		result.forEach((Block<Document>) document -> surveys.add(SurveyForm.deserialize(document.toJson())));
		if (surveys.size() == 0) {
			throw new DataNotFoundException(new Throwable(
					"SURVEY ACRONYM {" + acronym.toUpperCase() + "} VERSION {" + version.toString() + "} not found."));
		}

		return surveys.get(0);
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

		return updateOne.getModifiedCount() > 0 ? true : false;
	}

	public boolean deleteByAcronym(String acronym) {
		DeleteResult deleteResult = collection.deleteOne(eq("surveyTemplate.identity.acronym", acronym.toUpperCase()));

		return deleteResult.getDeletedCount() > 0 ? true : false;
	}

}
