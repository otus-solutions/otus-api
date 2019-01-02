package org.ccem.otus.model;

import java.util.Objects;

public class DataSourceElement {
    private String value;
    private String extractionValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSourceElement that = (DataSourceElement) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(extractionValue, that.extractionValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, extractionValue);
    }
}
