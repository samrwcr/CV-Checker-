package com.bham.pij.assignments.candidates;

public class JobCandidatesMain {

  public static void main(String[] args) {
    CleaningUp newCleanUp = new CleaningUp();
    newCleanUp.cleanUpFile();

    CandidatesToInterview newCandidates = new CandidatesToInterview();
    newCandidates.findCandidates();
    newCandidates.candidatesWithExperience();
    newCandidates.createCSVFile();
    newCandidates.createReport();
  }
}
