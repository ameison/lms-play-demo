package util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

public class Files {

    private static String pathImage = "/tmp/bittamina/imagenes/";

    public static String saveImageFile(FilePart<File> filePart){
        String fileName = null;
        try{
            BufferedImage originalImage = ImageIO.read(filePart.getFile());
            String[] parts = filePart.getContentType().split("/");
            fileName = UUID.randomUUID().toString()+"."+parts[1];
            File newFile = new File(pathImage + fileName);
            ImageIO.write(originalImage, parts[1], newFile);

        }catch (FileNotFoundException fe){
            //Logger.error(fe.getMessage());
        }catch (IOException ie){
            //Logger.error(ie.getMessage());
        }
        return fileName;
    }

    public static String saveFile(FilePart<File> filePart){
        String fileName = null;
        try {
            String[] parts = filePart.getContentType().split("/");
            fileName = UUID.randomUUID().toString()+"."+parts[1];
            File newFile1 = new File(pathImage + fileName);
            File file1 = filePart.getFile();
            InputStream isFile1 = new FileInputStream(file1);
            byte[] byteFile1 = IOUtils.toByteArray(isFile1);
            FileUtils.writeByteArrayToFile(newFile1, byteFile1);
            isFile1.close();
        }catch (FileNotFoundException fe){
            //Logger.error(fe.getMessage());
        }catch (IOException ie){
            //Logger.error(ie.getMessage());
        }
        return fileName;
    }

}

