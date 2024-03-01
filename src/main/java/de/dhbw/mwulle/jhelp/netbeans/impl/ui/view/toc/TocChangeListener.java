package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.netbeans.impl.ContentManager;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

public class TocChangeListener implements LookupListener {

    private final TocViewComponent tocViewComponent;

    public TocChangeListener(TocViewComponent tocViewComponent) {
        this.tocViewComponent = tocViewComponent;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (tocViewComponent.getViewState() != ViewState.READY) {
            return;
        }
        TocItemNode tocItemNode = Utilities.actionsGlobalContext().lookup(TocItemNode.class);
        ContentManager contentManager = Utilities.actionsGlobalContext().lookup(ContentManager.class);
        HelpSet helpSet = Utilities.actionsGlobalContext().lookup(HelpSet.class);

        if (tocItemNode == null || contentManager == null || helpSet == null) {
            // TODO 2024-02-22: Maybe log this?
            return;
        }

        if (tocItemNode.getTocItem() == null) {
            // Root node does not have a toc item
            // TODO 2024-02-23: Maybe log this, since we set the root as invisible
            return;
        }

        if (tocItemNode.getTocItem().getTarget() == null) {
            return;
        }

        MapId mapId = helpSet.findMapId(tocItemNode.getTocItem().getTarget());

        if (mapId == null) {
            // TODO 2024-02-23: Maybe log this?
            return;
        }

        tocViewComponent.setViewState(ViewState.SELECTING_NODE);
        contentManager.setContent(mapId);
        tocViewComponent.setViewState(ViewState.READY);
    }
}
