package org.ccem.otus.importation.activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ActivityImportResultDTOTest {
    @InjectMocks
    private ActivityImportResultDTO activityImportResultDTO;

    @Test
    public void setFailImportMethod_and_getFailImportMethod_should_check_in() {
        activityImportResultDTO.setFailImport();
        assertEquals(true, activityImportResultDTO.getFailImport());
    }

    @Test
    public void setRecruitmentNumberValidationResult_method_should_execute() {
        activityImportResultDTO.setRecruitmentNumberValidationResult(Mockito.anyLong(),Mockito.anyBoolean());
    }

    @Test
    public void setCategoryValidationResult_method_should_execute() {
        activityImportResultDTO.setCategoryValidationResult(Mockito.anyString(),Mockito.anyBoolean());
    }

    @Test
    public void setInterviewerValidationResult_method_should_execute() {
        activityImportResultDTO.setInterviewerValidationResult(Mockito.anyString(),Mockito.anyBoolean());
    }

    @Test
    public void setPaperInterviewerValidationResult_method_should_execute() {
        activityImportResultDTO.setPaperInterviewerValidationResult(Mockito.anyString(),Mockito.anyBoolean());
    }

    @Test
    public void setQuestionFillError_method_should_execute() {
        activityImportResultDTO.setQuestionFillError(Mockito.anyString(),Mockito.anyBoolean());
    }
}