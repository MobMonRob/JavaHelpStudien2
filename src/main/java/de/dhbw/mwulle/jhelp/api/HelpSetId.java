package de.dhbw.mwulle.jhelp.api;

import java.util.Objects;

public class HelpSetId {

    private final String id;

    private HelpSetId(String id) {
        this.id = id;
    }

    public static HelpSetId fromString(String id) {
        if (id == null) {
            return null;
        }
        return new HelpSetId(id);
    }

    public String getStringId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelpSetId helpSetId = (HelpSetId) o;
        return Objects.equals(id, helpSetId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "HelpSetId{" +
                "id='" + id + '\'' +
                '}';
    }
}
