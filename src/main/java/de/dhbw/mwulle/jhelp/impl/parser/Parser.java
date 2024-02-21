package de.dhbw.mwulle.jhelp.impl.parser;

import org.w3c.dom.Element;

public interface Parser<B> {

    void parse(B builder, Element element);
}
