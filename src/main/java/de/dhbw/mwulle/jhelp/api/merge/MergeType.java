package de.dhbw.mwulle.jhelp.api.merge;

import java.util.Objects;

public class MergeType {

    private final String id;

    private MergeType(String id) {
        this.id = id;
    }

    public static MergeType fromString(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return new MergeType(id);
    }

    public String getStringID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergeType mergeType = (MergeType) o;
        return Objects.equals(id, mergeType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MergeType{" +
                "id='" + id + '\'' +
                '}';
    }
}
