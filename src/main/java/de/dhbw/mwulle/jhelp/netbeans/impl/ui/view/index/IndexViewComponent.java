package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.index;

import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class IndexViewComponent extends JPanel implements Lookup.Provider, ExplorerManager.Provider {

    private final ExplorerManager explorerManager = new ExplorerManager();
    private final BeanTreeView beanTreeView = new BeanTreeView();
    private final Lookup lookup;

    public IndexViewComponent(IndexItemNode root) {
        super(new GridLayout()); // We use Grid, so that the bean tree view fits nicely
        lookup = ExplorerUtils.createLookup(explorerManager, new ActionMap());
        lookup.lookupResult(IndexItemNode.class).addLookupListener(new IndexChangeListener());
        beanTreeView.setRootVisible(false);
        beanTreeView.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        explorerManager.setRootContext(root);

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
}
