package br.org.otus.extraction.builder;


import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
public class AttachmentsExtractionQueryBuilderTest {

    private static String ACRONYM = "RS";
    private static Integer VERSION = 1;
    private static ArrayList<ObjectId> objectIds = new ArrayList<>();

    @Test
    public void getAttachmentsRemovedReportQuery_should_return_AttachmentsRemovedReportQuery(){
        String removedQuery = "[{\"$match\":{\"surveyForm.surveyTemplate.identity.acronym\":\"RS\",\"surveyForm.version\":1.0,\"isDiscarded\":false}},{\"$project\":{\"participantData.recruitmentNumber\":1.0,\"fillContainer\":1.0,\"surveyForm.surveyTemplate.identity.acronym\":1.0}},{\"$unwind\":\"$fillContainer.fillingList\"},{\"$match\":{\"fillContainer.fillingList.answer.type\":\"FileUploadQuestion\"}},{\"$unwind\":\"$fillContainer.fillingList.answer.value\"},{\"$match\":{\"fillContainer.fillingList.answer.value.oid\":{\"$in\":[]}}},{\"$group\":{\"_id\":{\"recruitmentNumber\":\"$participantData.recruitmentNumber\",\"questionId\":\"$surveyForm.surveyTemplate.identity.acronym\",\"archiveId\":\"$fillContainer.fillingList.answer.value.oid\",\"archiveName\":\"$fillContainer.fillingList.answer.value.name\",\"status\":\"$status\",\"uploadDate\":\"$fillContainer.fillingList.answer.value.sentDate\"}}},{\"$group\":{\"_id\":{},\"attachmentsList\":{\"$push\":{\"recruitmentNumber\":\"$_id.recruitmentNumber\",\"questionId\":\"$_id.questionId\",\"archiveId\":\"$_id.archiveId\",\"status\":\"Stored\",\"archiveName\":\"$_id.archiveName\"}}}}]";
        assertEquals(removedQuery,new GsonBuilder().create().toJson(new AttachmentsExtractionQueryBuilder().getAttachmentsStoredReportQuery(ACRONYM,VERSION,objectIds)));
    }

    @Test
    public void getAttachmentsStoredReportQuery_should_return_AttachmentsStoredReportQuery(){
        String storedQuery = "[{\"$match\":{\"surveyForm.surveyTemplate.identity.acronym\":\"RS\",\"surveyForm.version\":1.0,\"isDiscarded\":false}},{\"$project\":{\"participantData.recruitmentNumber\":1.0,\"fillContainer\":1.0,\"surveyForm.surveyTemplate.identity.acronym\":1.0}},{\"$unwind\":\"$fillContainer.fillingList\"},{\"$match\":{\"fillContainer.fillingList.answer.type\":\"FileUploadQuestion\"}},{\"$unwind\":\"$fillContainer.fillingList.answer.value\"},{\"$match\":{\"fillContainer.fillingList.answer.value.oid\":{\"$nin\":[]}}},{\"$group\":{\"_id\":{\"recruitmentNumber\":\"$participantData.recruitmentNumber\",\"questionId\":\"$surveyForm.surveyTemplate.identity.acronym\",\"archiveId\":\"$fillContainer.fillingList.answer.value.oid\",\"archiveName\":\"$fillContainer.fillingList.answer.value.name\",\"status\":\"$status\",\"uploadDate\":\"$fillContainer.fillingList.answer.value.sentDate\"}}},{\"$group\":{\"_id\":{},\"attachmentsList\":{\"$push\":{\"recruitmentNumber\":\"$_id.recruitmentNumber\",\"questionId\":\"$_id.questionId\",\"archiveId\":\"$_id.archiveId\",\"status\":\"Removed\",\"archiveName\":\"$_id.archiveName\"}}}}]";
        assertEquals(storedQuery,new GsonBuilder().create().toJson(new AttachmentsExtractionQueryBuilder().getAttachmentsRemovedReportQuery(ACRONYM,VERSION,objectIds)));
    }
}
