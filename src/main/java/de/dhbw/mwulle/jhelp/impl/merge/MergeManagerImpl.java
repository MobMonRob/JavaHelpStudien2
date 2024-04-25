package de.dhbw.mwulle.jhelp.impl.merge;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.HelpSetMap;
import de.dhbw.mwulle.jhelp.api.MapIdEntry;
import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeManager;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.api.merge.Merger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MergeManagerImpl implements MergeManager {

    private static final MergeType NO_MERGE = MergeType.fromString("javax.help.NoMerge");
    private final Map<MergeType, Merger> mergers = new HashMap<>();

    @Override
    public HelpSet merge(HelpSet first, HelpSet second) {
        MergeData mergeData = MergeData.create(this, first, second);
        HelpSetMap helpSetMap = mergeHelpSetMap(first.getHelpSetMap(), second.getHelpSetMap());
        List<View> newViews = new ArrayList<>();

        // As of the spec, only views, which are present in the main HelpSet get merged
        // Views which are only present in the second HelpSet get ignored
        for (View view : first.getViews()) {
            Optional<View> otherView = second.getViews().stream().filter(v -> view.getType().equals(v.getType())).findFirst();

            // As of the spec, no merge only applies for the entire view, so it is enough if we only check here
            if (!otherView.isPresent() || NO_MERGE.equals(otherView.get().getMergeType())) {
                newViews.add(view);
                continue;
            }

            newViews.add(view.merge(otherView.get(), otherView.get().getMergeType(), mergeData));
        }

        return new HelpSet(first.getTitle(), helpSetMap, newViews);
    }

    private HelpSetMap mergeHelpSetMap(HelpSetMap first, HelpSetMap second) {
        // TODO 2024-03-27: Filter out duplicates
        List<MapIdEntry> newMapIdEntries = new ArrayList<>(first.getMapIdEntries());
        newMapIdEntries.addAll(second.getMapIdEntries());

        return new HelpSetMap(first.getHomeId(), first.getLanguage(), newMapIdEntries);
    }

    @Override
    public void registerMerger(MergeType mergeType, Merger merger) {
        mergers.put(mergeType, merger);
    }

    @Override
    public Merger getMerger(MergeType mergeType) {
        return mergers.get(mergeType);
    }
}
