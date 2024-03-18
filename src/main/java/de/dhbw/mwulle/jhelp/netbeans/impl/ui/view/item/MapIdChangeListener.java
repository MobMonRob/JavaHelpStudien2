package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item;

import de.dhbw.mwulle.jhelp.api.MapIdEntry;
import de.dhbw.mwulle.jhelp.impl.view.item.Item;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import java.beans.PropertyVetoException;

public class MapIdChangeListener<T extends Item<T>> implements LookupListener {

    private final Lookup.Provider provider;
    private final ItemViewComponent<T> itemViewComponent;

    public MapIdChangeListener(Lookup.Provider provider, ItemViewComponent<T> itemViewComponent) {
        this.provider = provider;
        this.itemViewComponent = itemViewComponent;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (itemViewComponent.getViewState() != ViewState.READY) {
            return;
        }

        MapIdEntry mapIdEntry = provider.getLookup().lookup(MapIdEntry.class);

        if (mapIdEntry == null) {
            // TODO 2024-02-22: Maybe log this?
            return;
        }

        T item = itemViewComponent.getView().findItem(mapIdEntry.getId());

        if (item == null) {
            // TODO 2024-02-23: Maybe log this?
            try {
                itemViewComponent.getExplorerManager().setSelectedNodes(new Node[0]);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        itemViewComponent.setViewState(ViewState.CHANGING_NODE);
        itemViewComponent.expandTo(item);

        ItemNode<T> itemNode = findNode(item);

        if (itemNode == null) {
            // TODO 2024-02-23: Maybe log this?
            try {
                itemViewComponent.getExplorerManager().setSelectedNodes(new Node[0]);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            } finally {
                itemViewComponent.setViewState(ViewState.READY);
            }
            return;
        }

        try {
            itemViewComponent.getExplorerManager().setSelectedNodes(new ItemNode[]{itemNode});
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } finally {
            itemViewComponent.setViewState(ViewState.READY);
        }
    }

    private ItemNode<T> findNode(T item) {
        return findNode(itemViewComponent.getExplorerManager().getRootContext().getChildren().getNodes(), item);
    }

    private ItemNode<T> findNode(Node[] nodes, T target) {
        for (Node node : nodes) {
            if (!(node instanceof ItemNode)) {
                continue;
            }

            ItemNode<T> itemNode = (ItemNode<T>) node;

            if (itemNode.getItem() == target) {
                return itemNode;
            }

            ItemNode<T> other = findNode(itemNode.getChildren().getNodes(), target);
            if (other != null) {
                return other;
            }
        }

        return null;
    }
}
