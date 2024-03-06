package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.MapIdEntry;
import de.dhbw.mwulle.jhelp.impl.view.item.Item;
import de.dhbw.mwulle.jhelp.netbeans.impl.ContentManager;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

public class ItemNodeChangeListener<T extends Item<T>> implements LookupListener {

    private final ItemViewComponent<T> itemViewComponent;

    public ItemNodeChangeListener(ItemViewComponent<T> itemViewComponent) {
        this.itemViewComponent = itemViewComponent;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (itemViewComponent.getViewState() != ViewState.READY) {
            return;
        }

        ItemNode<T> itemNode = Utilities.actionsGlobalContext().lookup(itemViewComponent.getItemNodeClass());
        ContentManager contentManager = Utilities.actionsGlobalContext().lookup(ContentManager.class);
        HelpSet helpSet = Utilities.actionsGlobalContext().lookup(HelpSet.class);

        if (itemNode == null || contentManager == null || helpSet == null) {
            // TODO 2024-02-22: Maybe log this?
            return;
        }

        if (itemNode.getItem() == null) {
            // Root node does not have a index item
            // TODO 2024-02-23: Maybe log this, since we set the root as invisible
            return;
        }

        if (itemNode.getItem().getTarget() == null) {
            return;
        }

        MapIdEntry mapIdEntry = helpSet.findMapIdEntry(itemNode.getItem().getTarget());

        if (mapIdEntry == null || mapIdEntry.getUrl() == null) {
            // TODO 2024-02-23: Maybe log this?
            return;
        }

        itemViewComponent.setViewState(ViewState.SELECTING_NODE);
        contentManager.setContent(mapIdEntry);
        itemViewComponent.setViewState(ViewState.READY);
    }
}
