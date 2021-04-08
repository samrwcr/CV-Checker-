package com.bham.pij.assignments.candidates;

import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;



public class CandidatesToInterview {

  Path newCleanCVFile = Paths.get("cleancv.txt");
  Path toInterviewCVFile = Paths.get("to-interview.txt");
  Path toInterviewExpFile = Paths.get("to-interview-experience.txt");
  Path toInterviewTableFormatFile = Paths.get("to-interview-table-format.csv");
  ArrayList<ArrayList<String>> candidatesList = new ArrayList<>();

  public void findCandidates() {

    String[] keywordsDegree = {"Degree in Computer Science", "Masters in Computer Science"};
    String[] keywordsExperience = {"Data Analyst", "Programmer", "Computer programmer","Operator"};
    String s = "";
    String delimiter = ",";
    boolean hasKeyQual = false;
    boolean hasKeyExp = false;

    try {
      InputStream newCleanStream =
          new BufferedInputStream(Files.newInputStream(newCleanCVFile));
      BufferedReader newCleanReader =
          new BufferedReader(new InputStreamReader(newCleanStream));
      OutputStream toInterviewStream =
          new BufferedOutputStream(Files.newOutputStream(toInterviewCVFile));
      BufferedWriter toInterviewWriter =
          new BufferedWriter(new OutputStreamWriter(toInterviewStream));

      s = newCleanReader.readLine();

      while(s != null) {


        if(hasKeyQual == true || hasKeyExp == true) {
          hasKeyQual = false;
          hasKeyExp = false;
        }

        String[] array = s.split(delimiter);
        StringBuilder sb = new StringBuilder();
        String selectedCandidate = "";


        for(int x = 0; x < array.length; x++) {
          if(hasKeyQual == true) {
            break;
          }
          for(int i = 0; i < keywordsDegree.length; i++) {
            if(array[x].equals(keywordsDegree[i])) {
              hasKeyQual = true;
              break;
            }
          }
        }

        for (String value : array) {
          if (hasKeyExp == true) {
            break;
          }
          for (int i = 0; i < keywordsExperience.length; i++) {
            if (value.equals(keywordsExperience[i])) {
              hasKeyExp = true;
              break;
            }
          }
        }

        if(hasKeyQual == true && hasKeyExp == true) {

          for(int i = 0; i < array.length; i++) {

            String st = array[i];
            sb.append(st);

            if(i < array.length - 1) {
              sb.append(" ");
            }
          }

          sb.append(System.getProperty("line.separator"));
          selectedCandidate = sb.toString();
          toInterviewWriter.write(selectedCandidate, 0, selectedCandidate.length());

          ArrayList<String> candidateInfo = new ArrayList();
          for(int i = 0; i < array.length; i++) {
            candidateInfo.add(array[i]);
          }
          candidatesList.add(candidateInfo);
        }

        s = newCleanReader.readLine();
      }

      newCleanReader.close();
      toInterviewWriter.close();


    }
    catch(Exception e) {
      System.out.println("Message: " + e);
    }

  }

  public void candidatesWithExperience() {


    try {

      InputStream newToInterviewStream =
          new BufferedInputStream(Files.newInputStream(toInterviewCVFile));
      BufferedReader newToInterviewReader =
          new BufferedReader(new InputStreamReader(newToInterviewStream));
      OutputStream toInterviewExpStream =
          new BufferedOutputStream(Files.newOutputStream(toInterviewExpFile));
      BufferedWriter toInterviewExpWriter =
          new BufferedWriter(new OutputStreamWriter(toInterviewExpStream));


      String delimiter = " ";
      String s = "";
      s = newToInterviewReader.readLine();

      while(s != null) {

        StringBuilder sb = new StringBuilder();
        String[] array = s.split(delimiter);

        for(String st : array) {

          boolean expInt = isInt(st);
          if(expInt == true) {
            int yearsExp = Integer.parseInt(st);

            if(yearsExp > 5)  {

              sb.append(array[0] + " " + yearsExp + System.getProperty("line.separator"));

            }
            break;
          }

        }

        String expCandidates = sb.toString();
        toInterviewExpWriter.write(expCandidates);

        s = newToInterviewReader.readLine();

      }

      newToInterviewReader.close();
      toInterviewExpWriter.close();

    }
    catch(Exception e) {
      System.out.println("Message: " + e);
    }

    
  }

