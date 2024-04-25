package de.dhbw.mwulle.jhelp.impl.merge;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.merge.MergeAble;
import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeOperations;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.api.merge.Merger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SortMerger implements Merger {

    @Override
    public <T extends MergeAble<T>> List<T> mergeList(MergeData mergeData, MergeType mergeType, List<T> first, List<T> second, MergeOperations<T> mergeOperations) {
        List<Holder<T>> sorted = new ArrayList<>(first.stream().map(i -> new Holder<>(mergeData.getFirstHelpSet(), i)).collect(Collectors.toList()));
        sorted.addAll(second.stream().map(i -> new Holder<>(mergeData.getSecondHelpSet(), i)).collect(Collectors.toList()));
        sorted.sort((a, b) -> mergeOperations.compare(a.getItem(), b.getItem()));

        List<T> merged = new ArrayList<>();

        for (int i = 0; i < sorted.size(); i++) {
            if ((i + 1) == sorted.size()) {
                merged.add(sorted.get(i).getItem());
                break;
            }

            Holder<T> firstItem = sorted.get(i);
            Holder<T> secondItem = sorted.get(i + 1);

            if (firstItem.getHelpSet() == secondItem.getHelpSet()) {
                merged.add(firstItem.getItem());
                continue;
            }

            if (mergeOperations.basicEquals(firstItem.getItem(), secondItem.getItem())) {
                if (secondItem.getHelpSet() == mergeData.getFirstHelpSet()) {
                    firstItem = secondItem;
                    secondItem = sorted.get(i);
                }

                merged.add(firstItem.getItem().merge(secondItem.getItem(), mergeType, mergeData));
                i++;
                continue;
            } else if (mergeOperations.compare(firstItem.getItem(), secondItem.getItem()) == 0) {
                if (secondItem.getHelpSet() == mergeData.getFirstHelpSet()) {
                    firstItem = secondItem;
                    secondItem = sorted.get(i);
                }
                merged.add(mergeOperations.appendText(firstItem.getItem(), " (" + firstItem.getHelpSet().getTitle() + ")"));
                merged.add(mergeOperations.appendText(secondItem.getItem(), " (" + secondItem.getHelpSet().getTitle() + ")"));
                i++;
                continue;
            }

            merged.add(firstItem.getItem());
        }

        return merged;
    }

    private static class Holder<T> {
        private final HelpSet helpSet;
        private final T item;

        private Holder(HelpSet helpSet, T item) {
            this.helpSet = helpSet;
            this.item = item;
        }

        public HelpSet getHelpSet() {
            return helpSet;
        }

        public T getItem() {
            return item;
        }
    }
}
