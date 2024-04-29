package de.dhbw.mwulle.jhelp.netbeans.impl;

import de.dhbw.mwulle.jhelp.api.MapId;

import java.util.Objects;

public class SearchResult {

    private final float score;
    private final MapId mapId;

    public SearchResult(float score, MapId mapId) {
        this.score = score;
        this.mapId = mapId;
    }

    public float getScore() {
        return score;
    }

    public MapId getMapId() {
        return mapId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return Float.compare(getScore(), that.getScore()) == 0 && Objects.equals(getMapId(), that.getMapId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScore(), getMapId());
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "score=" + score +
                ", mapId=" + mapId +
                '}';
    }
}
