package br.org.otus.survey;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.service.user.group.UserPermission;
import org.ccem.otus.persistence.SurveyDao;
import org.ccem.otus.survey.form.SurveyForm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;

public class SurveyDaoBean extends MongoGenericDao<Document> implements SurveyDao {

  private static final String COLLECTION_NAME = "survey";

  public SurveyDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  @UserPermission
  public List<SurveyForm> findUndiscarded(List<String> permittedAcronyms, String userEmail) {
    ArrayList<SurveyForm> surveys = new ArrayList<SurveyForm>();
    Document query = new Document("isDiscarded", false).append("surveyTemplate.identity.acronym",new Document("$in",permittedAcronyms));
    collection.aggregate(Arrays.asList(new Document("$match",query))).forEach((Block<Document>) document -> {
      surveys.add(SurveyForm.deserialize(document.toJson()));
    });

    return surveys;
  }

  @Override
  public List<SurveyForm> findByAcronym(String acronym) {
    ArrayList<SurveyForm> surveys = new ArrayList<>();
    collection.find(eq("surveyTemplate.identity.acronym", acronym)).forEach((Block<Document>) document -> {
      surveys.add(SurveyForm.deserialize(document.toJson()));
    });

    return surveys;
  }

  @Override
  public List<SurveyForm> findByCustomId(Set<String> ids, String surveyAcronym) {
    ArrayList<SurveyForm> surveys = new ArrayList<>();
    collection
      .find(and(
        not(
          eq("surveyTemplate.identity.acronym", surveyAcronym)
        ),
        or(
          in("surveyTemplate.itemContainer.customID", ids),
          in("surveyTemplate.itemContainer.options.customOptionID", ids)
        )
        )
      )
      .forEach((Block<Document>) document -> {
        surveys.add(SurveyForm.deserialize(document.toJson()));
      });

    return surveys;

  }

  @Override
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

  @Override
  public ObjectId persist(SurveyForm survey) {
    Document parsed = Document.parse(SurveyForm.serialize(survey));
    parsed.remove("_id");
    collection.insertOne(parsed);

    return parsed.getObjectId("_id");
  }

  @Override
  public boolean updateLastVersionSurveyType(String acronym, String surveyFormType) throws DataNotFoundException {
    Document query = new Document("surveyTemplate.identity.acronym", acronym);
    query.put("isDiscarded", false);

    UpdateResult updateOne = collection.updateOne(query,
      new Document("$set", new Document("surveyFormType", surveyFormType)));

    if (updateOne.getMatchedCount() == 0) {
      throw new DataNotFoundException("SURVEY ACRONYM {" + acronym.toUpperCase() + "} not found.");
    }

    return updateOne.getModifiedCount() > 0;
  }

  @Override
  public boolean deleteLastVersionByAcronym(String acronym) throws DataNotFoundException {
    Document query = new Document("surveyTemplate.identity.acronym", acronym);
    query.put("isDiscarded", false);

    UpdateResult updateResult = collection
      .updateOne(
        query,
        new Document("$set", new Document("isDiscarded", true)),
        new UpdateOptions().upsert(false)
      );

    if (updateResult.getMatchedCount() == 0) {
      throw new DataNotFoundException("No survey found to discard");
    }

    return updateResult.getModifiedCount() != 0;
  }

  @Override
  public boolean discardSurvey(ObjectId id) throws DataNotFoundException {
    Document query = new Document("_id", id);

    UpdateResult updateResult = collection
      .updateOne(
        query,
        new Document("$set", new Document("isDiscarded", true)),
        new UpdateOptions().upsert(false)
      );

    if (updateResult.getMatchedCount() == 0) {
      throw new DataNotFoundException("No survey found to discard");
    }

    return updateResult.getModifiedCount() != 0;
  }

  @Override
  public SurveyForm getLastVersionByAcronym(String acronym) throws DataNotFoundException {
    Document query = new Document();

    query.put("surveyTemplate.identity.acronym", acronym.toUpperCase());

    Document higherVersionDocument = collection.find(query).sort(descending("version")).first();

    if (higherVersionDocument == null) {
      return null;
    }

    return SurveyForm.deserialize(higherVersionDocument.toJson());
  }

  @Override
  public List<Integer> getSurveyVersions(String acronym) {
    Document query = new Document("surveyTemplate.identity.acronym", acronym);
    ArrayList<Integer> versions = new ArrayList<>();

    for (Integer integer : collection.distinct("version", query, Integer.class)) {
      versions.add(integer);
    }
    return versions;
  }

  @Override
  public List<String> listAcronyms() {
    ArrayList<String> results = new ArrayList<>();
    for (String acronym : collection.distinct("surveyTemplate.identity.acronym", String.class)) {
      results.add(acronym);
    }
    return results;

  }

  @Override
  public List<String> aggregate(ArrayList<Document> query) {
    ArrayList<String> documents = new ArrayList<>();
    MongoCursor<Document> iterator = collection.aggregate(query).iterator();


    while (iterator.hasNext()) {
      documents.add(iterator.next().toJson());
    }

    return documents;
  }

}
