package com.ymdx.pay.demo.reconciliation;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/2/20
 * @Description
 */
@Slf4j
public class CsvUtil {

    /**
     * 解析csv文件并转成bean
     * @param fileUrl csv文件
     * @param clazz 类
     * @param <T> 泛型
     * @return 泛型bean集合
     */
    public static <T> List<T> getCsvData(String fileUrl, Class<T> clazz) {
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(new FileInputStream(fileUrl), "GBK");
        } catch (Exception e) {
            log.error("读取csv文件失败！");
        }
        //列名映射策略
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }

    /**
     * @author YaoShuangQi
     * @create 2021/2/20 15:27
     * @version 1.0.0
     * @Description 将对账文件写入到txt文件。根据指定格式（具体见译码行文档）
     */
    public static void writeReconFileToGongYong(List<String> dataStr, String createFileTxt) throws IOException {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            fos=new FileOutputStream(new File(createFileTxt));
            osw = new OutputStreamWriter(fos, "UTF-8");
            try(BufferedWriter bw = new BufferedWriter(osw)) {
                for(String arr:dataStr){
                    //行写入
                    bw.write(arr+"\r\n");
                }
            }
        }finally {
            if(osw != null){
                osw.close();
                fos.close();
            }
        }
    }
}
