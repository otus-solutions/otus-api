package org.ccem.otus.enums;

import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.ParticipantDataSource;
import org.ccem.otus.persistence.ParticipantDataSourceDao;
import org.ccem.otus.persistence.ReportDao;

public enum DataSourceMapping {
    PARTICIPANT_DATASOURCE(ParticipantDataSource.class, ParticipantDataSourceDao.class, "Participant");

    private Class<? extends DataSource> dataSource;
    private Class<? extends ReportDao> dao;
    private String Key;

    DataSourceMapping(Class<? extends DataSource> dataSource, Class<? extends ReportDao> dao, String Key) {
        this.dataSource = dataSource;
        this.dao = dao;
        this.Key = Key;
    }

    public Class<? extends DataSource> getItemClass() {
        return this.dataSource;
    }

    public Class<? extends ReportDao> getDao() {
        return this.dao;
    }

    public String getDataSourceKey() {
        return this.Key;
    }

    public static DataSourceMapping getEnumByObjectType(String objectType) {
        DataSourceMapping aux = null;
        DataSourceMapping[] var2 = values();

        for (DataSourceMapping item : var2) {
            if (item.getDataSourceKey().equals(objectType)) {
                aux = item;
            }
        }

        if (aux == null) {
            throw new RuntimeException("Error: " + objectType + " was not found at DataSourceMapping.");
        } else {
            return aux;
        }
    }
}