  public void createCSVFile() {

    try {


      InputStream newToInterviewStream =
          new BufferedInputStream(Files.newInputStream(toInterviewCVFile));
      BufferedReader newToInterviewReader =
          new BufferedReader(new InputStreamReader(newToInterviewStream));
      OutputStream toInterviewTableStream =
          new BufferedOutputStream(Files.newOutputStream(toInterviewTableFormatFile));
      BufferedWriter toInterviewTableWriter =
          new BufferedWriter(new OutputStreamWriter(toInterviewTableStream));

      String s = "";
      String delimiter = " ";
      StringBuilder sb = new StringBuilder();
      sb.append("Identifier" + "," + "Qualification" + "," + "Position1" + "," + "Experience1" + "," + "Position2" + "," + "Experience2" + "," + "eMail" + System.getProperty("line.separator"));
      String headers = sb.toString();
      toInterviewTableWriter.write(headers);

      s = newToInterviewReader.readLine();


      while(s != null) {

        String ID = "";
        String qual = "";
        StringBuilder job1 = new StringBuilder();
        String position1 = "";
        String exp1 = "";
        StringBuilder job2 = new StringBuilder();
        String position2 = "";
        String exp2 = "";
        String email = "";
        int index1 = 0;
        int index2 = 0;
        StringBuilder csvLine = new StringBuilder();

        String[] array = s.split(delimiter);
        ID = array[0];
        qual = array[1] + " " + array[2] + " " + array[3] + " " + array[4];

        for(int i = 0; i < array.length; i++) {

          boolean experience1 = isInt(array[i]);
          if(experience1 == true) {
            exp1   = array[i];
            index1 = i;

            if (array[5].equals(array[index1 - 1])) {
              job1.append(array[5]);
            } else {
              job1.append(array[5] + " " + array[index1 - 1]);
            }
            break;
          }
        }

        if(array.length <= 8) {
          if(array[index1+1].contains("@")) {
            email = array[index1+1];
            position1 = job1.toString();
            csvLine.append(ID + "," + qual + "," + position1 + "," + exp1 + "," + " " + "," + " " + "," + email + System.getProperty("line.separator"));
          }
          String csvToWrite = csvLine.toString();
          toInterviewTableWriter.write(csvToWrite);
        }



        if(array.length > 8) {
          for(int i = index1+1; i < array.length; i++) {

            boolean experience2 = isInt(array[i]);
            if(experience2 == true) {
              exp2 = array[i];
              index2 = i;

              if (array[index1 + 1].equals(array[index2 - 1])) {
                job2.append(array[index1 + 1]);
                email     = array[index2 + 1];
                position2 = job2.toString();
                position1 = job1.toString();

                csvLine.append(ID +
                               "," +
                               qual +
                               "," +
                               position1 +
                               "," +
                               exp1 +
                               "," +
                               position2 +
                               "," +
                               exp2 +
                               "," +
                               email +
                               System.getProperty("line.separator"));
                break;
              } else {
                job2.append(array[index1 + 1] + " " + array[index2 - 1]);
                email     = array[index2 + 1];
                position2 = job2.toString();
                position1 = job1.toString();
                csvLine.append(ID +
                               "," +
                               qual +
                               "," +
                               position1 +
                               "," +
                               exp1 +
                               "," +
                               position2 +
                               "," +
                               exp2 +
                               "," +
                               email +
                               System.getProperty("line.separator"));
                break;

              }

            }

          }
          String csvToWrite = csvLine.toString();
          toInterviewTableWriter.write(csvToWrite);
        }

        s = newToInterviewReader.readLine();
      }

      newToInterviewReader.close();
      toInterviewTableWriter.close();

    }
    catch(Exception e) {
      System.out.println("Message: " + e);
    }




  }

