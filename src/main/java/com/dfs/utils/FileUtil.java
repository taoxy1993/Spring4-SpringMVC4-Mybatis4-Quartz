package com.dfs.utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import ws.schild.jave.*;

import javax.management.loading.MLet;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import static com.sun.jmx.defaults.JmxProperties.MLET_LOGGER;

/**
 * @author taoxy 2019/1/3
 */
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);

    private static final int BUFFER_SIZE = 16 * 1024;

    public static final String FILE_RESOURCE_FOLDER_NAME = "fileResource";// 存放资源的文件夹

    public static final String MEMBER_PIC_FOLDER = "member";

    public static final String EXPERT_PIC_FOLDER = "expert";

    /**
     * 将文件转成字节流
     *
     * @param fileName 文件名(完整路径)
     * @return
     */
    public static byte[] getBytes(String fileName) {

        if (!(new File(fileName).exists())) {
            return null;
        }

        try {
            BufferedInputStream buf = new BufferedInputStream(
                    new FileInputStream(fileName));
            ByteArrayOutputStream outByte = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = buf.read(b, 0, 1024)) != -1) {
                outByte.write(b, 0, i);
            }
            outByte.flush();
            buf.close();
            return outByte.toByteArray();
        } catch (IOException e) {
            logger.error("Read file error! ", e);
            return null;
        }
    }

    /**
     * 将文件转成字节流
     *
     * @param folder   文件夹
     * @param fileName 文件名称
     * @return
     */
    public static byte[] getBytes(String folder, String fileName) {

        return getBytes(getFolder(folder) + fileName);
    }

    /**
     * 将字节流转成文件
     *
     * @param sourceBytes 源字节流
     * @param targetFile  目标文件夹
     */
    public static void writeBytes(byte[] sourceBytes, String targetFile) {

        try {
            int index = targetFile.lastIndexOf(File.separator);
            if (index != -1) {
                FileUtil.makedirs(targetFile.substring(0, index));
            }

            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            fileOutputStream.write(sourceBytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            logger.error("Save file error! " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建文件夹，若文件夹已存在，则不创建
     *
     * @param path 文件夹路径
     */
    public static void makedirs(String path) {

        if (StringUtil.isNotEmpty(File.separator)) {
            File dir = new File(path);
            dir.setExecutable(true);//设置可执行权限
            dir.setReadable(true);//设置可读权限
            dir.setWritable(true);//设置可写权限
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    /**
     * 将字节流转成文件
     *
     * @param sourceBytes  源字节流
     * @param targetFolder 目标文件夹
     * @param targetFile   目标文件名称
     */
    public static void writeBytes(byte[] sourceBytes, String targetFolder,
                                  String targetFile) {

        writeBytes(sourceBytes, getFolder(targetFolder) + targetFile);
    }

    private static String getFolder(String folder) {

        if (StringUtil.isEmpty(folder)) {
            folder = File.separator;
        }

        if (!File.separator.equals(folder.substring(folder.length() - 1))) {
            folder = folder + File.separator;
        }
        return folder;
    }

    /**
     * 读取roperties文件
     *
     * @param fileName 文件名 eg:D:/temp.properties
     * @return
     */
    public static Properties getProperties(String fileName) {

        Properties result = new Properties();// 属性集合对象
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            result.load(fis);// 将属性文件流装载到Properties对象中

        } catch (Exception e) {
            logger.error("Save file error! " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                logger.error("Save file error! " + e.getMessage());
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 保存properties 文件
     *
     * @param savedProp  需要保存的properties文件
     * @param targetFile 保存文件名 eg:D:/temp.properties
     */
    public static void writeProperties(Properties savedProp, String targetFile) {

        writeProperties(savedProp, null, targetFile);
    }

    /**
     * 保存properties文件
     *
     * @param savedProp  需要保存的properties文件
     * @param comments   properties文件注释
     * @param targetFile 保存文件名 eg:D:/temp.properties
     */
    public static void writeProperties(Properties savedProp, String comments,
                                       String targetFile) {

        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(targetFile);
            savedProp.store(fout, comments);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param pathname 文件名（包括路径）
     */
    public static void deleteFile(String pathname) {

        File file = new File(pathname);
        if (file.isFile() && file.exists()) {
            file.delete();
        } else {
            logger.error("File[" + pathname + "] not exists!");
        }

    }

    /**
     * 将二进制转保存成临时文件
     *
     * @param dirStr   路径
     * @param fileName 文件名称
     * @param bytes    二进制数组
     * @return
     */
    public static boolean saveTempFile(String dirStr, String fileName,
                                       byte[] bytes) {

        File dirFile = new File(dirStr);
        dirFile.setExecutable(true);//设置可执行权限
        dirFile.setReadable(true);//设置可读权限
        dirFile.setWritable(true);//设置可写权限
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File saveFile = new File(dirStr + getFileSeparator() + fileName);
        if (saveFile.exists()) {
            saveFile.delete();
        }
        try {
            FileOutputStream os = new FileOutputStream(dirStr
                    + getFileSeparator() + fileName);
            os.write(bytes);
            os.flush();
            os.close();
            return true;
        } catch (IOException e) {
            logger.error("IOException", e);
        }

        return false;
    }

    /**
     * 文件拷贝
     *
     * @param src 源文件
     * @param dst 目标文件
     * @throws Exception
     */
    public static void copy(File src, File dst) throws Exception {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src),
                        BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst),
                        BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *   * Copy InputStream to File.
     */
    public static void copy(InputStream inputStream, File output) throws IOException {
        FileOutputStream fos = new FileOutputStream(output);
        IOUtils.copy(inputStream, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return
     */
    public static String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }

    /**
     * 获取文件分隔符
     *
     * @return
     */
    public static String getFileSeparator() {
        return File.separator;
    }

    /**
     * 获取相对路径
     *
     * @param params 按参数先后位置得到相对路径
     * @return
     */
    public static String getRelativePath(String... params) {

        if (null != params) {
            String path = "";
            for (String str : params) {
                path = path + getFileSeparator() + str;
            }

            return path;
        }

        return null;
    }

    /**
     * 把一个字符串写到指定文件中
     *
     * @param str
     * @param path
     * @param fileName
     */
    public static void writeStringToFile(String str, String path, String fileName) {
        try {
            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File file = new File(path + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            fw.write(str);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            logger.error("load in file error");
        }
    }

    public static String readStringFromFile(String path, String fileName) {
        StringBuffer fileInString = null;
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            return null;
        }
        File file = new File(path + fileName);
        if (!file.exists()) {
            return null;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = "";
            while ((str = br.readLine()) != null) {
                fileInString = fileInString.append(str);
            }
        } catch (Exception e) {
            logger.error("read file error");
            return null;
        }
        return fileInString.toString();
    }

    public static void changeFolderPermission(File dirFile) throws IOException {
        Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        try {
            Path path = Paths.get(dirFile.getAbsolutePath());
            Files.setPosixFilePermissions(path, perms);
        } catch (Exception e) {
            logger.info("Change folder " + dirFile.getAbsolutePath() + " permission failed.", e);
        }
    }

    public static void deleteTmpfile(File tmpFile) {
        if (tmpFile != null) {
            try {
                boolean deleted = tmpFile.delete();
                if (!deleted) {
                    MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(),
                            "getTmpDir", "Failed to delete temp file");
                }
            } catch (Exception x) {
                MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(),
                        "getTmpDir", "Failed to delete temporary file", x);
            }
        }
    }

    public static boolean convertAudioToMp3(File source, File target) {

        ConvertProgressListener listener = new ConvertProgressListener();

        boolean succeeded = true;

        try {
            // Audio Attributes/音频编码属性
            AudioAttributes audio = new AudioAttributes();
            /*
             * 它设置将用于音频流转码的编解码器的名称。您必须从当前Encoder实例的getAudioEncoders（）方法返回的列表中选择一个值。否则，
             * 您可以传递AudioAttributes.DIRECT_STREAM_COPY特殊值，该值需要源文件中原始音频流的副本。
             */
            audio.setCodec("libmp3lame");
            /*
             * 它设置新重新编码的音频流的比特率值。如果未设置比特率值，编码器将选择默认值。该值应以每秒位数表示。例如，如果你想要128 kb /
             * s比特率，你应该调用setBitRate（new Integer（128000））。
             */
            audio.setBitRate(128000);
            /* 它设置将在重新编码的音频流中使用的音频通道的数量（1 =单声道，2 =立体声）。如果未设置通道值，编码器将选择默认值。 */
            audio.setChannels(2);
            /*
             * 它设置新重新编码的音频流的采样率。如果未设置采样率值，编码器将选择默认值。该值应以赫兹表示。例如，如果您想要类似CD的44100
             * Hz采样率，则应调用setSamplingRate（new Integer（44100））。
             */
            audio.setSamplingRate(44100);
            /* 可以调用此方法来改变音频流的音量。值256表示没有音量变化。因此，小于256的值是音量减小，而大于256的值将增加音频流的音量。 */
            audio.setVolume(new Integer(256));

            // Encoding attributes/编码属性
            EncodingAttributes attrs = new EncodingAttributes();
            /*
             * 它设置将用于新编码文件的流容器的格式。给定参数表示格式名称。
             * 编码格式名称有效且仅在它出现在正在使用的Encoder实例的getSupportedEncodingFormats（）方法返回的列表中时才受支持。
             */
            attrs.setFormat("mp3");
            /* 它设置音频编码属性。如果从未调用过新的EncodingAttributes实例，或者给定参数为null，则编码文件中不会包含任何音频流 */
            attrs.setAudioAttributes(audio);
            /*
             * 它为转码操作设置偏移量。源文件将从其开始的偏移秒开始重新编码。例如，如果您想剪切源文件的前五秒，
             * 则应在传递给编码器的EncodingAttributes对象上调用setOffset（5）。
             */
            // attrs.setOffset(5F);
            /*
             * 它设置转码操作的持续时间。只有源的持续时间秒才会在目标文件中重新编码。例如，如果您想从源中提取和转码30秒的一部分，
             * 则应在传递给编码器的EncodingAttributes对象上调用setDuration（30）
             */
            // attrs.setDuration(30F);

            // Encode/编码
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs, listener);

        } catch (Exception ex) {
            ex.printStackTrace();
            succeeded = false;
        }
        return succeeded;
    }


    public static class ConvertProgressListener implements EncoderProgressListener {

        //编码器在分析源文件后调用此方法。该信息参数是实例 ws.schild.jave.MultimediaInfo类，它代表了有关源音频和视频流及其容器的信息


        public void sourceInfo(MultimediaInfo info) {
            // TODO Auto-generated method stub

        }
        //每次完成编码操作的进度时，编码器调用该方法。所述permil参数是表示通过当前操作到达点的值和它的范围是从0（操作刚开始）到1000（操作完成）
        public void progress(int permil) {
            double progress = permil / 1000.00;
            System.out.println(progress);

        }

        public void message(String message) {

        }
    }
}