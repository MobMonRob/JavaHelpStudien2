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
        return new IndexItemNode(Children.create(new TocItemNodeFactory(indexView.getIndexItems()), false), null);
    }

    public IndexItem getIndexItem() {
        return indexItem;
    }

    private static class TocItemNodeFactory extends ChildFactory<IndexItem> {

        private final List<IndexItem> children;

        public TocItemNodeFactory(List<IndexItem> children) {
            this.children = children;
        }

        @Override
        protected Node createNodeForKey(IndexItem key) {
            return new IndexItemNode(Children.create(new TocItemNodeFactory(key.getChildren()), false), key);
        }

        @Override
        protected boolean createKeys(List<IndexItem> toPopulate) {
            toPopulate.addAll(children);
            return true;
        }
    }
}
