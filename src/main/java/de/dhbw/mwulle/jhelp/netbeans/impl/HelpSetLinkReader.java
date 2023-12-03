package de.dhbw.mwulle.jhelp.netbeans.impl;

import de.dhbw.mwulle.jhelp.HelpSetManager;
import org.openide.loaders.DataObject;
import org.openide.loaders.Environment;
import org.openide.loaders.XMLDataObject;
import org.openide.util.Lookup;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;

public class HelpSetLinkReader implements Environment.Provider {

    @Override
    public Lookup getEnvironment(DataObject obj) {
        XMLDataObject xmlDataObject = (XMLDataObject) obj;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document = factory.newDocumentBuilder().parse(xmlDataObject.getPrimaryFile().toURL().toString());
            ClassLoader classLoader = Lookup.getDefault().lookup(ClassLoader.class);
            String url = document.getDocumentElement().getAttribute("url");

            System.out.println("Reading HelpSet from: " + url);

            HelpSetManager.getInstance().loadHelpSet(classLoader.getResource(url));
        } catch (Throwable e) {
            System.out.println("Error next: " + e.getMessage());
            e.printStackTrace(System.out);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null; // TODO: 12/3/23 Actually return a Lookup 
    }
}
