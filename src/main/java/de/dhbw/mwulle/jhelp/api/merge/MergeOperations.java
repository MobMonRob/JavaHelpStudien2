package de.dhbw.mwulle.jhelp.api.merge;

public interface MergeOperations<T> {

    int compare(T first, T second);

    T appendText(T t, String text);

    boolean basicEquals(T first, T second);
}
