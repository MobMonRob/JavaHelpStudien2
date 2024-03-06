package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view;

import de.dhbw.mwulle.jhelp.api.View;
import org.openide.util.Lookup;

import java.awt.*;

public interface UiViewFactory {

    Component createComponent(Lookup.Provider lookupProvider, View view);

    Class<? extends View> getViewClass();
}
