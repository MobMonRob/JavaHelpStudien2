package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.MapIdEntry;
import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.view.item.Item;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.function.IntFunction;

public abstract class ItemNode<T extends Item<T>> extends AbstractNode {

    private final Lookup.Provider provider;
    private final View view;
    private final T item;

    protected ItemNode(Children children, Lookup.Provider provider, View view, T item) {
        super(children);
        this.provider = provider;
        this.view = view;
        this.item = item;
        if (item != null) {
            setDisplayName(item.getText());
        }
    }

    public T getItem() {
        return item;
    }

    public Lookup.Provider getProvider() {
        return provider;
    }

    public View getView() {
        return view;
    }

    protected MapId getDefaultOpenIcon() {
        return null;
    }

    protected MapId getDefaultClosedIcon() {
        return null;
    }

    protected MapId getDefaultLeafIcon() {
        return null;
    }

    protected MapId getIcon() {
        return null;
    }

    @Override
    public Image getIcon(int type) {
        return getIcon(type, getDefaultClosedIcon(), super::getIcon);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type, getDefaultOpenIcon(), super::getOpenedIcon);
    }

    private Image getIcon(int type, MapId noneLeafIcon, IntFunction<Image> fallback) {
        if (getItem() == null) {
            return fallback.apply(type);
        }

        System.out.println("Ping");
        HelpSet helpSet = getProvider().getLookup().lookup(HelpSet.class);
        if (helpSet == null) {
            System.out.println("Peng");
            return fallback.apply(type);
        }

        Image image = getFromIcon(helpSet, type, getIcon());
        if (image != null) {
            return image;
        }

        if (getChildren() == Children.LEAF) {
            image = getFromIcon(helpSet, type, getDefaultLeafIcon());
            if (image != null) {
                return image;
            }
        } else {
            image = getFromIcon(helpSet, type, noneLeafIcon);
            if (image != null) {
                return image;
            }
        }

        return fallback.apply(type);
    }

    private Image getFromIcon(HelpSet helpSet, int type, MapId icon) {
        if (icon == null) {
            return null;
        }

        MapIdEntry mapIdEntry = helpSet.findMapIdEntry(icon);

        if (mapIdEntry == null) {
            return null;
        }

        if (mapIdEntry.getUrl() == null) {
            return null;
        }

        try {
            return IconUtil.convertImage(type, ImageIO.read(mapIdEntry.getUrl()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract static class ItemNodeFactory<T extends Item<T>> extends ChildFactory<T> {
        private final Lookup.Provider provider;
        private final View view;
        private final List<T> children;

        protected ItemNodeFactory(Lookup.Provider provider, View view, List<T> children) {
            this.provider = provider;
            this.view = view;
            this.children = children;
        }

        @Override
        protected abstract Node createNodeForKey(T key);

        @Override
        protected boolean createKeys(List<T> list) {
            list.addAll(children);
            return true;
        }

        public View getView() {
            return view;
        }

        public Lookup.Provider getProvider() {
            return provider;
        }
    }
}
