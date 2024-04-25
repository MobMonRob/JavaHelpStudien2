package de.dhbw.mwulle.jhelp.impl.merge;

import de.dhbw.mwulle.jhelp.api.merge.MergeAble;
import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeOperations;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.api.merge.Merger;

import java.util.ArrayList;
import java.util.List;

public class UnitAppendMerger implements Merger {

    @Override
    public <T extends MergeAble<T>> List<T> mergeList(MergeData mergeData, MergeType mergeType, List<T> first, List<T> secondFinal, MergeOperations<T> mergeOperations) {
        List<T> merges = new ArrayList<>(first);
        List<T> second = new ArrayList<>(secondFinal);

        dance:
        for (int i = 0; i < first.size(); i++) {
            T item = first.get(i);
            for (int o = 0; o < second.size(); o++) {
                T other = second.get(o);
                if (mergeOperations.basicEquals(item, other)) {
                    merges.set(i, item.merge(other, mergeType, mergeData));
                    second.remove(other);
                    continue dance;
                } else if (mergeOperations.compare(item, other) == 0) {
                    merges.set(i, mergeOperations.appendText(item, " (" + mergeData.getFirstHelpSet().getTitle() + ")"));
                    merges.add(mergeOperations.appendText(other, " (" + mergeData.getSecondHelpSet().getTitle() + ")"));
                    second.remove(other);
                    continue dance;
                }
            }
        }

        merges.addAll(second);

        return merges;
    }
}
