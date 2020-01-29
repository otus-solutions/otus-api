package br.org.otus.examUploader.utils;

import java.util.ArrayList;

public class ResponseAliquot {

  public String aliquot;
  public String message;
  public ArrayList<String> possibleExams;
  public String receivedExam;

  public void setAliquot(String aliquot) {
    this.aliquot = aliquot;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setPossibleExams(ArrayList<String> possibleExams) {
    this.possibleExams = possibleExams;
  }

  public void setReceivedExam(String receivedExam) {
    this.receivedExam = receivedExam;
  }
}
