package com.anxi.code.random;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RandomCreateFile {


    private static final char[] chars = {'a', 'b', 'c', 'd', 'e'};

    private static int fileSuffix = 1;

    private static int catalogSuffix = 1;

    public static void main(String[] s) throws IOException {
        catalogCreate(10, "E:\\test");
    }

    private static void catalogCreate(int forNum, String path) throws IOException {
        for (int i = 0; i < forNum; i++) {
            int fileLength = (int) (new Random().nextDouble() * 4);
            if (fileLength % 2 == 0) {
                File file = new File(path + File.separator + "catalog" + catalogSuffix++);
                file.mkdir();
                catalogCreate(--forNum, file.getPath());
            } else {
                File file = new File(path + File.separator + randomFileName(fileLength));
                file.createNewFile();
            }
        }
    }

    private static String randomFileName(int fileLength) {
        StringBuilder fileName = new StringBuilder("");
        for (int i = 0; i < fileLength; i++) {
            int randomIndex = (int) (new Random().nextDouble() * 5);
            fileName.append(chars[randomIndex]);
        }
        fileName.append("." + fileSuffix++);
        return fileName.toString();
    }

}
