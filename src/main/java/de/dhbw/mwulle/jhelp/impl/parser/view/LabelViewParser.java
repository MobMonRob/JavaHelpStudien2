package de.dhbw.mwulle.jhelp.impl.parser.view;

import de.dhbw.mwulle.jhelp.impl.builder.ViewBuilder;
import org.w3c.dom.Element;

public class LabelViewParser implements ViewParser {
    @Override
    public void parse(ViewBuilder builder, Element element) {
        if (builder.getLabel() != null) {
            throw new IllegalStateException(String.format("Label was already set to '%s' but got other label node with value %s", builder.getLabel(), element.getNodeValue()));
        }

        builder.setLabel(element.getNodeValue());
    }
}
