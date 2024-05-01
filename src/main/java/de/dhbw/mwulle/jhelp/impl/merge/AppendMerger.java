package de.dhbw.mwulle.jhelp.impl.merge;

import de.dhbw.mwulle.jhelp.api.merge.MergeAble;
import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeOperations;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.api.merge.Merger;

import java.util.ArrayList;
import java.util.List;

public class AppendMerger implements Merger {

    @Override
    public <T extends MergeAble<T>> List<T> mergeList(MergeData mergeData, MergeType mergeType, List<T> first, List<T> second, MergeOperations<T> mergeOperations) {
        List<T> merged = new ArrayList<>(first);
        merged.addAll(second);

        return merged;
    }
}
