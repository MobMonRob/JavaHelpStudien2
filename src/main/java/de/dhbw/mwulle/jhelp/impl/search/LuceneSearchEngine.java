package de.dhbw.mwulle.jhelp.impl.search;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.HelpSetId;
import de.dhbw.mwulle.jhelp.api.MapIdEntry;
import de.dhbw.mwulle.jhelp.api.search.SearchEngine;
import de.dhbw.mwulle.jhelp.api.search.Searcher;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class LuceneSearchEngine implements SearchEngine {

    private static final String INDEX_BASE_LOCATION = "jhelp/index/";
    private static final String INDEX_INFO_FILE = "jhelp-index-info";

    private final Map<HelpSetId, File> loadedFiles = new HashMap<>();

    @Override
    public synchronized void indexHelpSet(String buildNumber, HelpSetId identifier, HelpSet helpSet) {
        if (loadedFiles.containsKey(identifier)) {
            System.out.println("Already loaded");
            return;
        }

        File directory = getDirectory(identifier);
        System.out.println("Directory: " + directory);

        if (directory.exists() && sameBuild(directory, buildNumber)) {
            System.out.println("Already cached");
            loadedFiles.put(identifier, directory);
            return;
        }

        if (directory.exists()) {
            try (Stream<Path> stream = Files.walk(directory.toPath())) {
                stream.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (IndexWriter indexWriter = new IndexWriter(FSDirectory.open(directory.toPath()), new IndexWriterConfig(new StandardAnalyzer()))) {
            indexMapId(indexWriter, helpSet.getHelpSetMap().getMapIdEntries());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File(directory, INDEX_INFO_FILE)))) {
            fileWriter.write(buildNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadedFiles.put(identifier, directory);
    }

    @Override
    public Searcher createSearcher(List<HelpSetId> identifiers) {
        List<File> directories = new ArrayList<>(identifiers.size());

        for (HelpSetId identifier : identifiers) {
            File directory = loadedFiles.get(identifier);
            if (directory == null) {
                throw new RuntimeException("File is not loaded for " + identifier);
            }

            directories.add(directory);
        }

        try {
            IndexReader reader = new MultiReader(directories.stream().map(d -> {
                try {
                    return DirectoryReader.open(FSDirectory.open(d.toPath()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).toArray(IndexReader[]::new));

            return new LuceneSearcher(reader, new IndexSearcher(reader));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract File getCacheDirectory();

    private boolean sameBuild(File directory, String buildNumber) {
        File info = new File(directory, INDEX_INFO_FILE);
        if (!info.exists()) {
            return false;
        }

        try (BufferedReader fileReader = new BufferedReader(new FileReader(info))) {
            String version = fileReader.readLine();
            if (Objects.equals(buildNumber, version)) {
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private File getDirectory(HelpSetId helpSetId) {
        String identifier = helpSetId.getStringId().replace('.', '/');
        if (identifier.startsWith("/")) {
            identifier = identifier.substring(1);
        }

        return new File(getCacheDirectory(), INDEX_BASE_LOCATION + identifier);
    }

    private void indexMapId(IndexWriter indexWriter, List<MapIdEntry> mapIds) {
        for (MapIdEntry mapIdEntry : mapIds) {
            indexMapId(indexWriter, mapIdEntry.getChildren());

            if (!mapIdEntry.getUrl().toString().endsWith(".html")) {
                continue;
            }

            try (InputStream inputStream = mapIdEntry.getUrl().openStream()) {
                Document document = new Document();
                document.add(new TextField("MAP_ID", mapIdEntry.getId().getStringId(), Field.Store.YES));

                org.jsoup.nodes.Document html = Jsoup.parse(inputStream, null, mapIdEntry.getUrl().toString());
                document.add(new TextField("title", html.title(), Field.Store.YES));
                document.add(new TextField("body", html.text(), Field.Store.YES));

                indexWriter.addDocument(document);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
