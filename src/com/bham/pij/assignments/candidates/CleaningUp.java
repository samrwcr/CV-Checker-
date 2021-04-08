package com.bham.pij.assignments.candidates;

import java.nio.file.*;
import java.io.*;


public class CleaningUp {

  Path dirtyCVFile = Paths.get("dirtycv.txt");
  Path cleanCVFile = Paths.get("cleancv.txt");

  public void cleanUpFile() {

    String s = "";
    int idNum = 0;
    String delimiter = ",";

    try {
      InputStream dirtyStream =
          new BufferedInputStream(Files.newInputStream(dirtyCVFile));
      BufferedReader dirtyReader =
          new BufferedReader(new InputStreamReader(dirtyStream));
      OutputStream cleanStream =
          new BufferedOutputStream(Files.newOutputStream(cleanCVFile));
      BufferedWriter cleanWriter =
          new BufferedWriter(new OutputStreamWriter(cleanStream));

      s = dirtyReader.readLine();

      while(s != null) {

        String subString1 = "";
        String subString2 = "";
        StringBuilder sb = new StringBuilder();


        if(s.contains("CV")){
          idNum++;
        }


        if(s.contains(":")) {
          for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ':') {
              int endIn = i;
              subString1 = s.substring(0, endIn);
              subString2 = s.substring(endIn + 1);
              break;
            }
          }
        }

        switch(subString1) {
          case "Surname":
            sb.append(subString2 + "000" + idNum + delimiter);
            break;
          case "Qualification":
            sb.append(subString2 + delimiter);
            break;
          case "Position":
            sb.append(subString2 + delimiter);
            break;
          case "Experience":
            sb.append(subString2 + delimiter);
            break;
          case "eMail":
            sb.append(subString2 + delimiter);
            break;
        }

        if(s.contains("End")) {
          sb.append(System.getProperty("line.separator"));
        }

        String mainString = sb.toString();
        cleanWriter.write(mainString, 0, mainString.length());


        s = dirtyReader.readLine();


      }
      dirtyReader.close();
      cleanWriter.close();

    } catch (Exception e) {
      System.out.println("Message: " + e);

    }


  }


}

