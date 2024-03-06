package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocItem;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item.ItemNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

import java.util.List;

public class TocItemNode extends ItemNode<TocItem> {

    protected TocItemNode(Children children, Lookup.Provider provider, View view, TocItem item) {
        super(children, provider, view, item);
    }

    public static TocItemNode createRootNode(Lookup.Provider provider, TocView tocView) {
        return new TocItemNode(createChildren(provider, tocView, tocView.getItems()), provider, tocView, null);
    }

    private static Children createChildren(Lookup.Provider provider, View view, List<TocItem> tocItems) {
        if (tocItems.isEmpty()) {
            return Children.LEAF;
        }

        return Children.create(new TocItemNodeFactory(provider, view, tocItems), false);
    }

    @Override
    public TocView getView() {
        // If this is not a TocView, then something terrible went wrong
        return (TocView) super.getView();
    }

    @Override
    protected String getDefaultClosedIcon() {
        if (getView().getBaseTocItemInfo() == null) {
            return null;
        }
        return getView().getBaseTocItemInfo().getCategoryClosedImage();
    }

    @Override
    protected String getDefaultOpenIcon() {
        if (getView().getBaseTocItemInfo() == null) {
            return null;
        }
        return getView().getBaseTocItemInfo().getCategoryOpenImage();
    }

    @Override
    protected String getDefaultLeafIcon() {
        if (getView().getBaseTocItemInfo() == null) {
            return null;
        }
        return getView().getBaseTocItemInfo().getTopicImage();
    }

    @Override
    protected String getIcon() {
        return getItem().getImage();
    }

    private static class TocItemNodeFactory extends ItemNodeFactory<TocItem> {

        protected TocItemNodeFactory(Lookup.Provider provider, View view, List<TocItem> children) {
            super(provider, view, children);
        }

        @Override
        protected Node createNodeForKey(TocItem key) {
            return new TocItemNode(createChildren(getProvider(), getView(), key.getChildren()), getProvider(), getView(), key);
        }
    }
}
