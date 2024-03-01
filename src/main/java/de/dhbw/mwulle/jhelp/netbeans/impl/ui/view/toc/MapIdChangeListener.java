package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocItem;
import java.beans.PropertyVetoException;
import org.openide.nodes.Node;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

public class MapIdChangeListener implements LookupListener {

    private final TocViewComponent tocViewComponent;

    public MapIdChangeListener(TocViewComponent tocViewComponent) {
        this.tocViewComponent = tocViewComponent;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (tocViewComponent.getViewState() != ViewState.READY) {
            return;
        }

        MapId mapId = Utilities.actionsGlobalContext().lookup(MapId.class);

        if (mapId == null) {
            // TODO 2024-02-22: Maybe log this?
            return;
        }

        TocItem tocItem = tocViewComponent.getView().findTocItem(mapId);

        if (tocItem == null) {
            // TODO 2024-02-23: Maybe log this?
            try {
                tocViewComponent.getExplorerManager().setSelectedNodes(new Node[0]);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        TocItemNode tocItemNode = findNode(tocItem);

        if (tocItemNode == null) {
            // TODO 2024-02-23: Maybe log this?
            try {
                tocViewComponent.getExplorerManager().setSelectedNodes(new Node[0]);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        tocViewComponent.setViewState(ViewState.CHANGING_NODE);
        try {
            tocViewComponent.getExplorerManager().setSelectedNodes(new TocItemNode[]{tocItemNode});
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } finally {
            tocViewComponent.setViewState(ViewState.READY);
        }
    }

    private TocItemNode findNode(TocItem tocItem) {
        return findNode(tocViewComponent.getExplorerManager().getRootContext().getChildren().getNodes(), tocItem);
    }

    private TocItemNode findNode(Node[] nodes, TocItem target) {
        for (Node node : nodes) {
            if (!(node instanceof TocItemNode)) {
                continue;
            }

            TocItemNode tocItemNode = (TocItemNode) node;

            if (tocItemNode.getTocItem() == target) {
                return tocItemNode;
            }

            TocItemNode other = findNode(tocItemNode.getChildren().getNodes(), target);
            if (other != null) {
                return other;
            }
        }

        return null;
    }
}
