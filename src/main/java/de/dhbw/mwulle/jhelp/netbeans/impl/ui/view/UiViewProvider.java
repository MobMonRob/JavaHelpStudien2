package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.ui.HelpTopComponent;

import java.awt.*;

public interface UiViewProvider {

    Component createComponent(HelpTopComponent topComponent, HelpSet helpSet, View view);
}
