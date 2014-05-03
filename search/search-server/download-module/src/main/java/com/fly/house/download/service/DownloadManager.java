package com.fly.house.download.service;

import com.fly.house.download.exception.FileDownloadException;
import com.fly.house.download.exception.FileParsingException;
import com.fly.house.download.model.DownloadInfo;
import com.fly.house.model.Artifact;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.tika.metadata.HttpHeaders.CONTENT_TYPE;

/**
 * Created by dimon on 03.05.14.
 */
public class DownloadManager {

    private DownloadInfo downloadInfo;

    public DownloadManager(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;

        downloadInfo.getPath();
    }

    public Artifact start() {
        URL url = foldUrl();
        try (InputStream rbc = new BufferedInputStream(url.openStream())) {
            return parse(rbc);
        } catch (IOException e) {
            throw new FileDownloadException(e);
        } catch (SAXException | TikaException e) {
            throw new FileParsingException(e);
        }
    }

    private URL foldUrl() {
        String hostAddress = downloadInfo.getIp().getHostAddress();
        int port = downloadInfo.getPort();
        String path = downloadInfo.getPath();
        try {
            return new URL("http", hostAddress, port, path);
        } catch (MalformedURLException e) {
            throw new FileDownloadException(e);
        }
    }

    private Artifact parse(InputStream in) throws TikaException, SAXException, IOException {
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        BodyContentHandler contentHandler = new BodyContentHandler();
        parser.parse(in, contentHandler, metadata, context);
        return retrieveInfo(metadata, contentHandler);
    }

    private Artifact retrieveInfo(Metadata metadata, BodyContentHandler contentHandler) {
        Artifact artifact = new Artifact();
        artifact.setType(metadata.get(metadata.get(CONTENT_TYPE)));

        String size = metadata.get(Metadata.CONTENT_LENGTH);
        String digest = metadata.get(Metadata.CONTENT_MD5);

        //todo fix
        artifact.setTitle("");
        artifact.setDigest(digest);
        artifact.setSize(Long.valueOf(size));
        artifact.setFullText(contentHandler.toString());
        return artifact;
    }

}
