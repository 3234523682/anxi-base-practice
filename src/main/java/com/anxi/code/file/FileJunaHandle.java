package com.anxi.code.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileJunaHandle {

    private static List<FileObj> fileList = new ArrayList<>();

    public static void main(String[] s) {
        String cataloguePath = "D:\\software\\test";
        fileGather(cataloguePath,"D:\\software\\test01");
        fileJunaHandle();
    }

    private static void fileGather(String... cataloguePaths) {
        for (String cataloguePath : cataloguePaths) {
            File catalogueFile = new File(cataloguePath);
            File[] files = catalogueFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    fileGather(file.getPath());
                } else {
                    String name = file.getName();
                    fileList.add(new FileObj(name, file.getPath(), simpleFileNameGenerate(name.substring(0, name.indexOf(".")).toLowerCase())));
                }
            }
        }
    }

    private static void fileJunaHandle() {
        for (int index = 0; index < fileList.size(); index++) {
            FileObj fileObj = fileList.get(index);
            String simpleFileName = fileObj.simpleFileName;
            List<FileObj> repetitionList = new ArrayList<>();
            Iterator<FileObj> iterator = fileList.iterator();
            int index1 = 0;
            while (iterator.hasNext()) {
                FileObj next = iterator.next();
                if (simpleFileName.equals(next.simpleFileName)) {
                    repetitionList.add(next);
                    iterator.remove();
                    if (index1 <= index) {
                        index = index1;
                    }
                }
                index1++;
            }
            if (repetitionList.size() > 1) {
                System.out.println(simpleFileName);
                for (int i = 0; i < repetitionList.size(); i++) {
                    System.out.println("    " + repetitionList.get(i).filePath);
                }
            }
        }
    }

    private static String simpleFileNameGenerate(String name) {
        char[] chars = name.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char str : chars) {
            if (isLetterDigitOrChinese(String.valueOf(str))) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    private static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";//其他需要，直接修改正则表达式就好
        return str.matches(regex);
    }


    static class FileObj {

        String fileName;

        String filePath;

        String simpleFileName;

        public FileObj(String fileName, String filePath, String simpleFileName) {
            this.fileName = fileName;
            this.filePath = filePath;
            this.simpleFileName = simpleFileName;
        }
    }

}
