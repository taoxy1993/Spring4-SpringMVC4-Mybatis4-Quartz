package com.dfs.utils;


import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.dfs.utils.FileUtil.deleteTmpfile;

/**
 * @author taoxy 2019/1/3
 */
public class ImageUtil {

    private static final Logger logger = Logger.getLogger(FileUploadUtil.class);


    public static void transPictureTojpg(MultipartFile imageFile, File file) throws IOException {
        File tmpFile = null;
        try {
            if (imageFile.getName().contains(".jpg") || imageFile.getName().contains(".JPG")) {
                imageFile.transferTo(file);
                //将png图片转格式为jpg
            } else {
                tmpFile = File.createTempFile("tmp", ".png");
                logger.info("创建格式转换临时文件完成，文件完整路径:{}" + tmpFile.getAbsolutePath());
                imageFile.transferTo(tmpFile);
                BufferedImage bufferedImage = ImageIO.read(tmpFile);
                BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
                ImageIO.write(newBufferedImage, "jpg", file);
            }
        } catch (Exception e) {

        } finally {
            deleteTmpfile(tmpFile);
        }

    }
}
