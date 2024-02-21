package de.dhbw.mwulle.jhelp.impl.view.index;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.builder.ViewBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import de.dhbw.mwulle.jhelp.impl.view.ViewFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.net.URL;
import java.util.function.Function;

public class IndexViewFactory implements ViewFactory {

    private final IndexItemParser parser;
    private final Function<URL, Document> documentFunction;

    public IndexViewFactory(IndexItemParser parser, Function<URL, Document> documentFunction) {
        this.parser = parser;
        this.documentFunction = documentFunction;
    }

    @Override
    public View createView(ViewBuilder viewBuilder, Element dataTag) {
        Document document = documentFunction.apply(ParserUtil.resolve(viewBuilder.getDirectory(), dataTag.getNodeValue()));

        // TODO 2024-02-21: Handler language attribute from root index element
        Element root = ParserUtil.getElementByTagName("index", document);

        return new IndexView(viewBuilder.getName(), viewBuilder.getLabel(), viewBuilder.getType(), viewBuilder.getMergeType(), viewBuilder.getLanguage(), parser.parse(root));
    }
}
