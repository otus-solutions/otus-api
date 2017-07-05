package br.org.otus.survey.activity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.service.ActivityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.rest.Response;
import br.org.otus.survey.activity.api.ActivityFacade;

@RunWith(PowerMockRunner.class)
public class ActivityResourceTest {
	private static final long RECRUIMENT_NUMBER = 3051442;
	private static final String ID_SURVEY_ACITIVITY = "591a40807b65e4045b9011e7";
	
	@Mock
	ActivityFacade activityFacade;
	@InjectMocks
	ActivityResource activityResource;
	@Mock
	SurveyActivity surveyActivity;	
	@Mock
	ParticipantFacade participantFacade;	
	@Mock
	ActivityService activityService;	

	private String jsonString;
	private SurveyActivity activityDeserialize;
	private List<SurveyActivity> listSurveyActivity;
	private Participant participant;	
	
	
	@Before
	public void setUp() {			
		participant = new Participant((long) RECRUIMENT_NUMBER);
		when(participantFacade.getByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
		
		jsonString = ActivitySimplifiedFactory.create().toString();	
		activityDeserialize = SurveyActivity.deserialize(jsonString);
		listSurveyActivity = new ArrayList<SurveyActivity>();
		listSurveyActivity.add(SurveyActivity.deserialize(jsonString));							
	}	
	
	@Test
	public void method_getAll_should_return_entire_list_by_recuitment_number(){				
		when(activityFacade.list(RECRUIMENT_NUMBER)).thenReturn(listSurveyActivity);				
		String listSurveyActivityExpected = new Response().buildSuccess(listSurveyActivity).toSurveyJson();
		
		assertEquals(listSurveyActivityExpected, activityResource.getAll(RECRUIMENT_NUMBER));
		Mockito.verify(participantFacade).getByRecruitmentNumber(RECRUIMENT_NUMBER);	
	}

	@Test
	public void method_createActivity_should_return_ObjectResponse() {
		
		when(activityFacade.deserialize(jsonString)).thenReturn(activityDeserialize);
		when(activityFacade.create(activityDeserialize)).thenReturn(ID_SURVEY_ACITIVITY);		
		String activityExpected =  "{\"data\":\"591a40807b65e4045b9011e7\"}";	
		
		assertEquals(activityExpected, activityResource.createActivity(RECRUIMENT_NUMBER, jsonString));
		verify(participantFacade).getByRecruitmentNumber(anyLong());
		verify(activityFacade).deserialize(anyString());
		verify(activityFacade).create(anyObject());				
	}	
	
	@Test
	public void method_getByID_should_return_ObjectResponse() throws DataNotFoundException{
		when(participantFacade.getByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
		when(activityFacade.getByID(ID_SURVEY_ACITIVITY)).thenReturn(activityDeserialize);
		when(activityService.getByID(ID_SURVEY_ACITIVITY)).thenReturn(activityDeserialize);
		
		String responseExpected = new Response().buildSuccess(activityFacade.getByID(ID_SURVEY_ACITIVITY)).toSurveyJson();		
		assertEquals(responseExpected, activityResource.getByID(RECRUIMENT_NUMBER, ID_SURVEY_ACITIVITY));
	}
		
	@Test
	public void method_update_should_return_update_ObjectResponse(){
		
		when(activityFacade.deserialize(jsonString)).thenReturn(activityDeserialize);		
		when(activityFacade.updateActivity(activityFacade.deserialize(jsonString))).thenReturn(activityDeserialize);
		SurveyActivity deserializeActivityUpdate = activityFacade.updateActivity(activityFacade.deserialize(jsonString));
		SurveyActivity updatedActivity = activityFacade.updateActivity(deserializeActivityUpdate);
		String responseExpected = new Response().buildSuccess(updatedActivity).toSurveyJson();		
		
		assertEquals(responseExpected, activityResource.update(RECRUIMENT_NUMBER, ID_SURVEY_ACITIVITY, jsonString));		
		verify(participantFacade).getByRecruitmentNumber(anyLong());				
				
	}	

}
