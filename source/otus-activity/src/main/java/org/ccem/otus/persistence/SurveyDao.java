package org.ccem.otus.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.jumpMap.SurveyJumpMap;
import org.ccem.otus.survey.form.SurveyForm;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.result.UpdateResult;

public interface SurveyDao {
  List<SurveyForm> findUndiscarded(List<String> permitedAcronyms, String userEmail);

  List<SurveyForm> findAllUndiscarded();

  List<SurveyForm> findByAcronym(String acronym);

  List<SurveyForm> findByCustomId(Set<String> ids, String surveyAcronym);

  SurveyForm get(String acronym, Integer version) throws DataNotFoundException;

  ObjectId persist(SurveyForm survey);

  boolean updateLastVersionSurveyType(String acronym, String surveyFormType) throws DataNotFoundException;

  boolean deleteLastVersionByAcronym(String acronym) throws DataNotFoundException;

  boolean discardSurvey(ObjectId id) throws DataNotFoundException;

  SurveyForm getLastVersionByAcronym(String acronym) throws DataNotFoundException;

  List<Integer> getSurveyVersions(String acronym);

  List<String> listAcronyms();

  List<String> aggregate(ArrayList<Document> query);

  SurveyJumpMap createJumpMap(String acronym, Integer version);

  UpdateResult updateSurveyRequiredExternalID(ObjectId surveyID, Boolean stateRequiredExternalID) throws DataNotFoundException;

  AggregateIterable<Document> aggregate(List<Bson> query);

  Map<String, String> getAcronymNameMap() throws MemoryExcededException;
}
