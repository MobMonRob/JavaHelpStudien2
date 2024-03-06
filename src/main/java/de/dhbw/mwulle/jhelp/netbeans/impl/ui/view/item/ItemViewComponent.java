package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item;

import de.dhbw.mwulle.jhelp.api.MapIdEntry;
import de.dhbw.mwulle.jhelp.impl.view.item.Item;
import de.dhbw.mwulle.jhelp.impl.view.item.ItemView;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;
import org.openide.util.Utilities;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class ItemViewComponent<T extends Item<T>> extends JPanel implements Lookup.Provider, ExplorerManager.Provider {

    private final ExplorerManager explorerManager = new ExplorerManager();
    private final BeanTreeView beanTreeView = new BeanTreeView();
    private final ItemView<T> view;
    private final Class<? extends ItemNode<T>> itemNodeClass;
    private final Lookup lookup;
    private ViewState viewState = ViewState.READY;


    public ItemViewComponent(ItemView<T> view, Class<? extends ItemNode<T>> itemNodeClass, Lookup.Provider parentProvider, ItemNode<T> root) {
        super(new GridLayout());
        this.view = view;
        this.itemNodeClass = itemNodeClass;
        this.lookup = ExplorerUtils.createLookup(explorerManager, new ActionMap());

        beanTreeView.setRootVisible(false);
        beanTreeView.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        explorerManager.setRootContext(root);

        parentProvider.getLookup().lookupResult(itemNodeClass).addLookupListener(new ItemNodeChangeListener<>(parentProvider, this));
        parentProvider.getLookup().lookupResult(MapIdEntry.class).addLookupListener(new MapIdChangeListener<>(parentProvider, this));

        add(beanTreeView);
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    public ItemView<T> getView() {
        return view;
    }

    public Class<? extends ItemNode<T>> getItemNodeClass() {
        return itemNodeClass;
    }

    public ViewState getViewState() {
        return viewState;
    }

    public void setViewState(ViewState viewState) {
        this.viewState = viewState;
    }
}
