package com.poi.test.utils;

import org.apache.poi.xwpf.usermodel.*;

import javax.print.Doc;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ExportDocxUtils {
    public static void main(String[] args) throws Exception {
        //需要进行文本替换的信息
        Map<String, Object> data = new HashMap<>();
        //图片，如果是多个图片，就新建多个map
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("width", 100);
        header.put("height", 150);
        header.put("type", "jpg");
        header.put("content", inputStream2ByteArray(new FileInputStream("C:\\Users\\Dell\\Desktop\\p252662223.jpg"), true));
        header.put("title", "张三");
        data.put("${picture01}", header);
        Map<String, Object> header2 = new HashMap<String, Object>();
        header2.put("width", 100);
        header2.put("height", 150);
        header2.put("type", "jpg");
        header2.put("content", inputStream2ByteArray(new FileInputStream("C:\\Users\\Dell\\Desktop\\p252662223.jpg"), true));
        header2.put("title", "李四");
        data.put("${picture02}", header2);

        data.put("${name01}", "01.张三");
        data.put("${name02}", "02.李四");

        String path = "d:/template.docx";  //模板文件位置
        String fileName = "测试文档.docx";    //生成word文件的文件名
        exportWord(path, data, fileName);
    }
    /**
     * 根据模板生成word
     *
     * @param path      模板的路径
     * @param tableList 需要插入的参数
     * @param fileName  生成word文件的文件名
     */
    public static void exportWord(String path, Map<String, Object> data, String fileName) {
        InputStream inputStream = null;
        CustomXWPFDocument doc = null;
        try {
            //模板文件
            File file = new File(path);
            inputStream = new FileInputStream(file);
            //将模板文件流转换为document
            doc = new CustomXWPFDocument(inputStream);
            //得到文档中表格迭代器
            Iterator<XWPFTable> iterator = doc.getTablesIterator();
            //将图片插入表格中
            XWPFTable table;


            while (iterator.hasNext()) {
                table = iterator.next();
                //获取模板中行
                if (table.getRows().size() > 0) {
                    //1.创建行,根据需要插入的数据添加新行，不算表头
                    if(2 > table.getRow(0).getTableCells().size()){
                        for (int i = 0; i < 2 - 1; i++) {
                            table.createRow();
                        }
                    }
                    //遍历表格插入数据
                    List<XWPFTableRow> rows = table.getRows();
                    int length = rows.size();
                    int picIndex = 1;
                    for (int i = 0; i < length; i++) {
                        XWPFTableRow newRow = rows.get(i);
                        List<XWPFTableCell> cells = newRow.getTableCells();
                        for (int j = 0; j < cells.size(); j++) {
                            //每一个单元格
                            XWPFTableCell cell = cells.get(j);
                            //假设有两张图片
                            int length1 = 2;
                            if(j >= length1){
                                break;
                            }

                            //像每个单元格插入数据占位符
                            /**
                             * ${picture01}
                             * ${name01}
                             * ${cardNo01}
                             */
                            cell.setText("${picture"+String.format("%02d",picIndex)+"}");
                            cell.setText("${name"+String.format("%02d",picIndex)+"}");
                            //cell.setText("${cardNo"+String.format("%02d",picIndex)+"}");
                            //设置单元格垂直居中
                            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                            picIndex++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //创建临时的模板变量文件
            File file = File.createTempFile("pattern_temp", ".docx");
            System.out.println("临时文件创建成功="+file.getCanonicalPath());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            doc.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            close(inputStream);



            OutputStream outputsStream = null;
            InputStream inputStream1 = null;
            try {
                inputStream1 = new FileInputStream(file);
                //重新构建doc，将模板文件流转换为document
                CustomXWPFDocument doc1 = new CustomXWPFDocument(inputStream1);
                //填充数据
                replaceInTable(doc1, data, new ArrayList<>());

                File file1 = new File("d:/纯表格导入图片.docx");
                outputsStream = new FileOutputStream(file1);
                doc1.write(outputsStream);
                outputsStream.flush();
                outputsStream.close();
            } catch (Exception e) {
                System.out.println("导出文件{}失败"+fileName);
            } finally {
                close(outputsStream);
                close(inputStream1);
                //删除临时文件
                boolean deleteIfExists = Files.deleteIfExists(file.toPath());
                System.out.println("临时文件是否删除成功："+deleteIfExists);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params, CustomXWPFDocument doc) {
        List<XWPFRun> runs;
        Matcher matcher;
        System.out.println("==================>>>>> 替换变量" + para.getParagraphText());
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            int initCount = runs.size();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String key = run.toString();
                System.out.println("==>>runText = " + key);

                 if(params.containsKey(key)){
                    Object value = params.get(key);
                    if (value instanceof String) {
                        XWPFRun paraRun = para.createRun();
                        paraRun.setText(value.toString(), 0);
                    } else if (value instanceof Map) {
                        Map<String, Object> pic = (Map<String, Object>) value;
                        int width = Integer.parseInt(pic.get("width").toString());
                        int height = Integer.parseInt(pic.get("height").toString());
                        int picType = getPictureType(pic.get("type").toString());
                        byte[] byteArray = (byte[]) pic.get("content");
                        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                        try {
                            doc.addPictureData(byteInputStream, picType);
                            doc.createPicture(doc.getAllPictures().size() - 1, width, height, para);
                            XWPFRun paraRun = para.createRun();
                            paraRun.addBreak();//换行
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            for (int i = 0; i < initCount; i++) {
                para.removeRun(0);
            }
        }
    }


    /**
     * 为表格插入数据，行数不够添加新行
     *
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     */
    private static void insertTable(XWPFTable table, List<String[]> tableList) {
        //创建行,根据需要插入的数据添加新行，不处理表头
        for (int i = 0; i < tableList.size() - 1; i++) {
            XWPFTableRow row = table.createRow();
        }
        //遍历表格插入数据
        List<XWPFTableRow> rows = table.getRows();
        int length = table.getRows().size();
        for (int i = 0; i < length; i++) {
            XWPFTableRow newRow = table.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                String s = tableList.get(i)[j];
                cell.setText(s);
            }
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private static void replaceInTable(CustomXWPFDocument doc, Map<String, Object> params, List<String[]> tableList) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            if (table.getRows().size() > 0) {
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if (matcher(table.getText()).find()) {
                    rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            paras = cell.getParagraphs();
                            for (XWPFParagraph para : paras) {
                                replaceInPara(para, params, doc);
                            }
                        }
                    }
                } else {
                    insertTable(table, tableList);  //插入数据
                }
            }
        }
    }


    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }


    /**
     * 根据图片类型，取得对应的图片类型代码
     *
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
     *
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
     *
     * @param is
     */
    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    private static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}