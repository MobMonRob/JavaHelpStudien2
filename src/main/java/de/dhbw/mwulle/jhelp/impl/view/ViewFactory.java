package de.dhbw.mwulle.jhelp.impl.view;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.builder.ViewBuilder;
import org.w3c.dom.Element;

public interface ViewFactory {

    View createView(ViewBuilder viewBuilder, Element dataTag);
}
