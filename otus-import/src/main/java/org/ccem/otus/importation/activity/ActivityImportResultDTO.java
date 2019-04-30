package org.ccem.otus.importation.activity;

public class ActivityImportResultDTO {

    private boolean failImport = false;
    private RecruitmentNumberValidationResult recruitmentNumberValidationResult;
    private CategoryValidationResult categoryValidationResult;
    private InterviewerValidationResult interviewerValidationResult;
    private PaperInterviewerValidationResult paperInterviewerValidationResult;
    private QuestionFillValidationResult questionFillValidationResult;

    public void setFailImport() {
        this.failImport = true;
    }

    public boolean getFailImport() {
        return this.failImport;
    }

    private class RecruitmentNumberValidationResult {
        private Long recruitmentNumber;
        private boolean isValid;

        private RecruitmentNumberValidationResult(Long recruitmentNumber, boolean isValid) {
            this.recruitmentNumber = recruitmentNumber;
            this.isValid = isValid;
        }
    }

    private class CategoryValidationResult {
        private String category;
        private boolean isValid;

        public CategoryValidationResult(String category, boolean isValid) {
            this.category = category;
            this.isValid = isValid;
        }
    }

    private class InterviewerValidationResult {
        private String email;
        private boolean isValid;

        public InterviewerValidationResult(String email, boolean isValid) {
            this.email = email;
            this.isValid = isValid;
        }
    }

    private class PaperInterviewerValidationResult {
        private String email;
        private boolean isValid;

        public PaperInterviewerValidationResult(String email, boolean isValid) {
            this.email = email;
            this.isValid = isValid;
        }
    }

    private class QuestionFillValidationResult {
        private String questionId;
        private boolean shouldBeFilled;

        public QuestionFillValidationResult(String questionId, boolean shouldBeFilled) {
            this.questionId = questionId;
            this.shouldBeFilled = shouldBeFilled;
        }
    }

    public void setRecruitmentNumberValidationResult(Long recruitmentNumber, boolean isValid){
        this.recruitmentNumberValidationResult = new RecruitmentNumberValidationResult(recruitmentNumber,isValid);
    }

    public void setCategoryValidationResult(String category, boolean isValid){
        this.categoryValidationResult = new CategoryValidationResult(category,isValid);
    }

    public void setInterviewerValidationResult(String email, boolean isValid){
        this.interviewerValidationResult = new InterviewerValidationResult(email,isValid);
    }

    public void setPaperInterviewerValidationResult(String email, boolean isValid) {
        this.paperInterviewerValidationResult = new PaperInterviewerValidationResult(email, isValid);
    }

    public void setQuestionFillError(String questionId, boolean shouldBeFilled){
        this.questionFillValidationResult = new QuestionFillValidationResult(questionId, shouldBeFilled);
    }

}