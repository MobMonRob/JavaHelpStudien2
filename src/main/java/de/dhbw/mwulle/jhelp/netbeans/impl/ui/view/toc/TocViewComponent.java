package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class TocViewComponent extends JPanel implements Lookup.Provider, ExplorerManager.Provider {

    private final ExplorerManager explorerManager = new ExplorerManager();
    private final BeanTreeView beanTreeView = new BeanTreeView();
    private final Lookup lookup;

    public TocViewComponent(TocItemNode root) {
        super(new GridLayout());
        lookup = ExplorerUtils.createLookup(explorerManager, new ActionMap());
        lookup.lookupResult(TocItemNode.class).addLookupListener(new TocChangeListener());
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
