package com.dfs.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * @author taoxy 2019/1/3
 */
public class RunPyUtil {

    public static String runCmd(String cmd) throws Exception {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}