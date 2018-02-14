package org.ccem.otus.service;

import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ReportDao;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ReportServiceBean implements ReportService {

    @Inject
    private ReportDao reportDao;

    @Override
    public ReportTemplate findReport(long ri) {
        return reportDao.findReport(ri);
    }
}
