/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canadafixer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import zipactions.ZipEditor;

/**
 *
 * @author ltoscano
 */
public class CanadaFixer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<FileChange> changes = new ArrayList<>();
        final String CHANGE_DIR = "change_files/";

        final String PDF = "assets/sena/downloads/accessibility.pdf";
        final String NEW_PDF = CHANGE_DIR + "new_accessibility.pdf";
        
        final String HELP = "assets/sena/common/localization/eng/localize.json";
        final String NEW_HELP = CHANGE_DIR + "new_localize.json";
        
        final String INFO = "assets/sena/icons/info_logo.png";
        final String NEW_INFO = CHANGE_DIR + "new_info_logo.png";
        
        final String INDEX = "index.html";
        final String NEW_INDEX = CHANGE_DIR + "new_index.html";

        final String OBJECTS_DIR = System.getProperty("user.dir").concat(File.separator).concat("objects");

        changes.add(new FileChange(PDF, NEW_PDF));
        //changes.add(new FileChange(HELP, NEW_HELP));
        changes.add(new FileChange(INFO, NEW_INFO));
        changes.add(new FileChange(INDEX, NEW_INDEX));

        File folder = new File(OBJECTS_DIR);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (!file.isDirectory()) {
                String fname = file.getName().substring(0, file.getName().lastIndexOf('.'));
                ZipEditor zipper = new ZipEditor(fname, changes);
                try {
                    zipper.WriteFinish();
                } catch (TransformerException | IOException ex) {
                    System.out.println("error");
                    System.out.println(ex.toString());
                }
            }
        }
    }
}
