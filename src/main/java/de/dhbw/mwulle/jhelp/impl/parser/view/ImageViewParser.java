package de.dhbw.mwulle.jhelp.impl.parser.view;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.impl.builder.ViewBuilder;
import org.w3c.dom.Element;

public class ImageViewParser implements ViewParser {

    @Override
    public void parse(ViewBuilder builder, Element element) {
        if (builder.getImage() != null) {
            throw new IllegalStateException(String.format("Image was already set to '%s' but got other image node with value %s", builder.getImage(), element.getTextContent()));
        }

        builder.setImage(MapId.fromString(element.getTextContent()));
    }
}
