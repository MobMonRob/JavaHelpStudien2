package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.index;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexItem;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item.ItemNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

import java.util.List;

public class IndexItemNode extends ItemNode<IndexItem> {

    private IndexItemNode(Children children, IndexItemNodeFactory itemNodeFactory, Lookup.Provider provider, View view, IndexItem item) {
        super(children, itemNodeFactory, provider, view, item);
    }

    public static IndexItemNode createRootNode(Lookup.Provider provider, IndexView indexView) {
        IndexItemNodeFactory indexItemNodeFactory = createFactory(provider, indexView, indexView.getItems());
        return new IndexItemNode(createChildren(indexItemNodeFactory), indexItemNodeFactory, provider, indexView, null);
    }

    private static IndexItemNodeFactory createFactory(Lookup.Provider provider, View view, List<IndexItem> indexItems) {
        if (indexItems.isEmpty()) {
            return null;
        }

        return new IndexItemNodeFactory(provider, view, indexItems);
    }

    private static Children createChildren(IndexItemNodeFactory indexItemNodeFactory) {
        if (indexItemNodeFactory == null) {
            return Children.LEAF;
        }

        return Children.create(indexItemNodeFactory, false);
    }

    private static class IndexItemNodeFactory extends ItemNodeFactory<IndexItem> {

        protected IndexItemNodeFactory(Lookup.Provider provider, View view, List<IndexItem> children) {
            super(provider, view, children);
        }

        @Override
        protected Node createNodeForKey(IndexItem key) {
            IndexItemNodeFactory indexItemNodeFactory = createFactory(getProvider(), getView(), key.getChildren());
            return new IndexItemNode(createChildren(indexItemNodeFactory), indexItemNodeFactory, getProvider(), getView(), key);
        }
    }
}
