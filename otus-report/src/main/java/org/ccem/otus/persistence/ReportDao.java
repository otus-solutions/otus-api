package org.ccem.otus.persistence;

import org.ccem.otus.model.ReportTemplate;


public interface ReportDao {

   ReportTemplate find(long ri);

}
