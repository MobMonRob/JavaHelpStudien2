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

    protected IndexItemNode(Children children, Lookup.Provider provider, View view, IndexItem item) {
        super(children, provider, view, item);
    }

    public static IndexItemNode createRootNode(Lookup.Provider provider, IndexView indexView) {
        return new IndexItemNode(createChildren(provider, indexView, indexView.getItems()), provider, indexView, null);
    }

    private static Children createChildren(Lookup.Provider provider, View view, List<IndexItem> indexItems) {
        if (indexItems.isEmpty()) {
            return Children.LEAF;
        }

        return Children.create(new IndexItemNodeFactory(provider, view, indexItems), false);
    }

    private static class IndexItemNodeFactory extends ItemNodeFactory<IndexItem> {

        protected IndexItemNodeFactory(Lookup.Provider provider, View view, List<IndexItem> children) {
            super(provider, view, children);
        }

        @Override
        protected Node createNodeForKey(IndexItem key) {
            return new IndexItemNode(createChildren(getProvider(), getView(), key.getChildren()), getProvider(), getView(), key);
        }
    }
}
