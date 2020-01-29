package br.org.otus.attachments;

import org.apache.commons.csv.CSVFormat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AttachmentsReport.class, CSVFormat.class})
public class AttachmentsReportTest {

  private static String ARCHIVE_ID = "asfgdfghrhsrhsrfbhsfd";
  private static String ARCHIVE_NAME = "teste";
  private static String QUESTION_ID = "ACTDC";
  private static Integer RECRUITMENT_NUMBER = 2342345;
  private static String STATUS = "Stored";
  private static String JSON = "{\"_id\":{},\"attachmentsList\":[{\"recruitmentNumber\":2342345,\"questionId\":\"ACTDC\",\"archiveId\":\"asfgdfghrhsrhsrfbhsfd\",\"status\":\"Stored\",\"archiveName\":\"teste\"}]}";

  @InjectMocks
  AttachmentsReport attachmentsReport;

  @Test
  public void deserialize_should_return_valid_AttachmentsReport() {
    attachmentsReport = AttachmentsReport.deserialize(JSON);
    assertEquals(ARCHIVE_ID, attachmentsReport.getAttachmentsList().get(0).getArchiveId());
    assertEquals(ARCHIVE_NAME, attachmentsReport.getAttachmentsList().get(0).getArchiveName());
    assertEquals(QUESTION_ID, attachmentsReport.getAttachmentsList().get(0).getQuestionId());
    assertEquals(STATUS, attachmentsReport.getAttachmentsList().get(0).getStatus());
    assertEquals(RECRUITMENT_NUMBER, attachmentsReport.getAttachmentsList().get(0).getRecruitmentNumber());
  }

  @Test
  public void concat_should_increment_list_of_Attachment() {
    attachmentsReport = AttachmentsReport.deserialize(JSON);
    AttachmentsReport attachmentsReport2 = AttachmentsReport.deserialize(JSON);
    attachmentsReport.concat(attachmentsReport2.getAttachmentsList());
    assertEquals(2, attachmentsReport.getAttachmentsList().size());
  }

  @Test
  public void getCsv_should_return_byteArray() {
    attachmentsReport = AttachmentsReport.deserialize(JSON);
    Assert.assertTrue(attachmentsReport.getCsv() instanceof byte[]);
  }
}
