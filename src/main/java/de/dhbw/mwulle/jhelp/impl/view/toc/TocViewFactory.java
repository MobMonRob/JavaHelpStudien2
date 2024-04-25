package de.dhbw.mwulle.jhelp.impl.view.toc;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.impl.builder.ViewBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import de.dhbw.mwulle.jhelp.impl.view.ViewFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.net.URL;
import java.util.function.Function;

public class TocViewFactory implements ViewFactory {

    private final TocItemParser parser;
    private final BaseTocItemInfoParser baseTocItemInfoParser;
    private final Function<URL, Document> documentFunction;

    public TocViewFactory(TocItemParser parser, BaseTocItemInfoParser baseTocItemInfoParser, Function<URL, Document> documentFunction) {
        this.parser = parser;
        this.baseTocItemInfoParser = baseTocItemInfoParser;
        this.documentFunction = documentFunction;
    }

    @Override
    public View createView(ViewBuilder viewBuilder, Element dataTag) {
        Document document = documentFunction.apply(ParserUtil.resolve(viewBuilder.getDirectory(), dataTag.getTextContent()));

        // TODO 2024-02-21: Handle language attribute from root index element
        Element root = ParserUtil.getElementByTagName("toc", document);

        MergeType mergeType = viewBuilder.getMergeType();
        if (mergeType == null) {
            // From spec, this is default for this view type
            mergeType = MergeType.fromString("javax.help.AppendMerge");
        }

        return new TocView(viewBuilder.getName(), viewBuilder.getLabel(), viewBuilder.getType(), mergeType, viewBuilder.getLanguage(), parser.parse(root), baseTocItemInfoParser.parse(root));
    }
}
