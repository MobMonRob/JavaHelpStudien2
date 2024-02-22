package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.toc;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.UiViewProvider;
import de.dhbw.mwulle.jhelp.ui.HelpTopComponent;

import java.awt.*;

public class TocUiViewProvider implements UiViewProvider {
    @Override
    public Component createComponent(HelpTopComponent topComponent, HelpSet helpSet, View view) {
        TocView tocView = (TocView) view; // TODO 2024-02-22: Probably check and handle cases where it is not the correct view
        return new TocViewComponent(TocItemNode.createRootNode(tocView));
    }
}
