package de.dhbw.mwulle.jhelp.api.search;

import de.dhbw.mwulle.jhelp.api.MapId;

import java.util.Objects;

public class SearchResult {

    private final float score;
    private final MapId mapId;

    private SearchResult(float score, MapId mapId) {
        this.score = score;
        this.mapId = mapId;
    }

    public static SearchResult create(float score, MapId mapId) {
        return new SearchResult(score, mapId);
    }

    public MapId getMapId() {
        return mapId;
    }

    public float getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "score=" + score +
                ", mapId=" + mapId +
                '}';
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
}
