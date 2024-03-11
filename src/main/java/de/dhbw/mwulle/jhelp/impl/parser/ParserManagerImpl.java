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
import de.dhbw.mwulle.jhelp.impl.parser.maps.MapIdEntryMapsParser;
import de.dhbw.mwulle.jhelp.impl.parser.maps.MapRefMapsParser;
import de.dhbw.mwulle.jhelp.impl.parser.maps.RootMapsParser;
import de.dhbw.mwulle.jhelp.impl.parser.view.ImageViewParser;
import de.dhbw.mwulle.jhelp.impl.parser.view.LabelViewParser;
import de.dhbw.mwulle.jhelp.impl.parser.view.NameViewParser;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexItemParser;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexViewFactory;
import de.dhbw.mwulle.jhelp.impl.view.toc.BaseTocItemInfoParser;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocItemParser;
import de.dhbw.mwulle.jhelp.impl.view.toc.TocViewFactory;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ParserManagerImpl implements ParserManager {

    private final HelpSetParser rootHelpSetParser;
    private final DocumentBuilderFactory factory;
    private final CustomEntityResolver entityResolver = new CustomEntityResolver();

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
        rootMapsParser.registerChildParser("mapID", new MapIdEntryMapsParser());

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
        viewHelpSetParser.registerViewFactory("javax.help.TOCView", new TocViewFactory(new TocItemParser(), new BaseTocItemInfoParser(), parserManager::getDocument));

        rootHelpSetParser.registerChildParser("view", viewHelpSetParser);

        parserManager.registerDTD("-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN", "helpset.dtd");
        parserManager.registerDTD("-//Sun Microsystems Inc.//DTD JavaHelp Index Version 2.0//EN", "index.dtd");
        parserManager.registerDTD("-//Sun Microsystems Inc.//DTD JavaHelp Map Version 2.0//EN", "map.dtd");
        parserManager.registerDTD("-//Sun Microsystems Inc.//DTD JavaHelp TOC Version 2.0//EN", "toc.dtd");

        return parserManager;
    }

    public void registerDTD(String publicId, String dtdFile) {
        entityResolver.custom.put(publicId, dtdFile);
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
            DocumentBuilder documentBuilder = this.factory.newDocumentBuilder();
            documentBuilder.setEntityResolver(entityResolver);
            Document document = documentBuilder.parse(stream);
            document.normalize();
            return document;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private static class CustomEntityResolver implements EntityResolver {

        private final Map<String, String> custom = new HashMap<>();

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            String dtd = custom.get(publicId);

            System.out.println("Got publixId " + publicId);

            if (dtd == null) {
                return null;
            }

            FileObject fileObject = FileUtil.getConfigRoot().getFileObject("Services/JavaHelp/" + dtd);
            return new InputSource(fileObject.getInputStream());
        }
    }
}
