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
        return new TocItemNode(Children.create(new TocItemNodeFactory(tocView.getTocItems()), false), null);
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
            return new TocItemNode(Children.create(new TocItemNodeFactory(key.getChildren()), false), key);
        }

        @Override
        protected boolean createKeys(List<TocItem> toPopulate) {
            toPopulate.addAll(children);
            return true;
        }
    }
}
