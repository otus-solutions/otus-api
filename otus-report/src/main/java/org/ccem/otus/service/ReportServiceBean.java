package org.ccem.otus.service;

import org.ccem.otus.model.DataSourceModel;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ReportDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class ReportServiceBean implements ReportService {

    @Inject
    private ReportDao reportDao;

    @Override
    public ReportTemplate find(long ri) {
        return reportDao.find(ri);
    }
}
