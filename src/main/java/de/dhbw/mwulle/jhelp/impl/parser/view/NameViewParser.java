package de.dhbw.mwulle.jhelp.impl.parser.view;

import de.dhbw.mwulle.jhelp.impl.builder.ViewBuilder;
import org.w3c.dom.Element;

public class NameViewParser implements ViewParser {
    @Override
    public void parse(ViewBuilder builder, Element element) {
        if (builder.getName() != null) {
            throw new IllegalStateException(String.format("Name was already set to '%s' but got other name node with value %s", builder.getName(), element.getNodeValue()));
        }

        builder.setName(element.getNodeValue());
    }
}
