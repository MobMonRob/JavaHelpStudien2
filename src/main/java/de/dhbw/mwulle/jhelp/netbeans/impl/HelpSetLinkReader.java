package de.dhbw.mwulle.jhelp.netbeans.impl;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.impl.parser.ParserManagerImpl;
import org.openide.cookies.InstanceCookie;
import org.openide.loaders.DataObject;
import org.openide.loaders.Environment;
import org.openide.loaders.XMLDataObject;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.xml.EntityCatalog;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.net.URI;

public class HelpSetLinkReader implements Environment.Provider {

    @Override
    public Lookup getEnvironment(DataObject obj) {
        return Lookups.singleton(new InstanceCookie() {
            @Override
            public String instanceName() {
                return obj.getName();
            }

            @Override
            public Class<?> instanceClass() throws IOException, ClassNotFoundException {
                return HelpSet.class;
            }

            @Override
            public Object instanceCreate() throws IOException, ClassNotFoundException {
                System.out.println("Create HelpSet Instance");

                XMLDataObject xmlDataObject = (XMLDataObject) obj;
                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    Document document = factory.newDocumentBuilder().parse(xmlDataObject.getPrimaryFile().toURL().toString());
                    ClassLoader classLoader = Lookup.getDefault().lookup(ClassLoader.class);
                    String url = document.getDocumentElement().getAttribute("url");

                    System.out.println("Reading HelpSet from: " + url);
                    URI uri = classLoader.getResource(url).toURI();
                    System.out.println("Reading from uri: " + uri);

                    HelpSet helpSet = ParserManagerImpl.createDefault(EntityCatalog.getDefault()).parseHelpSet(uri);

                    // HelpSetManager.getInstance().loadHelpSet(classLoader.getResource(url));
                    return helpSet;
                } catch (Throwable e) {
                    System.out.println("Error next: " + e.getMessage());
                    e.printStackTrace(System.out);
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
