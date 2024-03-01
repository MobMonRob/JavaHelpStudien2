package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item;

import de.dhbw.mwulle.jhelp.impl.view.item.Item;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

public abstract class ItemNode<T extends Item<T>> extends AbstractNode {

    private final T item;

    protected ItemNode(Children children, T item) {
        super(children);
        this.item = item;
        if (item != null) {
            setDisplayName(item.getText());
        }
    }

    public T getItem() {
        return item;
    }


    protected abstract static class ItemNodeFactory<T extends Item<T>> extends ChildFactory<T> {
        private final List<T> children;

        protected ItemNodeFactory(List<T> children) {
            this.children = children;
        }

        @Override
        protected abstract Node createNodeForKey(T key);

        @Override
        protected boolean createKeys(List<T> list) {
            list.addAll(children);
            return true;
        }
    }
}