  public void createReport() {

    String id = "Identifier";
    int idSize = id.length();
    String qual = "Qualification";
    int qualSize = qual.length();
    String posit = "Position";
    int positSize = posit.length();
    String exp = "Experience";
    int expSize = exp.length();
    String email = "eMail";
    int emailSize = email.length();

    int idToQualSpaces = 6;
    int qualToPositionSpaces = 20;
    int positionToExpSpaces = 13;
    int expToEmailSpaces = 0;
    int emailToEndSpaces = 11;
    String delimiter = ",";
    String headerst = "";
    String candidateSt = "";
    StringBuilder reportSb = new StringBuilder();
    reportSb.append("Identifier" + "      " + "Qualification" + "            " +
                    "        "+
                    "Position" + "         " + "Experience" + "    " + "eMail" +
                    "           " + System.getProperty("line.separator"));


    try {

      InputStream reportStream =
          new BufferedInputStream(Files.newInputStream(toInterviewTableFormatFile));
      BufferedReader reportReader =
          new BufferedReader(new InputStreamReader(reportStream));

      headerst = reportReader.readLine();
      candidateSt = reportReader.readLine();

      while(candidateSt != null) {

        String[] array = candidateSt.split(delimiter);
        ArrayList<String> candidateInfo = new ArrayList();

        for(int i = 0; i < array.length; i++) {

          candidateInfo.add(array[i]);

        }

        candidateInfo.remove(4);
        candidateInfo.remove(4);

        for(int x = 0; x < candidateInfo.size(); x++) {

          String s = candidateInfo.get(x);

          if(x == 0) {

            int result = s.length() - idSize;
            if(result > 0) {
              int numOfSpaces = idToQualSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
            } else if(result < 0) {
              int numOfSpaces = idToQualSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
            } else {
              reportSb.append(s);
              for(int y = 0; y < idToQualSpaces; y++) {
                reportSb.append(" ");
              }
            }
          } else if(x == 1) {

            int result = s.length() - qualSize;
            if(result > 0) {
              int numOfSpaces = qualToPositionSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
            } else if(result < 0) {
              int numOfSpaces = qualToPositionSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
            } else {
              reportSb.append(s);
              for(int y = 0; y < qualToPositionSpaces; y++) {
                reportSb.append(" ");
              }
            }
          } else if(x == 2) {

            int result = s.length() - positSize;
            if(result > 0) {
              int numOfSpaces = positionToExpSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
            } else if(result < 0) {
              int numOfSpaces = positionToExpSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
            } else {
              reportSb.append(s);
              for(int y = 0; y < positionToExpSpaces; y++) {
                reportSb.append(" ");
              }
            }
          } else if( x == 3) {

            int result = s.length() - expSize;
            if(result > 0) {
              int numOfSpaces = expToEmailSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
            } else if(result < 0) {
              int numOfSpaces = expToEmailSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
            } else {
              reportSb.append(s);
              for(int y = 0; y < expToEmailSpaces; y++) {
                reportSb.append(" ");
              }
            }

          } else if( x == 4) {

            int result = s.length() - emailSize;
            if(result > 0) {
              int numOfSpaces = emailToEndSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
              reportSb.append(System.getProperty("line.separator"));
            } else if(result < 0) {
              int numOfSpaces = emailToEndSpaces - result;
              reportSb.append(s);
              for(int i = 0; i < numOfSpaces; i++) {
                reportSb.append(" ");
              }
              reportSb.append(System.getProperty("line.separator"));
            } else {
              reportSb.append(s);
              for(int y = 0; y < emailToEndSpaces; y++) {
                reportSb.append(" ");
              }
              reportSb.append(System.getProperty("line.separator"));
            }
          }
        }
        candidateSt = reportReader.readLine();
      }

      reportReader.close();
      System.out.print(reportSb);

    }
    catch(Exception e) {
      System.out.println("Message: " + e);
    }
  }


public static boolean isInt(String s) {

  try {
    Integer.parseInt(s);
  }catch(NumberFormatException e) {
    return false;
  }
  return true;
}

}