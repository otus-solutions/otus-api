package br.org.otus.microservice.prediction.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import javax.json.Json;
import java.time.LocalDateTime;

public class ActivityPredictionTest {
  private static String ID = "507f1f77bcf86cd799439019";

  @Test
  public void shouldBuildActivityId(){
    ActivityPrediction activityPrediction = new ActivityPrediction();
    activityPrediction.setActivityId(new ObjectId(ID));

    JsonObject object = new Gson().fromJson(activityPrediction.serialize(), JsonObject.class);
    Assert.assertEquals(object.get("activityId").getAsString(), ID);
  }

  @Test
  public void shouldBuildPredictionDate(){
    ActivityPrediction activityPrediction = new ActivityPrediction();

    JsonObject object = new Gson().fromJson(activityPrediction.serialize(), JsonObject.class);
    Assert.assertNotNull(object.get("predictionDate").getAsString());
  }

  @Test
  public void shouldAddOnlyValidQuestionType(){
    QuestionForPrediction singleSelectionQuestion = new QuestionForPrediction();
    singleSelectionQuestion.setType("SingleSelectionQuestion");

    QuestionForPrediction integerQuestion = new QuestionForPrediction();
    integerQuestion.setType("IntegerQuestion");

    ActivityPrediction activityPrediction = new ActivityPrediction();
    activityPrediction.addQuestionForPrediction(singleSelectionQuestion);
    activityPrediction.addQuestionForPrediction(integerQuestion);

    JsonObject object = new Gson().fromJson(activityPrediction.serialize(), JsonObject.class);
    JsonArray questions = object.getAsJsonArray("questions");
    Assert.assertEquals(questions.size(), 1);

    Assert.assertEquals(questions.get(0).getAsJsonObject().get("type").getAsString(),"SingleSelectionQuestion");
  }
}
