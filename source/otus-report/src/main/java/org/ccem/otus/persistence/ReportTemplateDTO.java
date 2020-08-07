package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.model.ReportTemplate;

public class ReportTemplateDTO {
  private ObjectId _id;
  private String label;
  private Boolean isInApp;

  public ReportTemplateDTO(ReportTemplate reportTemplate) {
    this._id = reportTemplate.getId();
    this.label = reportTemplate.getLabel();
    this.isInApp = reportTemplate.getIsInApp();
  }
}
