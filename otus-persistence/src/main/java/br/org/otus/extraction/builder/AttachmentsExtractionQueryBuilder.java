package br.org.otus.extraction.builder;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class AttachmentsExtractionQueryBuilder {

    private ArrayList<Bson> pipeline;

    public AttachmentsExtractionQueryBuilder() {
        this.pipeline = new ArrayList<>();
    }

    public ArrayList<Bson> getAttachmentsRemovedReportQuery(String acronym, Integer version, ArrayList<ObjectId> objectIds){
        return getBsons(acronym, version, objectIds, "$nin:","Removed");
    }

    public ArrayList<Bson> getAttachmentsStoredReportQuery(String acronym, Integer version, ArrayList<ObjectId> objectIds){
        return getBsons(acronym, version, objectIds, "$in:","Stored");
    }

    private ArrayList<Bson> getBsons(String acronym, Integer version, ArrayList<ObjectId> objectIds, String state, String statueSubtitle) {
        pipeline.add(parseQuery("{$match:{\"surveyForm.surveyTemplate.identity.acronym\":"+acronym+",\"surveyForm.version\":"+version+",\"isDiscarded\":false}}"));
        pipeline.add(parseQuery("{\"$addFields\":{\"itemContainer\":\"$surveyForm.surveyTemplate.itemContainer\"}}"));
        pipeline.add(parseQuery("{\"$project\":{\"itemContainer\":1,\"participantData.recruitmentNumber\":1.0,\"fillContainer\":1.0}}"));
        pipeline.add(parseQuery("{$unwind:\"$fillContainer.fillingList\"}"));
        pipeline.add(parseQuery("{$match:{\"fillContainer.fillingList.answer.type\":\"FileUploadQuestion\"}}"));
        pipeline.add(parseQuery("{$unwind:\"$fillContainer.fillingList.answer.value\"}"));
        pipeline.add(parseQuery("{$match:{\"fillContainer.fillingList.answer.value.oid\":{"+state+objectIds+"}}}"));
        pipeline.add(parseQuery("{\"$addFields\":{\"questionFound\":{\"$filter\":{\"input\":\"$itemContainer\",\"as\":\"item\",\"cond\":{\"$eq\":[\"$$item.templateID\",\"$fillContainer.fillingList.questionID\"]}}}}}"));
        pipeline.add(parseQuery("{\"$group\":{\"_id\":{\"recruitmentNumber\":\"$participantData.recruitmentNumber\",\"questionId\":{\"$arrayElemAt\": [ {\"$slice\": [ \"$questionFound.customID\",-1.0] }, 0.0]},\"archiveId\":\"$fillContainer.fillingList.answer.value.oid\",\"archiveName\":\"$fillContainer.fillingList.answer.value.name\",\"status\":\"$status\",\"uploadDate\":\"$fillContainer.fillingList.answer.value.sentDate\"}}}"));
        pipeline.add(parseQuery("{\"$group\":{\"_id\":{},\"attachmentsList\":{\"$push\":{\"recruitmentNumber\":\"$_id.recruitmentNumber\",\"questionId\":\"$_id.questionId\",\"archiveId\":\"$_id.archiveId\",\"status\":"+statueSubtitle+",\"archiveName\":\"$_id.archiveName\",\"uploadDate\":\"$_id.uploadDate\"}}}}"));
        return pipeline;
    }

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }

}
