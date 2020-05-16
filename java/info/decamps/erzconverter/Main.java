package info.decamps.erzconverter;

import info.decamps.erzconverter.csv.CalendarCsvParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

  public static void main(String[] args) throws IOException {
    File outFile = new File("ics");
    System.out.println("Output in " + outFile.getAbsolutePath());
    CalendarCsvParser csvParser = CalendarCsvParser.create();
    csvParser.parse(new File(args[0]));
    try (PrintWriter writer = new PrintWriter(new FileOutputStream(outFile))) {
      writer.write("OK");
    }
  }
}
