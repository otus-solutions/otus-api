package org.ccem.otus.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.dto.StageSurveyActivitiesDto;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollectionGroupsDTO;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface ActivityService {

  String create(SurveyActivity surveyActivity);

  SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException;

  List<SurveyActivity> list(long rn, String userEmail);

  List<StageSurveyActivitiesDto> listByStageGroups(long rn, String userEmail, Map<ObjectId, String> stageMap) throws MemoryExcededException;

  SurveyActivity getByID(String id) throws DataNotFoundException;

  List<SurveyActivity> get(String acronym, Integer version) throws DataNotFoundException, MemoryExcededException;

  List<SurveyActivity> getExtraction(String acronym, Integer version) throws DataNotFoundException, MemoryExcededException;

  boolean updateCheckerActivity(String checkerUpdated) throws DataNotFoundException;

  SurveyActivity getActivity(String acronym, Integer version, String categoryName, Long recruitmentNumber) throws DataNotFoundException;

  LinkedList<ActivityProgressResultExtraction> getActivityProgressExtraction(String center) throws DataNotFoundException;

  HashMap<Long, String> getParticipantFieldCenterByActivity(String acronym, Integer version) throws DataNotFoundException;

  void save(String userEmail, OfflineActivityCollection offlineActivityCollection) throws DataNotFoundException;

  OfflineActivityCollectionGroupsDTO fetchOfflineActivityCollections(String userEmail) throws DataNotFoundException;

  OfflineActivityCollection fetchOfflineActivityCollection(String collectionId) throws DataNotFoundException;

  void deactivateOfflineActivityCollection(String offlineCollectionId, List<ObjectId> createdActivityIds);

  boolean updateParticipantEmail(long rn, String email);

  void removeStageFromActivities(ObjectId stageOID);

  void discardByID(ObjectId activityOID) throws DataNotFoundException;

}
