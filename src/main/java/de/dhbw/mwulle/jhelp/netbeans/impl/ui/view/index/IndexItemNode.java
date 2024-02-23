package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.index;

import de.dhbw.mwulle.jhelp.impl.view.index.IndexItem;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

import java.util.List;

public class IndexItemNode extends AbstractNode {

    private final IndexItem indexItem;

    public IndexItemNode(Children children, IndexItem indexItem) {
        super(children);
        this.indexItem = indexItem;
        if (indexItem != null) {
            setDisplayName(indexItem.getText());
        }
    }

    public static IndexItemNode createRootNode(IndexView indexView) {
        return new IndexItemNode(createChildren(indexView.getIndexItems()), null);
    }

    private static Children createChildren(List<IndexItem> indexItems) {
        if (indexItems.isEmpty()) {
            return Children.LEAF;
        }

        return Children.create(new IndexItemNodeFactory(indexItems), false);
    }

    public IndexItem getIndexItem() {
        return indexItem;
    }

    private static class IndexItemNodeFactory extends ChildFactory<IndexItem> {

        private final List<IndexItem> children;

        public IndexItemNodeFactory(List<IndexItem> children) {
            this.children = children;
        }

        @Override
        protected Node createNodeForKey(IndexItem key) {
            return new IndexItemNode(createChildren(key.getChildren()), key);
        }

        @Override
        protected boolean createKeys(List<IndexItem> toPopulate) {
            toPopulate.addAll(children);
            return true;
        }
    }
}
