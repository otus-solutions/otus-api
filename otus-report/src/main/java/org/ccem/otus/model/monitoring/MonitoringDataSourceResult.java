package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.utils.AnswerAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.time.LocalDateTime;

public class MonitoringDataSourceResult {

    private String fieldCenter;
    private Integer month;
    private Integer year;
    private String acronym;
    private String sum;
    private LocalDateTime firstActivity;

    public static MonitoringDataSourceResult deserialize(String monitoringDataSourceResult) {
        return getGsonBuilder().create().fromJson(monitoringDataSourceResult, MonitoringDataSourceResult.class);
    }

    /**
     * @return a GsonBuilder instance with AnswerAdapter, ObjectIdToStringAdapter
     *         registered and also all registered adapters of SurveyForm.
     *         {@link SurveyForm#getGsonBuilder}
     *
     */
    public static GsonBuilder getGsonBuilder() {

        GsonBuilder builder = new GsonBuilder();

        return builder;
    }

    public void setDate(){
        month = firstActivity.getMonthValue();
        year = firstActivity.getYear();
    }

    public String getFieldCenter() {
        return fieldCenter;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getSum() {
        return sum;
    }

}
