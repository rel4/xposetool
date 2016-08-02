package com.meiriq.xposehook.tutorial;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by tian on 15-11-26.
 */
public class Shell {

    public static String execCommand(String command,boolean isSu){

        DataOutputStream dos = null;
        Process process = null;
        DataInputStream dis = null;
        String str = command;
        String result = "";
        if(isSu)
            str = "su";

        try {
            if(isSu){
                process = Runtime.getRuntime().exec(str);
                dos = new DataOutputStream(process.getOutputStream());
                dis = new DataInputStream(process.getInputStream());
            }
            if(isSu){
                dos.writeBytes(command + "\n");
                dos.flush();
            }

            String line = null;
//            while((line = dis.readLine())!= null)
//                result += line;


//            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(dos != null){
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(dis != null){
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String getRoot(){
        return execCommand("chmod 777 dev/input/event*",true);
    }
}
