package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocItem;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item.ItemNode;
import org.openide.cookies.InstanceCookie;
import org.openide.loaders.InstanceDataObject;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

import java.util.List;

public class TocItemNode extends ItemNode<TocItem> {

    private TocItemNode(Children children, TocItemNodeFactory tocItemNodeFactory, Lookup.Provider provider, View view, TocItem item) {
        super(children, tocItemNodeFactory, provider, view, item);
    }

    public static TocItemNode createRootNode(Lookup.Provider provider, TocView tocView) {
        TocItemNodeFactory tocItemNodeFactory = createFactory(provider, tocView, tocView.getItems());
        return new TocItemNode(createChildren(tocItemNodeFactory), tocItemNodeFactory, provider, tocView, null);
    }

    private static TocItemNodeFactory createFactory(Lookup.Provider provider, View view, List<TocItem> tocItems) {
        if (tocItems.isEmpty()) {
            return null;
        }
        return new TocItemNodeFactory(provider, view, tocItems);
    }

    private static Children createChildren(TocItemNodeFactory tocItemNodeFactory) {
        if (tocItemNodeFactory == null) {
            return Children.LEAF;
        }

        return Children.create(tocItemNodeFactory, false);
    }

    @Override
    public TocView getView() {
        // If this is not a TocView, then something terrible went wrong
        return (TocView) super.getView();
    }

    @Override
    protected MapId getDefaultClosedIcon() {
        if (getView().getBaseTocItemInfo() == null) {
            return null;
        }
        return getView().getBaseTocItemInfo().getCategoryClosedImage();
    }

    @Override
    protected MapId getDefaultOpenIcon() {
        if (getView().getBaseTocItemInfo() == null) {
            return null;
        }
        return getView().getBaseTocItemInfo().getCategoryOpenImage();
    }

    @Override
    protected MapId getDefaultLeafIcon() {
        if (getView().getBaseTocItemInfo() == null) {
            return null;
        }
        return getView().getBaseTocItemInfo().getTopicImage();
    }

    @Override
    protected MapId getIcon() {
        return getItem().getImage();
    }

    private static class TocItemNodeFactory extends ItemNodeFactory<TocItem> {

        protected TocItemNodeFactory(Lookup.Provider provider, View view, List<TocItem> children) {
            super(provider, view, children);
        }

        @Override
        protected Node createNodeForKey(TocItem key) {
            TocItemNodeFactory tocItemNodeFactory = createFactory(getProvider(), getView(), key.getChildren());
            return new TocItemNode(createChildren(tocItemNodeFactory), tocItemNodeFactory, getProvider(), getView(), key);
        }
    }
}
