package org.ccem.otus.model.survey.activity;

import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class SurveyActivityTest {

  private static final String statusHistory = "[{\"objectType\":\"ActivityStatus\",\"name\":\"CREATED\",\"date\":\"2017-04-12T10:35:11.971Z\",\"user\":{\"name\":\"Fulano\",\"surname\":\"Detal\",\"phone\":\"5199999999\",\"email\":\"fulano@yahoo.com\"}},{\"objectType\":\"ActivityStatus\",\"name\":\"OPENED\",\"date\":\"2017-04-12T11:16:08.584Z\",\"user\":{\"name\":\"Maria\",\"surname\":\"Aparecida\",\"phone\":\"5199999999\",\"email\":\"maria@gmail.com\"}},{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_ONLINE\",\"date\":\"2017-04-12T11:16:59.154Z\",\"user\":{\"name\":\"Maria\",\"surname\":\"da Graça\",\"phone\":\"5199999999\",\"email\":\"dagraca@gmail.com\"}},{\"objectType\":\"ActivityStatus\",\"name\":\"FINALIZED\",\"date\":\"2017-04-12T11:28:05.250Z\",\"user\":{\"name\":\"Maria\",\"surname\":\"Aparecida\",\"phone\":\"5199999999\",\"email\":\"maria@gmail.com\"}}]";

    @Test
    public void getLastStatusHistory() {
      SurveyActivity surveyActivity = SurveyActivity.deserialize("{\"statusHistory\":" + statusHistory + "}");
      ActivityStatus activityStatus = surveyActivity.getLastStatus().get();
      Assert.assertEquals(activityStatus.getUser().getEmail(), "maria@gmail.com");
    }
}