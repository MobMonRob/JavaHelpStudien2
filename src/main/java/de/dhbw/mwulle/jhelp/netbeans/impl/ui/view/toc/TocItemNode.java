package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import de.dhbw.mwulle.jhelp.impl.view.toc.TocItem;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

import java.util.List;

public class TocItemNode extends AbstractNode {

    private final TocItem tocItem;

    public TocItemNode(Children children, TocItem tocItem) {
        super(children);
        this.tocItem = tocItem;
        if (tocItem != null) {
            setDisplayName(tocItem.getText());
        }
    }

    public static TocItemNode createRootNode(TocView tocView) {
        return new TocItemNode(createChildren(tocView.getTocItems()), null);
    }

    private static Children createChildren(List<TocItem> tocItems) {
        if (tocItems.isEmpty()) {
            return Children.LEAF;
        }

        return Children.create(new TocItemNodeFactory(tocItems), false);
    }

    public TocItem getTocItem() {
        return tocItem;
    }

    private static class TocItemNodeFactory extends ChildFactory<TocItem> {

        private final List<TocItem> children;

        public TocItemNodeFactory(List<TocItem> children) {
            this.children = children;
        }

        @Override
        protected Node createNodeForKey(TocItem key) {
            return new TocItemNode(createChildren(key.getChildren()), key);
        }

        @Override
        protected boolean createKeys(List<TocItem> toPopulate) {
            toPopulate.addAll(children);
            return true;
        }
    }
}
