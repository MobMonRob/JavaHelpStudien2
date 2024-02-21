package de.dhbw.mwulle.jhelp.impl.parser;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.ParserManager;
import de.dhbw.mwulle.jhelp.impl.builder.HelpSetBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.helpset.HelpSetParser;
import de.dhbw.mwulle.jhelp.impl.parser.helpset.MapsHelpSetParser;
import de.dhbw.mwulle.jhelp.impl.parser.helpset.RootHelpSetParser;
import de.dhbw.mwulle.jhelp.impl.parser.helpset.TitleHelpSetParser;
import de.dhbw.mwulle.jhelp.impl.parser.helpset.ViewHelpSetParser;
import de.dhbw.mwulle.jhelp.impl.parser.maps.HomeIdMapsParser;
import de.dhbw.mwulle.jhelp.impl.parser.maps.MapIdMapsParser;
import de.dhbw.mwulle.jhelp.impl.parser.maps.MapRefMapsParser;
import de.dhbw.mwulle.jhelp.impl.parser.maps.RootMapsParser;
import de.dhbw.mwulle.jhelp.impl.parser.view.ImageViewParser;
import de.dhbw.mwulle.jhelp.impl.parser.view.LabelViewParser;
import de.dhbw.mwulle.jhelp.impl.parser.view.NameViewParser;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexItemParser;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexViewFactory;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocItemParser;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocViewFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ParserManagerImpl implements ParserManager {

    private final HelpSetParser rootHelpSetParser;
    private final DocumentBuilderFactory factory;

    public ParserManagerImpl(HelpSetParser rootHelpSetParser) throws ParserConfigurationException {
        this.rootHelpSetParser = rootHelpSetParser;
        this.factory = DocumentBuilderFactory.newInstance();
    }

    public static ParserManagerImpl createDefault() throws ParserConfigurationException {
        RootHelpSetParser rootHelpSetParser = new RootHelpSetParser();
        ParserManagerImpl parserManager = new ParserManagerImpl(rootHelpSetParser);

        rootHelpSetParser.registerChildParser("title", new TitleHelpSetParser());

        // Maps file parser
        RootMapsParser rootMapsParser = new RootMapsParser();
        rootMapsParser.registerChildParser("mapID", new MapIdMapsParser());

        // HelpSet Maps tag parser
        MapsHelpSetParser mapsHelpSetParser = new MapsHelpSetParser();
        mapsHelpSetParser.registerChildParser("homeID", new HomeIdMapsParser());
        mapsHelpSetParser.registerChildParser("mapref", new MapRefMapsParser(rootMapsParser, parserManager::getDocument));

        rootHelpSetParser.registerChildParser("maps", mapsHelpSetParser);

        // HelpSet View tag parser
        ViewHelpSetParser viewHelpSetParser = new ViewHelpSetParser();
        viewHelpSetParser.registerChildParser("name", new NameViewParser());
        viewHelpSetParser.registerChildParser("label", new LabelViewParser());
        viewHelpSetParser.registerChildParser("image", new ImageViewParser());

        viewHelpSetParser.registerViewFactory("javax.help.IndexView", new IndexViewFactory(new IndexItemParser(), parserManager::getDocument));
        viewHelpSetParser.registerViewFactory("javax.help.TOCView", new TocViewFactory(new TocItemParser(), parserManager::getDocument));

        rootHelpSetParser.registerChildParser("view", viewHelpSetParser);

        return parserManager;
    }

    @Override
    public HelpSet parseHelpSet(URI uri) {
        URL url;
        URL directory;
        Document document = null;
        try {
            url = uri.toURL();
            String urlString = url.toString();
            directory = new URL(urlString.substring(0, urlString.lastIndexOf('/') + 1));
            System.out.println("Directory: " + directory);
            document = getDocument(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        Element root = document.getDocumentElement();
        if (!"helpset".equals(root.getTagName())) {
            System.out.println("ERROR: " + root.getTagName());
            throw new RuntimeException(String.format("Expected root tag to be 'helpset' but got %s", root.getTagName()));
        }

        HelpSetBuilder helpSetBuilder = new HelpSetBuilder(directory);

        rootHelpSetParser.parse(helpSetBuilder, root);

        System.out.println("Help: " + document.getDocumentElement().toString());

        return helpSetBuilder.build();
    }

    private Document getDocument(URL url) {
        try (InputStream stream = url.openStream()) {
            System.out.println("Getting document for url " + url);
            Document document = this.factory.newDocumentBuilder().parse(stream);
            document.normalize();
            return document;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
