package org.ccem.otus.model;

import com.google.gson.GsonBuilder;

import org.bson.types.ObjectId;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.DataSourceAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReportTemplate {
	private ObjectId _id;
    private String template;
    private String label;
    private ArrayList<String> fieldCenter; //TODO ALTERAR PARA ARRAY
    private ArrayList<ReportDataSource> dataSources;

    public static String serialize(ReportTemplate reportTemplate) {
        return ReportTemplate.getGsonBuilder().create().toJson(reportTemplate);
    }

    public static ReportTemplate deserialize(String reportTemplateJson) {
        return ReportTemplate.getGsonBuilder().create().fromJson(reportTemplateJson, ReportTemplate.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
        builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
        builder.registerTypeAdapter(ReportDataSource.class, new DataSourceAdapter());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
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
    

}
