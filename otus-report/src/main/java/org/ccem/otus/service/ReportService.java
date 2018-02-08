package org.ccem.otus.service;
import org.ccem.otus.model.DataSourceModel;
import org.ccem.otus.model.ReportTemplate;

import java.util.ArrayList;


public interface ReportService {

    ReportTemplate find(long ri);
}
