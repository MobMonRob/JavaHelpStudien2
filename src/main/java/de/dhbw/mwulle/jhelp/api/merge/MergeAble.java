package de.dhbw.mwulle.jhelp.api.merge;

public interface MergeAble <T extends MergeAble<T>> {

    MergeType getMergeType();

    T merge(T other, MergeType mergeType, MergeData mergeData);
}
