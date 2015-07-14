/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zipactions;

import canadafixer.FileChange;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.xml.transform.TransformerException;

/**
 *
 * @author ltoscano
 */
public class ZipEditor {

    private static final String NEW_PREFIX = "New" + File.separator;
    private static final String ROOT_DIR = System.getProperty("user.dir");
    private static final String ZIP_DIR = "objects";

    String zipPath;
    String zipName;
    List<FileChange> changes;

    public ZipEditor(String zipName, List<FileChange> changes) {
        this.zipName = zipName;
        this.zipPath = ROOT_DIR.concat(File.separator).concat(ZIP_DIR).concat(File.separator).concat(zipName).concat(".zip");
        this.changes = changes;
    }

    public void WriteFinish() throws IOException, TransformerException {
        File regularZipFile = new File(zipPath);
        ZipFile zipFile = new ZipFile(zipPath);

        String absolutePath = regularZipFile.getAbsolutePath();
        String filepath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));

        String strNewFile = filepath.concat(File.separator).concat(NEW_PREFIX).concat(regularZipFile.getName());
        (new File(filepath.concat(File.separator).concat(NEW_PREFIX))).mkdirs();

        final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(strNewFile));

        for (Enumeration e = zipFile.entries(); e.hasMoreElements();) {
            ZipEntry entryIn = new ZipEntry((ZipEntry) e.nextElement());

            if (!replaceFile(entryIn.getName(), zos)) {
                ZipEntry destEntry = new ZipEntry(entryIn.getName());
                zos.putNextEntry(destEntry);

                InputStream is = zipFile.getInputStream(entryIn);
                byte[] buf = new byte[1024];
                int len;
                while ((len = (is.read(buf))) > 0) {
                    zos.write(buf, 0, len);
                }
            } 
            zos.closeEntry();
        }
        zos.close();
        zipFile.close();
    }

    private boolean replaceFile(String strCompare, ZipOutputStream zos) throws IOException {
        for (FileChange change : changes) {
            if (strCompare.equalsIgnoreCase(change.getFilepath())) {
                System.out.println("found");
                ZipEntry destEntry = new ZipEntry(strCompare);
                zos.putNextEntry(destEntry);

                InputStream is = new FileInputStream(change.getNewFile());
                byte[] buf = new byte[1024];
                int len;
                while ((len = (is.read(buf))) > 0) {
                    zos.write(buf, 0, len);
                }
                return true;
            }
        }
        return false;
    }

}
