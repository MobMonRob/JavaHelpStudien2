package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.index;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.UiViewFactory;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.awt.*;

@ServiceProvider(service = UiViewFactory.class)
public class IndexUiViewFactory implements UiViewFactory {
    @Override
    public Component createComponent(Lookup.Provider provider, View view) {
        IndexView indexView = (IndexView) view; // TODO 2024-02-22: Probably check and handle cases where it is not the correct view
        return new IndexViewComponent(provider, IndexItemNode.createRootNode(indexView), indexView);
    }

    @Override
    public Class<? extends View> getViewClass() {
        return IndexView.class;
    }
}
