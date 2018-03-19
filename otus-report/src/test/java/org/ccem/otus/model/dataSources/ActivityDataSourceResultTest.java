package org.ccem.otus.model.dataSources;

import com.google.gson.GsonBuilder;

import org.ccem.otus.model.dataSources.activity.ActivityDataSourceResult;
import org.ccem.otus.model.survey.activity.User;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ActivityDataSourceResultTest {

    private ActivityDataSourceResult activityDataSourceResult;

    private ActivityStatus activityStatus;

    private User user;

    private ActivityStatusOptions activityStatusOptions;

    private String activityDataSourceResultJson;

    private ArrayList<ActivityStatus> statusHistory;

    @Before
    public void setUp() {
        activityStatusOptions = ActivityStatusOptions.CREATED;

        user = new User();
        Whitebox.setInternalState(user, "name", "Joao");

        activityStatus = new ActivityStatus();
        Whitebox.setInternalState(activityStatus, "objectType", "ActivityStatus");
        Whitebox.setInternalState(activityStatus, "name", activityStatusOptions);
        Whitebox.setInternalState(activityStatus, "date", LocalDateTime.now());
        Whitebox.setInternalState(activityStatus, "user", user);

        statusHistory = new ArrayList<>();
        statusHistory.add(activityStatus);
        activityDataSourceResult = new ActivityDataSourceResult();
        Whitebox.setInternalState(activityDataSourceResult, "statusHistory", statusHistory);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        activityDataSourceResultJson = builder.create().toJson(activityDataSourceResult);
    }

    @Test
    public void method_serialize_should_return_json_of_activityDataSourceResult() {
        assertEquals(activityDataSourceResultJson,ActivityDataSourceResult.serialize(activityDataSourceResult));
    }

    @Test
    public void method_deserialize_should_return_activityDataSourceResult() {
        ActivityDataSourceResult result = ActivityDataSourceResult.deserialize(activityDataSourceResultJson);
        assertEquals(result.getStatusHistory().get(0).getUser().getName(),activityStatus.getUser().getName());
        assertEquals(result.getStatusHistory().get(0).getObjectType(),activityStatus.getObjectType());
        assertEquals(result.getStatusHistory().get(0).getName(),activityStatus.getName());
        assertEquals(result.getStatusHistory().get(0).getDate(),activityStatus.getDate());
    }

}
