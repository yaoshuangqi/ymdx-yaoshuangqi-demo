package com.ymdx.pay.demo.reconciliation;


import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReadConfig;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/2/7
 * @Description
 */
public class ReadAndWriterCsvFlie {

    // 需要写入的 csv 文件路径
    public static final String WRITE_CSV_FILE_PATH1 = "C:\\Users\\Administrator\\Desktop\\ttrty.csv";

    /**
     * 读取 csv 文件
     */
    public static void readCsvFile(String readCsvFilePath) {
        // 缓存读取的数据
        List<String[]> content = new ArrayList<>();

        try {
            // 创建 CSV Reader 对象, 参数说明（读取的文件路径，分隔符，编码格式)
            CsvReader csvReader = new CsvReader(new File(readCsvFilePath).toPath(),Charset.forName("GBK"), CsvReadConfig.defaultConfig());
            // 跳过表头
            //csvReader.readHeaders();

            CsvData read = csvReader.read();
            List<CsvRow> readRows = read.getRows();
            for (int row = 0; row < readRows.size(); row++) {
                // 读取第 row 行，第 0 列的数据
                CsvRow  strings= readRows.get(row);
                System.out.println("==> orderNum: " + strings.toString());
            }

            /*// 读取除表头外的内容
            while (csvReader.readRecord()) {
                // 读取一整行
                String line = csvReader.getRawRecord();
                System.out.println(line);

                content.add(csvReader.getValues());
            }
            csvReader.close();

            for (int row = 0; row < content.size(); row++) {
                // 读取第 row 行，第 0 列的数据
                String orderNum = content.get(row)[0];
                System.out.println("==> orderNum: " + orderNum);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 需要写入的 csv 文件路径
    public static final String WRITE_CSV_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\write_test.csv";

    /**
     * 生成 csv 文件
     */
    /*public static void writeCsvFile(String writeCsvFilePath) {
        // 创建 CSV Writer 对象, 参数说明（写入的文件路径，分隔符，编码格式)
        CsvWriter csvWriter = new CsvWriter(writeCsvFilePath,',', Charset.forName("GBK"));

        try {
            // 定义 header 头
            String[] headers = {"订单号", "用户名", "支付金额"};
            // 写入 header 头
            csvWriter.writeRecord(headers);

            // 写入一千条记录
            for (int i = 0; i < 1000; i++) {
                String orderNum = UUID.randomUUID().toString();
                String userName = "用户" + i;
                String payMoney = String.valueOf(i);

                // 写入行
                csvWriter.writeRecord((String[]) Arrays.asList(orderNum, userName, payMoney).toArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            csvWriter.close();
        }
    }*/


    //读取csv文件，提取有效数据
    private void readCsvAndPackageData(){

    }

    public static void main(String[] args) throws IOException {
        //readCsvFile(WRITE_CSV_FILE_PATH1);

        //获取csv文件中的数据，并转换成bean类，用于解析对账文件
        List<AccountWaterBean> csvData = CsvUtil.getCsvData(WRITE_CSV_FILE_PATH1, AccountWaterBean.class);
        for (AccountWaterBean csvDatum : csvData) {
            System.out.println("==>> "+csvDatum.toString());
        }
        String createFileTxt = "C:\\Users\\Administrator\\Desktop\\evdokey_201103221556.txt";

        ArrayList<String> objects = new ArrayList<>();
        objects.add("张三&&23&&福建");
        objects.add("lisi&&30&&上海");
        objects.add("lisi&&30&&上海");
        objects.add("wangwu&&43&&北京");
        //将数据封装成指定格式，并放入list集合中即可

        CsvUtil.writeReconFileToGongYong(objects, createFileTxt);
    }
}
