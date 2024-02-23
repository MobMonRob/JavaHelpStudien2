package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view;

import de.dhbw.mwulle.jhelp.api.View;

import java.awt.*;

public interface UiViewFactory {

    Component createComponent(View view);

    Class<? extends View> getViewClass();
}
