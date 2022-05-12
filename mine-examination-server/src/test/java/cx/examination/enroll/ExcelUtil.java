package cx.examination.enroll;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.RtfWriter2;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/5/12 16:16
 * @Version 1.0
 */
@Slf4j
public class ExcelUtil {

    /**
     * 默认从第一行开始读取表格
     * @param path
     * @param aimClass
     * @param <T>
     * @return
     */
    public static <T> List<T> parseFromExcel(String path, Class<T> aimClass) {
        return parseFromExcel(path, 0, aimClass);
    }

    /**
     * 将xls中数据转换成bean对象
     * @param path
     * @param firstIndex
     * @param aimClass
     * @param <T>
     * @return
     */
    public static <T> List<T> parseFromExcel(String path, int firstIndex, Class<T> aimClass) {
        List<T> result = new ArrayList<T>();
        try {
            FileInputStream fis = new FileInputStream(path);
            Workbook workbook = WorkbookFactory.create(fis);
            //对excel文档的第一页,即sheet1进行操作
            Sheet sheet = workbook.getSheetAt(0);
            int lastRaw = sheet.getLastRowNum();
            for (int i = firstIndex; i < lastRaw; i++) {
                //第i行
                Row row = sheet.getRow(i);
                if(StrUtil.isBlank(row.getCell(0).getStringCellValue())){
                    continue;
                }
                T parseObject = aimClass.newInstance();
                Field[] fields = aimClass.getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    //第j列
                    Cell cell = row.getCell(j);
                    Hyperlink hyperlink = cell.getHyperlink();
                    if(hyperlink != null){
                        String address = hyperlink.getAddress();
                        field.set(parseObject, address);
                        continue;
                    }
                    if (type.equals(String.class)) {
                        field.set(parseObject, cell.getStringCellValue());
                    } else if (type.equals(Date.class)) {
                        field.set(parseObject, cell.getDateCellValue());
                    }
                }
                result.add(parseObject);
            }
            fis.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occured when parsing object from Excel. at " + ExcelUtil.class);
        }
        return result;
    }

    /**
     * 将图片写入到word中
     */
    public static void pictureToWord(String directoryPath, String createImgDocPath){
        try {
            // 创建word文档,并设置纸张的大小
            Document doc = new Document(PageSize.A4);
            /**
             * 建立一个书写器与document对象关联,通过书写器可以将文档写入到输出流中
             */
            RtfWriter2.getInstance(doc, new FileOutputStream(createImgDocPath));
            doc.open();

            //从指定文件夹中获取所有图片
            File file = new File(directoryPath);
            if(!file.isDirectory()){
                log.error("不是图片文件夹，请检查, directoryPath:{}", directoryPath);
                return;
            }
            String[] fileNameList = file.list();
            for (String picName : fileNameList) {
                String picPath = directoryPath.concat(File.separator).concat(picName);
                Image png = Image.getInstance(picPath);
                doc.add(png);
                Paragraph p1 = new Paragraph("\r\n");
                doc.add(p1);
            }
            doc.close();
            log.info("图片写入word成功：{}", createImgDocPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        //参数里的1表示有效行数从第2行开始
        List<ExamineModel> studentInfos = ExcelUtil.parseFromExcel("C:\\Users\\Dell\\Desktop\\考试\\测试.xls", 1, ExamineModel.class);
       log.info(JSONUtil.toJsonStr(studentInfos));

       //picture写入图片
        pictureToWord("F:\\image\\faces", "F:\\image\\img2doc.doc");
    }
}

