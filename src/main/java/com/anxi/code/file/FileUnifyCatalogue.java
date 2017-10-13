package com.anxi.code.file;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileUnifyCatalogue {

    private final static File formCatalogue = new File("H:\\迅雷下载");

    private final static File toCatalogue = new File("F:\\asdf");

    private final static BigDecimal mbSize = new BigDecimal(1024 * 1024);

    private final static Long minFileSize = mbSize.multiply(new BigDecimal(4)).longValue();

    private static BigDecimal fileTotalByteSize = BigDecimal.ZERO;

    private static int fileNum = 0;

    private static long beginTime;

    private static long endTime;

    private static BigDecimal msNum = new BigDecimal(1000);

    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] s) throws InterruptedException, IOException {
        beginTime = System.currentTimeMillis();
        fileMove(formCatalogue);
        endTime = System.currentTimeMillis();
        //statisticsOutput();
    }

    private static void fileMove(File catalogueFile) throws InterruptedException, IOException {
        File[] files = catalogueFile.listFiles();
        for (final File file : files) {
            if (file.isDirectory()) {
                fileMove(new File(file.getPath()));
            } else {
                final long fileSize = file.length();
                if (!toCatalogue.getPath().equals(file.getParent()) && fileSize > minFileSize) {
                    /*Files.move(file, new File(toCatalogue.getPath() + File.separator + file.getName()));
                    fileNum++;
                    fileTotalByteSize = fileTotalByteSize.add(new BigDecimal(fileSize));*/
                    fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("剪切文件[" + file.getName() + "]开始");
                                long beginMs = System.currentTimeMillis();
                                Files.move(file, new File(toCatalogue.getPath() + File.separator + file.getName()));
                                long endMs = System.currentTimeMillis();
                                System.out.println("剪切文件[" + file.getName() + "]结束，耗时：" + new BigDecimal(endMs - beginMs).divide(msNum) + "秒");
                                fileNum++;
                                fileTotalByteSize = fileTotalByteSize.add(new BigDecimal(fileSize));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    private static void statisticsOutput() {
        System.out.println("文件总数量：" + fileNum);
        System.out.println("文件总大小：" + fileTotalByteSize.divide(mbSize) + " MB");
        System.out.println("总用时：" + new BigDecimal(endTime - beginTime).divide(msNum) + "秒");
    }

}
