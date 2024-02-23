package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.UiViewFactory;
import org.openide.util.lookup.ServiceProvider;

import java.awt.*;

@ServiceProvider(service = UiViewFactory.class)
public class TocUiViewFactory implements UiViewFactory {
    @Override
    public Component createComponent(View view) {
        TocView tocView = (TocView) view; // TODO 2024-02-22: Probably check and handle cases where it is not the correct view
        return new TocViewComponent(TocItemNode.createRootNode(tocView));
    }

    @Override
    public Class<? extends View> getViewClass() {
        return TocView.class;
    }
}
