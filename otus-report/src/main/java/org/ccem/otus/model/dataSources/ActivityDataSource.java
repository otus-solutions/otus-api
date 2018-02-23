package org.ccem.otus.model.dataSources;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityDataSource extends ReportDataSource<ActivityDataSourceResult> {

    private ActivityDataSourceFilters filters;
    private ArrayList<ActivityDataSourceResult> result = new ArrayList<>();

    @Override
    public void addResult(ActivityDataSourceResult result) {
        this.result.add(result);
    }

    @Override
    public ArrayList<Document> builtQuery(Long recruitmentNumber) {
        ArrayList<Document> query = new ArrayList<>();
        buildMachStage(recruitmentNumber, query);
        buildProjectionStage(query);
        appendStatusHistoryFilter(query);
        return query;
    }

    private void buildMachStage(Long recruitmentNumber, ArrayList<Document> query) {
        Document filters = new Document("participantData.recruitmentNumber", recruitmentNumber)
                .append("surveyForm.surveyTemplate.identity.acronym", this.filters.getAcronym());

        if(this.filters.getCategory() != null){
            appendCategoryFilter(filters, this.filters.getCategory());
        }

        Document matchStage = new Document("$match",filters);
        query.add(matchStage);
    }

    private void buildProjectionStage(ArrayList<Document> query) {
        Document projectionFields = new Document("_id",-1);
        if (this.filters.getStatusHistory() != null) {
            Document statusHistoryProjectionFilter = new Document("$filter",new Document("input","$statusHistory").append("as","statusHistory").append("cond",new Document("$eq",Arrays.asList("$$statusHistory.name",this.filters.getStatusHistory().getName()))));
            projectionFields.append("statusHistory", statusHistoryProjectionFilter);
        }
        Document projectStage = new Document("$project", projectionFields);
        query.add(projectStage);
    }

    private void appendStatusHistoryFilter(ArrayList<Document> query) {
        if (this.filters.getStatusHistory() != null) {
            Document statusHistoryProjection = new Document("$project",new Document("statusHistory",new Document("$slice", Arrays.asList("$statusHistory", this.filters.getStatusHistory().getPosition()))));
            query.add(statusHistoryProjection);
            Document statusHistoryMatchStage = new Document("$match",new Document("statusHistory.name", this.filters.getStatusHistory().getName()));
            query.add(statusHistoryMatchStage);
        }
    }

    private void appendCategoryFilter(Document matchStage, String category) {
        matchStage.append("category.name",category);
    }

    public ArrayList<ActivityDataSourceResult> getResult() {
        return result;
    }
}
