package org.ccem.otus.model;

import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.DataSourceAdapter;

import java.util.ArrayList;

public class ReportTemplate {
    private ArrayList<ReportDataSource> dataSources;

    public ArrayList<ReportDataSource> getDataSources(){
        return dataSources;
    }

    public static String serialize(ReportTemplate reportTemplate) {
        return getGsonBuilder().create().toJson(reportTemplate);
    }

    public static ReportTemplate deserialize(String examResultLotJson) {
        return ReportTemplate.getGsonBuilder().create().fromJson(examResultLotJson, ReportTemplate.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
        builder.registerTypeAdapter(ReportDataSource.class, new DataSourceAdapter());
        return builder;
    }
}
