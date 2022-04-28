package com.qxx.thirdservice.util;

import com.aspose.words.License;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Created by tyq on 2017/11/14.
 * <p>
 */
@Slf4j
public class AsposeLicenseUtil {

    /**
     * 获取license
     *
     * @return
     */
    public static boolean getLicense() {
        try (InputStream inputStream = AsposeLicenseUtil.class.getClassLoader().getResourceAsStream("Aspose.Words.lic")) {
            License license = new License();
            license.setLicense(inputStream);
            return true;
        } catch (Exception e) {
            log.error("读取 aspose.words license 时发生错误.", e);
        }
        return false;
    }

    public static void main(String[] args) {

        String[] arg = {"/Users/qxx/miniforge3/bin/python", "/Users/qxx/PythonProject/pythonProject2/snow_nlp.py"};
        callPythonPrintLog(arg);
    }

    public static void callPythonPrintLog(String[] args1){
        BufferedReader in = null;
        try {
            Process proc = Runtime.getRuntime().exec(args1);// 执行py文件

            in = new BufferedReader(new InputStreamReader(proc.getInputStream())); // 输入流获取执行结果
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            proc.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getAbsolutePath(InputStream stream, String path){
        try {
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);

            File targetFile = new File(path);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            return targetFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
