/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canadafixer;

import java.io.File;

/**
 *
 * @author ltoscano
 */
public class FileChange {
    
    private String filepath;
    private File newFile;
    
    //**************************************************************************
    
    public FileChange(String filepath, String newFileUrl){
    
        this.filepath = filepath;
        this.newFile = new File(newFileUrl);
    }
    
    //**************************************************************************

    public String getFilepath() {
        return filepath;
    }

    public File getNewFile() {
        return newFile;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setNewFile(File newFile) {
        this.newFile = newFile;
    }
   
    //**************************************************************************
    
}
