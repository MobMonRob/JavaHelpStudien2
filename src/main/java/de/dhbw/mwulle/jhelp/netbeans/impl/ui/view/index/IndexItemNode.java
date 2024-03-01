package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.index;

import de.dhbw.mwulle.jhelp.impl.view.index.IndexItem;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item.ItemNode;
import java.util.List;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

public class IndexItemNode extends ItemNode<IndexItem> {

    protected IndexItemNode(Children children, IndexItem item) {
        super(children, item);
    }

    public static IndexItemNode createRootNode(IndexView indexView) {
        return new IndexItemNode(createChildren(indexView.getItems()), null);
    }

    private static Children createChildren(List<IndexItem> indexItems) {
        if (indexItems.isEmpty()) {
            return Children.LEAF;
        }

        return Children.create(new IndexItemNodeFactory(indexItems), false);
    }

    private static class IndexItemNodeFactory extends ItemNodeFactory<IndexItem> {

        protected IndexItemNodeFactory(List<IndexItem> children) {
            super(children);
        }

        @Override
        protected Node createNodeForKey(IndexItem key) {
            return new IndexItemNode(createChildren(key.getChildren()), key);
        }
    }
}
