package com.xyz.mpeg2mp4;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Convert2Mp4 {
    public static void convert(String path,String out,String fileName){
        try {
          out = out + File.separator +fileName+ ".mp4";
          String[] command = {
              "ffmpeg",
              "-i", path,
              "-codec:v", "libx264",
              "-codec:a", "aac",
              "-strict", "experimental",
              "-b:a", "192k",
              "-movflags", "faststart",
              out
          };

          ProcessBuilder processBuilder = new ProcessBuilder(command);
          Process process = processBuilder.start();

          BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
          String line;
          while ((line = reader.readLine()) != null) {
            System.out.println(line);
          }

          process.waitFor();
          System.out.println("Conversion completed.");
          System.out.println("Video saved to "+out);

        } catch (InterruptedException | IOException e) {
          e.printStackTrace();
        }
    }
}
