package br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.model;

import org.bson.types.ObjectId;

import java.util.List;

public class MaterialTrackingResultExtraction {
  String materialCode;
  String origin;
  String destination;
  boolean receipted;
  String receiveResponsible;
  String sendingDate;
  String receiptDate;
  String lotCode;
  List<ObjectId> receiptedMetadata;
  String otherMetadata;

  public String getMaterialCode() {
    return materialCode;
  }

  public String getOrigin() {
    return origin;
  }

  public String getDestination() {
    return destination;
  }

  public boolean getReceipted() {
    return receipted;
  }

  public String getReceiveResponsible() {
    return receiveResponsible;
  }

  public String getSendingDate() {
    return sendingDate;
  }

  public String getReceiptDate() {
    return receiptDate;
  }

  public String getLotCode() {
    return lotCode;
  }

  public List<ObjectId> getReceiptedMetadata() { return receiptedMetadata; }

  public String getOtherMetadata() { return otherMetadata; }

}
