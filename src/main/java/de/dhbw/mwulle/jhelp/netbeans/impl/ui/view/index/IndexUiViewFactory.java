package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.index;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.UiViewFactory;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.item.ItemViewComponent;
import java.awt.*;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = UiViewFactory.class)
public class IndexUiViewFactory implements UiViewFactory {
    @Override
    public Component createComponent(Lookup.Provider provider, View view) {
        IndexView indexView = (IndexView) view; // TODO 2024-02-22: Probably check and handle cases where it is not the correct view
        return new ItemViewComponent<>(indexView, IndexItemNode.class, provider, IndexItemNode.createRootNode(indexView));
    }

    @Override
    public Class<? extends View> getViewClass() {
        return IndexView.class;
    }
}
