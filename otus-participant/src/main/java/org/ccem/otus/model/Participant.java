package org.ccem.otus.model;

public class Participant {

    private Long recruitmentNumber;
    private String name;
    private FieldCenter fieldCenter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        return recruitmentNumber != null ? recruitmentNumber.equals(that.recruitmentNumber) : that.recruitmentNumber == null;

    }

    @Override
    public int hashCode() {
        return recruitmentNumber != null ? recruitmentNumber.hashCode() : 0;
    }
}
