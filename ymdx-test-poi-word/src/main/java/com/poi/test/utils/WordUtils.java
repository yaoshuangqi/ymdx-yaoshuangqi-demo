package com.poi.test.utils;
import org.apache.poi.xwpf.usermodel.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: SONGTIANk
 * @Description: Word替换字符和插入图片工具类
 * @Date: 2020/11/11 17:16
 * @Version: 1.0
 */
public class WordUtils {

    /**
     * 根据模板生成word
     * @param path     模板的路径
     * @param params   需要替换的参数
     * @param fileName 生成word文件的文件名
     * @tips 此处为了方便而将文件生成在了本地，
     * 你也可以直接将文件放入response 响应头中，调用浏览器的下载器直接下载到本地，
     * 这样更符合业务逻辑，即：数据库或者页面输入数据到word模板中，自动生成word并下载；
     * 也可使用插入图片，替换需要在业务中插入的电子签名，或者盖章的word文档。
     * 当然了，此处的传参就需要多加一个 HttpServletResponse response
     * 文件名写中文会有乱码，具体如何调用浏览器下载和解决乱码问题可参考我的另一篇
     * [简便的Excel导出功能](https://blog.csdn.net/weixin_43238452/article/details/108790379)的解决办法。
     */
    public void getWord(String path, Map<String, Object> params,String fileName) throws Exception {
        File file = new File(path);
        //创建输入流
        InputStream is = new FileInputStream(file);
        //创建doc对象
        CustomXWPFDocument doc = new CustomXWPFDocument(is);
        //替换文本里面的变量
        this.replaceInPara(doc, params);
        //输出流写入文件
        FileOutputStream out = new FileOutputStream(fileName);
        doc.write(out);
        //关闭输入输出流
        this.close(is);
        out.close();
        System.out.println("<-----生成结束----->");
    }

    /**
     * 替换段落里面的变量
     * @param doc    要替换的文档
     * @param params 参数
     */
    private void replaceInPara(CustomXWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params, doc);
        }
    }

    /**
     * 替换段落里面的变量
     * @param para   要替换的段落
     * @param params 参数
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params, CustomXWPFDocument doc) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            int start = -1;
            int end = -1;
            String str = "";
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
                    start = i;
                }
                if ((start != -1)) {
                    str += runText;
                }
                if ('}' == runText.charAt(runText.length() - 1)) {
                    if (start != -1) {
                        end = i;
                        break;
                    }
                }
            }

            for (int i = start; i <= end; i++) {
                para.removeRun(i);
                i--;
                end--;
            }

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                if (str.indexOf(key) != -1) {
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        str = str.replace(key, value.toString());
                        para.createRun().setText(str, 0);
                        break;
                    } else if (value instanceof Map) {
                        str = str.replace(key, "");
                        Map pic = (Map) value;
                        int width = Integer.parseInt(pic.get("width").toString());
                        int height = Integer.parseInt(pic.get("height").toString());
                        int picType = getPictureType(pic.get("type").toString());
                        byte[] byteArray = (byte[]) pic.get("content");
                        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                        try {
                            //int ind = doc.addPicture(byteInputStream,picType);
                            //doc.createPicture(ind, width , height,para);
                            doc.addPictureData(byteInputStream, picType);
                            doc.createPicture(doc.getAllPictures().size() - 1, width, height, para);
                            para.createRun().setText(str, 0);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }
    /**
     * 根据图片类型，取得对应的图片类型代码
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * 将输入流中的数据写入字节数组
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    e2.getStackTrace();
                }
            }
        }
        return byteArray;
    }
    /**
     * 关闭输入流
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
