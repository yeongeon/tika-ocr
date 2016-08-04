package io.mulberry.ocr;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.ocr.TesseractOCRParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class MainApplication {

  static final Class<MainApplication> SELF_CLASS = MainApplication.class;

  public static void main(String[] args) throws IOException, TikaException, SAXException {
    URL url = SELF_CLASS.getResource("/files/image/sample1.png");
    String targetFile = url.getPath();

    System.out.println("targetFile: " + targetFile);
    File file = new File(targetFile);
    BufferedImage image = ImageIO.read(file);

    BodyContentHandler handler = new BodyContentHandler();

    Metadata metadata = new Metadata();
    FileInputStream inputstream = new FileInputStream(file);
    ParseContext pcontext = new ParseContext();

    TesseractOCRConfig config = new TesseractOCRConfig();
    config.setLanguage("eng");

    config.setTesseractPath("/usr/local/bin/tesseract");
    pcontext.set(TesseractOCRConfig.class, config);

    TesseractOCRParser JpegParser = new TesseractOCRParser();
    pcontext.set(TesseractOCRParser.class, JpegParser);

    JpegParser.parse(inputstream, handler, metadata, pcontext);

    System.out.println("Metadata of the document:");
    String[] metadataNames = metadata.names();
    for (String name : metadataNames) {
      System.out.println(name + ": " + metadata.get(name));
    }
    System.out.println("Contents of the document:" + handler.toString());
  }

}
