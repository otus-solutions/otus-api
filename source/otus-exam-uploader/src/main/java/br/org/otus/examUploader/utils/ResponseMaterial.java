package br.org.otus.examUploader.utils;

import java.util.ArrayList;

public class ResponseMaterial {

  public String material;
  public String message;
  public ArrayList<String> possibleExams;
  public String receivedExam;

  public void setMaterial(String material) {
    this.material = material;
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
