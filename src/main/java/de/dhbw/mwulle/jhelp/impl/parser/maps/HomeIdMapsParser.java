package de.dhbw.mwulle.jhelp.impl.parser.maps;

import de.dhbw.mwulle.jhelp.impl.builder.MapsBuilder;
import org.w3c.dom.Element;

public class HomeIdMapsParser implements MapsParser {
    @Override
    public void parse(MapsBuilder builder, Element element) {
        if (builder.getHomeId() != null) {
            throw new IllegalStateException(String.format("Home id was already set to '%s' but got another home id node with value %s", builder.getHomeId(), element.getTextContent()));
        }

        builder.setHomeId(element.getTextContent());
    }
}
