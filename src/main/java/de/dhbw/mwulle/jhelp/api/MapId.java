package de.dhbw.mwulle.jhelp.api;

import java.util.Objects;

public class MapId {

    private final String id;

    private MapId(String id) {
        this.id = id;
    }

    public static MapId fromString(String id) {
        if (id == null) {
            return null;
        }
        return new MapId(id);
    }

    public String getStringId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapId mapId = (MapId) o;
        return Objects.equals(id, mapId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MapId{" +
                "id='" + id + '\'' +
                '}';
    }
}
