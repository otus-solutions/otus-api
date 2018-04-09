package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.DataSourceAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class ReportTemplate {
	private ObjectId _id;
    private String template;
    private String label;
    private String sender;
    private LocalDateTime sendingDate;
    private ArrayList<String> fieldCenter;
    private ArrayList<ReportDataSource> dataSources;

    public static String serialize(ReportTemplate reportTemplate) {
        return ReportTemplate.getGsonBuilder().create().toJson(reportTemplate);
    }

    public static ReportTemplate deserialize(String reportTemplateJson) throws ValidationException {
        ReportTemplate reportTemplate = ReportTemplate.getGsonBuilder().create().fromJson(reportTemplateJson, ReportTemplate.class);
        validate(reportTemplate);
        return reportTemplate;
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
        builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
        builder.registerTypeAdapter(ReportDataSource.class, new DataSourceAdapter());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        builder.disableHtmlEscaping();
        return builder;
    }

    public ArrayList<ReportDataSource> getDataSources(){
        return dataSources;
    }

    public String getTemplate() {
        return template;
    }

    public String getLabel() {
    	return label;
    }

    public ArrayList<String> getFieldCenter() {
    	return fieldCenter;
    }

	public ObjectId getId() {
		return _id;
	}

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    private static void validate(ReportTemplate reportTemplate) throws ValidationException {
        String result = "";
        result = validateTemplate(reportTemplate.template, result);
        result = validateLabel(reportTemplate.label, result);
        result = validateSendingDate(reportTemplate.sendingDate, result);
        result = validateFieldCenter(reportTemplate.fieldCenter, result);
        result = validateDataSources(reportTemplate.dataSources, result);
        if(!result.trim().isEmpty()){
            throw new ValidationException(new Throwable("Required field(s) "+result+" is(are) invalid"));
        }
    }


    private static String validateTemplate(String template, String result){
        if(template == null || template.trim().isEmpty()) {
            result = (result.equals("")) ? "Template" : result.concat(", Template");
        }
        return result;
    }

    private static String validateLabel(String label, String result){
        if(label == null || label.trim().isEmpty()){
            result = (result.equals("")) ? "Label" : result.concat(", Label");
        }
        return result;
    }

    private static String validateSendingDate(LocalDateTime sendingDate, String result){
        if(sendingDate == null){
            result = (result.equals("")) ? "SendingDate" : result.concat(", SendingDate");
        }
        return result;
    }

    private static String validateFieldCenter(ArrayList<String> fieldCenter, String result) {
        if(fieldCenter == null){
            result = (result.equals("")) ? "FieldCenter" : result.concat(", FieldCenter");
        }
        return result;
    }

    private static String validateDataSources(ArrayList<ReportDataSource> dataSources, String result){
        if(dataSources == null){
            result = (result.equals("")) ? "DataSources" : result.concat(", DataSources");
        }
        return result;
    }

    public static GsonBuilder getResponseGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());

        return builder;
    }
}
