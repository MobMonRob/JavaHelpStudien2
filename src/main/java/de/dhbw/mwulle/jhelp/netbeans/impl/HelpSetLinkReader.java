package de.dhbw.mwulle.jhelp.netbeans.impl;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.HelpSetId;
import de.dhbw.mwulle.jhelp.api.search.SearchEngine;
import de.dhbw.mwulle.jhelp.impl.parser.ParserManagerImpl;
import org.openide.cookies.InstanceCookie;
import org.openide.loaders.DataObject;
import org.openide.loaders.Environment;
import org.openide.loaders.XMLDataObject;
import org.openide.modules.ModuleInfo;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.xml.EntityCatalog;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

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
                    ModuleInfo moduleInfo = null;
                    URI uri = null;
                    for (ModuleInfo info : Lookup.getDefault().lookupAll(ModuleInfo.class)) {
                        if (!info.isEnabled()) {
                            continue;
                        }

                        URL other = info.getClassLoader().getResource(url);

                        if (other != null) {
                            System.out.println("Found: " + other);
                            System.out.println("With: " + info);
                            moduleInfo = info;
                            uri = other.toURI();
                            break;
                        }
                    }
                    System.out.println("Reading from uri: " + uri);

                    if (uri == null) {
                        System.out.println("Not found for " + url);
                        return null;
                    }

                    HelpSetId helpSetId = HelpSetId.fromString(url);
                    HelpSet helpSet = ParserManagerImpl.createDefault(EntityCatalog.getDefault()).parseHelpSet(helpSetId, uri);

                    Lookup.getDefault().lookup(SearchEngine.class).indexHelpSet(moduleInfo.getBuildVersion(), helpSetId, helpSet);

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
