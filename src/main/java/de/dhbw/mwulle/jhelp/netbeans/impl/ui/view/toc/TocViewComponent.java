package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocView;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;

public class TocViewComponent extends JPanel implements Lookup.Provider, ExplorerManager.Provider {

    private final ExplorerManager explorerManager = new ExplorerManager();
    private final BeanTreeView beanTreeView = new BeanTreeView();
    private final Lookup lookup;
    private ViewState viewState = ViewState.READY;
    private final TocView view;

    public TocViewComponent(Lookup.Provider parentProvider, TocItemNode root, TocView view) {
        super(new GridLayout());
        this.view = view;
        lookup = ExplorerUtils.createLookup(explorerManager, new ActionMap());
        beanTreeView.setRootVisible(false);
        beanTreeView.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        explorerManager.setRootContext(root);

        parentProvider.getLookup().lookupResult(TocItemNode.class).addLookupListener(new TocChangeListener(this));
        parentProvider.getLookup().lookupResult(MapId.class).addLookupListener(new MapIdChangeListener(this));

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

    public ViewState getViewState() {
        return viewState;
    }

    public void setViewState(ViewState viewState) {
        this.viewState = viewState;
    }

    public TocView getView() {
        return view;
    }
}
