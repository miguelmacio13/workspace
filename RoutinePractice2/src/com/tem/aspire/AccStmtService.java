package com.tem.aspire;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.temenos.t24.api.complex.eb.servicehook.ServiceData;
import com.temenos.t24.api.hook.system.ServiceLifecycle;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class AccStmtService extends ServiceLifecycle {


    @Override
    public void processSingleThreaded(ServiceData serviceData) {
        String sourcepath = "D:/R22_ALL/bnk/UD/&HOLD&";
  
        File directory = new File(sourcepath);
        File[] files = directory.listFiles(File::isFile);
        File recentFile = null;
        
        long lastModifiedTime = Long.MIN_VALUE;

        if(files != null){
            for(File file : files){
                if (file.lastModified() > lastModifiedTime){
                    recentFile = file;
                    lastModifiedTime = file.lastModified();
                    
                }
            }
        }
        
        try {
            Path source = Paths.get(recentFile.toString());
            String fileName = source.getFileName().toString();
            
            Path destination = Paths.get("D:/R22_ALL/TAFJ/ext" + fileName);
            
            Files.copy(source, destination);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
