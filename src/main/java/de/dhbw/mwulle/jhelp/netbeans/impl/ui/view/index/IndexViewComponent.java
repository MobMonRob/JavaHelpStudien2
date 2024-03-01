package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.index;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexView;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;

public class IndexViewComponent extends JPanel implements Lookup.Provider, ExplorerManager.Provider {

    private final ExplorerManager explorerManager = new ExplorerManager();
    private final BeanTreeView beanTreeView = new BeanTreeView();
    private final Lookup lookup;
    private ViewState viewState = ViewState.READY;
    private final IndexView view;

    public IndexViewComponent(Lookup.Provider parentProvider, IndexItemNode root, IndexView view) {
        super(new GridLayout()); // We use Grid, so that the bean tree view fits nicely
        this.view = view;
        lookup = ExplorerUtils.createLookup(explorerManager, new ActionMap());
        beanTreeView.setRootVisible(false);
        beanTreeView.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        explorerManager.setRootContext(root);

        parentProvider.getLookup().lookupResult(IndexItemNode.class).addLookupListener(new IndexChangeListener(this));
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

    public IndexView getView() {
        return view;
    }
}
