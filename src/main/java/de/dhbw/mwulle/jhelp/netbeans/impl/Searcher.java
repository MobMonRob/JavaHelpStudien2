package de.dhbw.mwulle.jhelp.netbeans.impl;

import de.dhbw.mwulle.jhelp.api.MapId;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Searcher implements Closeable {

    private final IndexReader indexReader;
    private final IndexSearcher indexSearcher;

    public Searcher(IndexReader reader, IndexSearcher indexSearcher) {
        this.indexReader = reader;
        this.indexSearcher = indexSearcher;
    }

    public List<SearchResult> search(String queryString) {
        List<SearchResult> results = new ArrayList<>();
        SimpleQueryParser parser = new SimpleQueryParser(new StandardAnalyzer(), "body");
        Query query = parser.parse(queryString);

        try {
            TopDocs topDocs = indexSearcher.search(query, 30);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document document = indexSearcher.storedFields().document(scoreDoc.doc);
                results.add(new SearchResult(scoreDoc.score, MapId.fromString(document.getField("MAP_ID").stringValue())));
            }

            results.sort((a, b) -> Float.compare(a.getScore(), b.getScore()));

            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        indexReader.close();
    }
}
