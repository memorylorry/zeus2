package com.taobao.zeus.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogAppender {

    private String location = "killpid.log";

    public LogAppender setLocation(String location) {
        this.location = location;
        return this;
    }
    public LogAppender(){}
    public LogAppender(String location){
        this.location = location;
    }

    public boolean append(String message) {
        File file = new File(location);
        FileOutputStream fos = null;

        try {
            if(!file.exists()){
                file.createNewFile();
            }
            fos = new FileOutputStream(file,true);
            message = message + "\n";
            fos.write(message.getBytes());
        }catch (IOException e) {
            return false;
        }finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
            }
        }

        return true;
    }

}
