package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.index;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexItem;
import java.beans.PropertyVetoException;
import org.openide.nodes.Node;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

public class MapIdChangeListener implements LookupListener {

    private final IndexViewComponent indexViewComponent;

    public MapIdChangeListener(IndexViewComponent indexViewComponent) {
        this.indexViewComponent = indexViewComponent;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (indexViewComponent.getViewState() != ViewState.READY) {
            return;
        }

        MapId mapId = Utilities.actionsGlobalContext().lookup(MapId.class);

        if (mapId == null) {
            // TODO 2024-02-22: Maybe log this?
            return;
        }

        IndexItem indexItem = indexViewComponent.getView().findIndexIdem(mapId);

        if (indexItem == null) {
            // TODO 2024-02-23: Maybe log this?
            try {
                indexViewComponent.getExplorerManager().setSelectedNodes(new Node[0]);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        IndexItemNode indexItemNode = findNode(indexItem);

        if (indexItemNode == null) {
            // TODO 2024-02-23: Maybe log this?
            try {
                indexViewComponent.getExplorerManager().setSelectedNodes(new Node[0]);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        indexViewComponent.setViewState(ViewState.CHANGING_NODE);
        try {
            indexViewComponent.getExplorerManager().setSelectedNodes(new IndexItemNode[]{indexItemNode});
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } finally {
            indexViewComponent.setViewState(ViewState.READY);
        }
    }

    private IndexItemNode findNode(IndexItem indexItem) {
        return findNode(indexViewComponent.getExplorerManager().getRootContext().getChildren().getNodes(), indexItem);
    }

    private IndexItemNode findNode(Node[] nodes, IndexItem target) {
        for (Node node : nodes) {
            if (!(node instanceof IndexItemNode)) {
                continue;
            }

            IndexItemNode indexItemNode = (IndexItemNode) node;

            if (indexItemNode.getIndexItem() == target) {
                return indexItemNode;
            }

            IndexItemNode other = findNode(indexItemNode.getChildren().getNodes(), target);
            if (other != null) {
                return other;
            }
        }

        return null;
    }
}
