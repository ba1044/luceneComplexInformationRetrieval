

package edu.unh.cs753853.team1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import edu.unh.cs.treccar.*;
import edu.unh.cs.treccar.read_data.*;

public class IndexParagraphs {

    // Empty constructor for IndexParagraphs
    public IndexParagraphs() {

    }

    // Main function, so the program can be run from the command line
    public static void main(String[] args) throws Exception {
        // Relative path definitions
        String indexDir = "index";
        String paragraphFile = "test200/train.test200.cbor.paragraphs";

        // Record start time to track how long indexing took
        Date start = new Date();
        try {
            // Print the name of the directory that we are indexing into
            System.out.println("Indexing to directory '" + indexDir + "'...");

            // Turn string paths into Path and Dir objects
            Directory dir = FSDirectory.open(Paths.get(indexDir));
            final Path paragraphPath = Paths.get(paragraphFile);

            // Get our analyzer and our IndexWriterConfig
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            // Create a new index every time index is run
            iwc.setOpenMode(OpenMode.CREATE);

            // Get our IndexWriter and index the document
            IndexWriter writer = new IndexWriter(dir, iwc);
            indexParagraphs(writer, paragraphPath);

            // Make sure we close our writer
            writer.close();

            // Get our end time and calculate time to index
            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");
        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
        }
    }

    static void indexParagraphs(IndexWriter writer, Path paragraphFile) throws IOException, Exception {
        // Try to open our file for reading
        int count = 0;
        System.out.println("Indexing paragraphs:");
        try(InputStream stream = Files.newInputStream(paragraphFile)){
            // For each paragraph, index it
            for(Data.Paragraph p: DeserializeData.iterableParagraphs(stream)) {
                indexDoc(writer, p);
                count++;
            }
        }
        System.out.print(count + " paragraphs indexed in ");
    }

   //  indexDoc
     //       index the <file> using the given IndexWriter <writer>
     
    static void indexDoc(IndexWriter writer, Data.Paragraph p) throws IOException, Exception {
        // Create our new document
        Document document = new Document();

        // Add the necessary Fields, ID and paragraph content
        Field idField = new StringField("paragraphID", p.getParaId(), Field.Store.YES);
        Field contentField = new TextField("paragraphContent", p.getTextOnly(), Field.Store.YES);

        // Add those fields to our document
        document.add(idField);
        document.add(contentField);

        // Display IDs

        System.out.println(" Paragraph ID:  " + p.getParaId());

        // Add document to the document writer
        writer.addDocument(document);
    }
}
