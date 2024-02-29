package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.index;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.netbeans.impl.ContentManager;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

public class IndexChangeListener implements LookupListener {
    @Override
    public void resultChanged(LookupEvent ev) {
        IndexItemNode indexItemNode = Utilities.actionsGlobalContext().lookup(IndexItemNode.class);
        ContentManager contentManager = Utilities.actionsGlobalContext().lookup(ContentManager.class);
        HelpSet helpSet = Utilities.actionsGlobalContext().lookup(HelpSet.class);

        if (indexItemNode == null || contentManager == null || helpSet == null) {
            // TODO 2024-02-22: Maybe log this?
            return;
        }

        if (indexItemNode.getIndexItem() == null) {
            // Root node does not have a index item
            // TODO 2024-02-23: Maybe log this, since we set the root as invisible
            return;
        }

        if (indexItemNode.getIndexItem().getTarget() == null) {
            return;
        }

        MapId mapId = helpSet.findMapId(indexItemNode.getIndexItem().getTarget());

        if (mapId == null || mapId.getUrl() == null) {
            // TODO 2024-02-23: Maybe log this?
            return;
        }

        contentManager.setContent(mapId.getUrl());
    }
}
