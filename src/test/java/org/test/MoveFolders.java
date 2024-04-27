package org.test;

import java.io.*;

public class MoveFolders {
    public static void main(String[] args) {
        String sourcePath = "D:\\BaiduNetdiskDownload\\Bomi--【解压密码获取：www.0890.xyz】";
        String destinationPath = "D:\\BaiduNetdiskDownload\\bomi";

        File sourceFolder = new File(sourcePath);
        File[] sourceSubFolders = sourceFolder.listFiles(File::isDirectory);

        for (File subFolder : sourceSubFolders) {
            File[] subFolderContents = subFolder.listFiles();
            boolean isSecondLevel = true;

            for (File content : subFolderContents) {
                if (content.isDirectory()) {
                    isSecondLevel = false;
                    break;
                }
            }

            if (isSecondLevel) {
                String subFolderName = subFolder.getName();
                File destinationFolder = new File(destinationPath, subFolderName);

                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir();
                }

                try {
                    moveFolder(subFolder, destinationFolder);
                    System.out.println("Moved folder " + subFolderName + " successfully.");
                } catch (IOException e) {
                    System.out.println("Failed to move folder " + subFolderName + ".");
                    e.printStackTrace();
                }
            }
        }
    }

    private static void moveFolder(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdir();
            }

            String[] contents = source.list();

            for (String content : contents) {
                File sourceContent = new File(source, content);
                File destinationContent = new File(destination, content);
                moveFolder(sourceContent, destinationContent);
            }

            source.delete();
        } else {
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();

            source.delete();
        }
    }
}
