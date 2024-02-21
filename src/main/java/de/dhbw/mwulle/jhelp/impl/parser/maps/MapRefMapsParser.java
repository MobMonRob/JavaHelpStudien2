package de.dhbw.mwulle.jhelp.impl.parser.maps;

import de.dhbw.mwulle.jhelp.impl.builder.MapsBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import java.util.function.Function;

public class MapRefMapsParser implements MapsParser {

    private final MapsParser rootMapsParser;
    private final Function<URL, Document> documentFunction;

    public MapRefMapsParser(MapsParser rootMapsParser, Function<URL, Document> documentFunction) throws ParserConfigurationException {
        this.rootMapsParser = rootMapsParser;
        this.documentFunction = documentFunction;
    }

    @Override
    public void parse(MapsBuilder builder, Element element) {
        String locString = element.getAttribute("location");

        if (locString.trim().isEmpty()) {
            throw new RuntimeException(String.format("Map ref location is not valid got '%s'", locString));
        }

        URL mapRef = ParserUtil.resolve(builder.getDirectory(), locString);
        Document document = documentFunction.apply(mapRef);

        Element root = document.getDocumentElement();

        rootMapsParser.parse(builder, root);
    }
}
