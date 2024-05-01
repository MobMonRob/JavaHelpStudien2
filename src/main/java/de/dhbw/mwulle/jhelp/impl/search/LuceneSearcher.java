package de.dhbw.mwulle.jhelp.impl.search;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.search.SearchResult;
import de.dhbw.mwulle.jhelp.api.search.Searcher;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LuceneSearcher implements Searcher {

    private final IndexReader indexReader;
    private final IndexSearcher indexSearcher;

    public LuceneSearcher(IndexReader reader, IndexSearcher indexSearcher) {
        this.indexReader = reader;
        this.indexSearcher = indexSearcher;
    }

    @Override
    public List<SearchResult> search(String queryString, int limit) {
        List<SearchResult> results = new ArrayList<>();
        SimpleQueryParser parser = new SimpleQueryParser(new StandardAnalyzer(), "body");
        Query query = parser.parse(queryString);

        try {
            TopDocs topDocs = indexSearcher.search(query, limit);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document document = indexSearcher.storedFields().document(scoreDoc.doc);
                results.add(SearchResult.create(scoreDoc.score, MapId.fromString(document.getField("MAP_ID").stringValue())));
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
