package de.dhbw.mwulle.jhelp.api.merge;

import java.util.List;

public interface Merger {

    <T extends MergeAble<T>> List<T> mergeList(MergeData mergeData, MergeType mergeType, List<T> first, List<T> second, MergeOperations<T> mergeOperations);
}
