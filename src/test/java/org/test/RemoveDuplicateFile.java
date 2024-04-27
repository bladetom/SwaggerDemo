package org.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tomtian
 * @create 2023-02-23 6:24 PM
 * @Description
 */
public class RemoveDuplicateFile {
    public static void main(String[] args) {
        String directoryPath = "E:\\file test";
        try {
            removeDuplicateFiles(directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void removeDuplicateFiles(String directoryPath) throws IOException, NoSuchAlgorithmException {
        Map<String, List<File>> fileMap = new HashMap<String, List<File>>();
        MessageDigest md = MessageDigest.getInstance("MD5");
        File directory = new File(directoryPath);
        File[] fileList = directory.listFiles();
        for (File file : fileList) {
            if (file.isFile()) {
                Path filePath = file.toPath();
                String fileHash = getFileHash(md, filePath);
                if (fileMap.containsKey(fileHash)) {
                    fileMap.get(fileHash).add(file);
                } else {
                    List<File> list = new ArrayList<File>();
                    list.add(file);
                    fileMap.put(fileHash, list);
                }
            }
        }
        for (String hash : fileMap.keySet()) {
            List<File> fileListByHash = fileMap.get(hash);
            if (fileListByHash.size() > 1) {
                for (int i = 1; i < fileListByHash.size(); i++) {
                    File fileToRemove = fileListByHash.get(i);
                    Files.delete(fileToRemove.toPath());
                }
            }
        }
    }

    public static String getFileHash(MessageDigest md, Path filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(filePath);
        md.update(fileBytes);
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void getSize(File file){
        for (File f:file.listFiles()
             ) {
            double size = (double) f.length()/1000/1000;
            f.renameTo(new File(f.getPath()+size+"MB"));
        }
    }
}
