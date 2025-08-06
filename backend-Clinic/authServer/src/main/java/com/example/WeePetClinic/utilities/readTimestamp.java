package com.example.WeePetClinic.utilities;

import java.sql.Timestamp;
import java.time.Year;
import java.util.IllegalFormatException;
import java.util.Scanner;

public class readTimestamp {

  public static final String red = "\u001B[31m";
  public static final String reset = "\u001B[0m";
  public static final String yellow = "\u001B[33m";

  public static Timestamp readTimeStamp(Scanner sc) {
    int m =0;
    int d =0;
    int h =0;
    int mi =0;
    try {
      System.out.println(yellow + "** Appointment time:" + reset);
      System.out.println(yellow + "Month: " + reset);
      m = sc.nextInt();
      System.out.println(yellow + "Date: " + reset);
      d = sc.nextInt();
      System.out.println(yellow + "Hour: " + reset);
      h = sc.nextInt();
      System.out.println(yellow + "Minute: " + reset);
      mi = sc.nextInt();
    } catch (IllegalFormatException e) {
      System.out.println(red+"invalid input, try again:"+reset);
    }
    return new Timestamp(Year.now().getValue() - 1900, m - 1, d, h, mi, 0, 0);
  }
}
