package org.ccem.otus.enums;

import org.ccem.otus.model.DataSource;
import org.ccem.otus.model.ParticipantDataSource;

public enum DataSourceMapping {
    PARTICIPANT_DATASOURCE(ParticipantDataSource.class, "Participant");

    private Class<? extends DataSource> dataSource;
    private String Key;

    DataSourceMapping(Class<? extends DataSource> dataSource, String Key) {
        this.dataSource = dataSource;
        this.Key = Key;
    }

    public Class<? extends DataSource> getItemClass() {
        return this.dataSource;
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
