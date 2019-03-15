package com.dfs.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author taoxy 2019/1/9
 */
public class FileUploadUtil {

    private static final Logger logger = Logger.getLogger(FileUploadUtil.class);

    /**
     * 上传图片
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static String uploadImage(MultipartFile multipartFile) throws IOException {
        String dirPath = (ResourceUtil.chat() + new SimpleDateFormat("yyyyMMdd").format(new Date()));
        File dir = new File(ResourceUtil.dataDir() + File.separator + dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Runtime.getRuntime().exec("chmod -R 777 " + dir);
        String filePath = dirPath + File.separator + StringUtil.createUUID() + ".jpg";
        File file = new File(ResourceUtil.dataDir() + File.separator + filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        Runtime.getRuntime().exec("chmod -R 777 " + file);
        multipartFile.transferTo(file);
        return filePath;
    }

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static String uploadImage(MultipartFile multipartFile, String businessName) throws IOException {
        String dirPath = (businessName + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()));
        File dir = new File(ResourceUtil.dataDir() + "/" + dirPath);
        logger.info("上传文件，文件上传目录:{}" + dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Runtime.getRuntime().exec("chmod -R 777 " + dir);
        String filename = multipartFile.getOriginalFilename();// 获取上传的文件的名称
        String prefix = filename.substring(filename.lastIndexOf(".") + 1);//获取后缀
        String filePath = dirPath + "/" + StringUtil.createUUID() + "." + prefix;
        logger.info("上传文件，开始创建目标文件，文件完整路径:{}"+filePath);
        File file = new File(ResourceUtil.dataDir() + "/" + filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        logger.info("上传文件，文件完整路径:{}"+file.getAbsolutePath());
//		ImageUtil.transPictureTojpg(multipartFile, file);
        Runtime.getRuntime().exec("chmod -R 777 " + file);
        multipartFile.transferTo(file);
        return filePath;
    }
}
