package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import de.dhbw.mwulle.jhelp.impl.view.toc.TocItem;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item.ItemNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

import java.util.List;

public class TocItemNode extends ItemNode<TocItem> {

    protected TocItemNode(Children children, TocItem item) {
        super(children, item);
    }

    public static TocItemNode createRootNode(TocView tocView) {
        return new TocItemNode(createChildren(tocView.getItems()), null);
    }

    private static Children createChildren(List<TocItem> tocItems) {
        if (tocItems.isEmpty()) {
            return Children.LEAF;
        }

        return Children.create(new TocItemNodeFactory(tocItems), false);
    }

    private static class TocItemNodeFactory extends ItemNodeFactory<TocItem>{

        protected TocItemNodeFactory(List<TocItem> children) {
            super(children);
        }

        @Override
        protected Node createNodeForKey(TocItem key) {
            return new TocItemNode(createChildren(key.getChildren()), key);
        }
    }
}
