package com.poi.test.utils;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/1/14 16:38
 * @Version 1.0
 */
public class PoiTest {
    public static void main(String[] args) {
        WordUtils wordUtil=new WordUtils();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("${position}", "java开发");
        params.put("${name}", "焦糖橙子");
        params.put("${sex}", "男");
        params.put("${eMail}", "8888@csdn.com");

        try{
            Map<String,Object> jpeg1 = new HashMap<String, Object>();
            jpeg1.put("width", 100);
            jpeg1.put("height", 150);
            jpeg1.put("type", "jpg");
            jpeg1.put("content", WordUtils.inputStream2ByteArray(new FileInputStream("C:\\Users\\Dell\\Desktop\\p252662223.jpg"), true));
            params.put("${jpeg1}",jpeg1);
            Map<String,Object>jpeg2 = new HashMap<String, Object>();
            jpeg2.put("width", 100);
            jpeg2.put("height", 150);
            jpeg2.put("type", "jpg");
            jpeg2.put("content", WordUtils.inputStream2ByteArray(new FileInputStream("C:\\Users\\Dell\\Desktop\\p252662223.jpg"), true));
            params.put("${jpeg2}",jpeg2);
            //模板文件位置
            String path="D:\\myword.docx";
            //生成文件位置
            String fileName= new String("D:\\create.docx".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            wordUtil.getWord(path,params,fileName);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
