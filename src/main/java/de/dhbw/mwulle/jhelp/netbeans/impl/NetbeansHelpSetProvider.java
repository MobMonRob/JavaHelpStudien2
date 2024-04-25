package de.dhbw.mwulle.jhelp.netbeans.impl;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.HelpSetProvider;
import de.dhbw.mwulle.jhelp.api.merge.MergeManager;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.impl.merge.AppendMerger;
import de.dhbw.mwulle.jhelp.impl.merge.MergeManagerImpl;
import de.dhbw.mwulle.jhelp.impl.merge.SortMerger;
import de.dhbw.mwulle.jhelp.impl.merge.UnitAppendMerger;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = HelpSetProvider.class)
public class NetbeansHelpSetProvider implements HelpSetProvider {

    // TODO 2024-04-25: Get MergeManager from a better place
    private final MergeManager mergeManager;

    public NetbeansHelpSetProvider() {
        mergeManager = new MergeManagerImpl();

        mergeManager.registerMerger(MergeType.fromString("javax.help.AppendMerge"), new AppendMerger());
        mergeManager.registerMerger(MergeType.fromString("javax.help.UniteAppendMerge"), new UnitAppendMerger());
        mergeManager.registerMerger(MergeType.fromString("javax.help.SortMerge"), new SortMerger());
    }

    @Override
    public HelpSet getMasterHelpSet() {
        HelpSet first = null;
        for (HelpSet helpSet : Lookup.getDefault().lookupAll(HelpSet.class)) {
            if (first == null) {
                first = helpSet;
                continue;
            }

            first = mergeManager.merge(first, helpSet);
        }

        return first;
    }
}
