package de.dhbw.mwulle.jhelp.impl.parser.helpset;

import de.dhbw.mwulle.jhelp.impl.builder.HelpSetBuilder;
import org.w3c.dom.Element;

public class TitleHelpSetParser implements HelpSetParser {

    @Override
    public void parse(HelpSetBuilder builder, Element element) {
        if (builder.getTitle() != null) {
            throw new IllegalStateException(String.format("Title was already set to '%s' but got other title node with value %s", builder.getTitle(), element.getNodeValue()));
        }

        builder.setTitle(element.getNodeValue());
    }
}
