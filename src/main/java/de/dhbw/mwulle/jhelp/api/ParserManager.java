package de.dhbw.mwulle.jhelp.api;

import java.net.URI;

public interface ParserManager {

    HelpSet parseHelpSet(HelpSetId helpSetId, URI uri);
}
