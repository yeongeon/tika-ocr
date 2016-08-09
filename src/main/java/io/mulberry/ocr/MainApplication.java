package io.mulberry.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainApplication {
  //static private Logger LOG = Logger.getLogger(MainApplication.class);

  static final Class<MainApplication> SELF_CLASS = MainApplication.class;

  public static void main(String[] args) throws IOException, TikaException, SAXException, TesseractException {
    URL url = SELF_CLASS.getResource("/files/image/sample-kor.png");
    String targetFile = url.getPath();


    System.out.println("targetFile: " + targetFile);
    File file = new File(targetFile);
    BufferedImage image = ImageIO.read(file);



    // tess4j
    String tessPath = SELF_CLASS.getResource("/tess4j/").getPath();
    Tesseract instance = Tesseract.getInstance();
    File tessDataFolder = LoadLibs.extractTessResources("/tess4j/");
    tessPath = tessDataFolder.getAbsolutePath();

    System.out.println(tessPath);

    instance.setDatapath(tessPath);
    instance.setLanguage("eng+kor");
    //instance.setPageSegMode(ITessAPI.TessPageSegMode.PSM_AUTO);
    //instance.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_TESSERACT_ONLY);
    String result = instance.doOCR(file);

    System.out.println(result);

/*
    // tesseract
    BodyContentHandler handler = new BodyContentHandler();

    Metadata metadata = new Metadata();
    FileInputStream inputstream = new FileInputStream(file);
    ParseContext pcontext = new ParseContext();

    TesseractOCRConfig config = new TesseractOCRConfig();
    //config.setLanguage("eng");

    String tesseractPath = SELF_CLASS.getResource("/files/image/tesseract").getPath();
    System.out.println(tesseractPath);
    config.setTesseractPath(tesseractPath);
    pcontext.set(TesseractOCRConfig.class, config);


    TesseractOCRParser imageParser = new TesseractOCRParser();
    pcontext.set(TesseractOCRParser.class, imageParser);

    imageParser.parse(inputstream, handler, metadata, pcontext);

    System.out.println("Metadata of the document:");
    String[] metadataNames = metadata.names();
    for (String name : metadataNames) {
      System.out.println(name + ": " + metadata.get(name));
    }
    System.out.println("Contents of the document:" + handler.toString());

*/

  }

}
