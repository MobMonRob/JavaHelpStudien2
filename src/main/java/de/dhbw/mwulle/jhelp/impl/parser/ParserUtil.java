package de.dhbw.mwulle.jhelp.impl.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

public final class ParserUtil {

    private ParserUtil() {
    }

    public static void foreachChildrenElement(Node node, Consumer<Element> elementConsumer) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                // Ignore none element nodes, can be a comment for example
                continue;
            }

            elementConsumer.accept((Element) childNode);
        }
    }

    public static Element getElementByTagName(String key, Document parent) {
        NodeList nodeList = parent.getElementsByTagName(key);
        return getElementByTagName(key, nodeList);
    }

    public static Element getElementByTagName(String key, Element parent) {
        NodeList nodeList = parent.getElementsByTagName(key);
        return getElementByTagName(key, nodeList);
    }

    private static Element getElementByTagName(String key, NodeList nodeList) {
        if (nodeList.getLength() != 1) {
            throw new RuntimeException(String.format("Expected exactly 1 element with key '%s' but got %s", key, nodeList.getLength()));
        }

        Node node = nodeList.item(0);

        if (node.getNodeType() != Node.ELEMENT_NODE) {
            throw new RuntimeException(String.format("Expected note with key '%s' of type element but got type %s", key, node.getNodeType()));
        }

        return (Element) node;
    }

    public static URL resolve(URL directory, String relativPath) {
        try {
            return new URL(directory + relativPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
