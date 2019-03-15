package com.dfs.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


/**
 * @author taoxy 2019/1/3
 */
public class ZipUtils {

    private static final Logger log = Logger.getLogger(ZipUtils.class);

    private static final int BUFFER = 1024 * 4;

    private ZipUtils() {
    }

    /**
     * APDPlat中的重要打包机制
     * 将jar文件中的某个文件夹里面的内容复制到某个文件夹
     *
     * @param jar    包含静态资源的jar包
     * @param subDir jar中包含待复制静态资源的文件夹名称
     * @param loc    静态资源复制到的目标文件夹
     * @param force  目标静态资源存在的时候是否强制覆盖
     */
    public static void unZip(String jar, String subDir, String loc, boolean force) {
        try {
            File base = new File(loc);
            if (!base.exists()) {
                base.mkdirs();
            }

            ZipFile zip = new ZipFile(new File(jar));
            Enumeration<? extends ZipEntry> entrys = zip.entries();
            while (entrys.hasMoreElements()) {
                ZipEntry entry = entrys.nextElement();
                String name = entry.getName();
                if (!name.startsWith(subDir)) {
                    continue;
                }
                // 去掉subDir
                name = name.replace(subDir, "").trim();
                if (name.length() < 2) {
                    log.debug(name + " 长度 < 2");
                    continue;
                }
                if (entry.isDirectory()) {
                    File dir = new File(base, name);
                    if (!dir.exists()) {
                        dir.mkdirs();
                        log.debug("创建目录");
                    } else {
                        log.debug("目录已经存在");
                    }
                    log.debug(name + " 是目录");
                } else {
                    File file = new File(base, name);
                    if (file.exists() && force) {
                        file.delete();
                    }
                    if (!file.exists()) {
                        InputStream in = zip.getInputStream(entry);
                        FileUtil.copy(in, file);
                        log.debug("创建文件");
                    } else {
                        log.debug("文件已经存在");
                    }
                    log.debug(name + " 不是目录");
                }
            }
        } catch (ZipException ex) {
            log.error("文件解压失败", ex);
        } catch (IOException ex) {
            log.error("文件操作失败", ex);
        }
    }

    /**
     * 创建ZIP文件
     *
     * @param zipPath 生成的zip文件存在路径（包括文件名）
     */
    public static boolean createZip(List<String> sourcePaths, String zipPath) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            for (String sourcePath : sourcePaths) {
                writeZip(new File(sourcePath), "", zos);
            }
        } catch (FileNotFoundException e) {
            log.error("创建ZIP文件失败", e);
            return false;
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                log.error("创建ZIP文件失败", e);
                return false;
            }

        }
        return true;
    }

    /**
     * 创建ZIP文件
     *
     * @param sourcePath 文件或文件夹路径
     * @param zipPath    生成的zip文件存在路径（包括文件名）
     */
    public static void createZip(String sourcePath, String zipPath) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            writeZip(new File(sourcePath), "", zos);
        } catch (FileNotFoundException e) {
            log.error("创建ZIP文件失败", e);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                log.error("创建ZIP文件失败", e);
            }

        }
    }

    /**
     * 写入ZIP文件
     *
     * @param file
     * @param parentPath
     * @param zos
     */
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) {
            if (file.isDirectory()) {// 处理文件夹
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                for (File f : files) {
                    writeZip(f, parentPath, zos);
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] buffer = new byte[BUFFER];
                    int len;
                    while ((len = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                    log.error("创建ZIP文件失败", e);
                } catch (IOException e) {
                    log.error("创建ZIP文件失败", e);
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        log.error("创建ZIP文件失败", e);
                    }
                }
            }
        }
    }

}
