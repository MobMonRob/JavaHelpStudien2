package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.search;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.MapIdEntry;
import de.dhbw.mwulle.jhelp.netbeans.impl.ContentManager;
import de.dhbw.mwulle.jhelp.netbeans.impl.SearchResult;
import de.dhbw.mwulle.jhelp.netbeans.impl.Searcher;
import org.openide.util.Lookup;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class SearchComponent extends JPanel implements Closeable {

    private final Lookup.Provider provider;
    private final Searcher searcher;
    private final JTextField textField;
    private final JList<SearchResult> jList;

    public SearchComponent(Lookup.Provider provider, Searcher searcher) {
        super(new BorderLayout());
        this.provider = provider;
        this.searcher = searcher;

        this.textField = new JTextField();
        this.jList = new JList<>();
        add(textField, BorderLayout.NORTH);
        add(jList, BorderLayout.CENTER);

        textField.setEditable(true);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.addListSelectionListener(this::handleSelection);
        textField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                handle();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handle();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handle();
            }
        });
    }

    private void handle() {
        String text = textField.getText();

        if (text.length() <= 3) {
            return;
        }

        List<SearchResult> searchResults = searcher.search(text);
        System.out.println(searchResults);
        jList.setListData(searchResults.toArray(new SearchResult[0]));
    }

    private void handleSelection(ListSelectionEvent e) {
        SearchResult result = jList.getSelectedValue();

        if (result == null) {
            System.out.println("Nothing selected");
            return;
        }

        ContentManager contentManager = provider.getLookup().lookup(ContentManager.class);
        HelpSet helpSet = provider.getLookup().lookup(HelpSet.class);

        MapIdEntry mapIdEntry = helpSet.findMapIdEntry(result.getMapId());

        if (mapIdEntry == null) {
            System.out.println("Did not found Map id " + result.getMapId());
            return;
        }

        contentManager.setContent(mapIdEntry);
    }

    @Override
    public void close() throws IOException {
        searcher.close();
    }
}
