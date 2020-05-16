package info.decamps.erzconverter.csv;

import com.google.auto.value.AutoValue;
import info.decamps.erzconverter.model.PickUp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarCsvParser {

  private final Spec spec;

  public CalendarCsvParser(Spec spec) {
    this.spec = spec;
  }

  public static CalendarCsvParser create() {
    return new CalendarCsvParser(Spec.DEFAULT);
  }

  public List<PickUp> parse(File csvFile) throws IOException {
    try (FileReader reader = new FileReader(csvFile)) {
      return parse(reader);
    }
  }

  public List<PickUp> parse(Reader reader) throws IOException {
    try (BufferedReader bufferedReader = new BufferedReader(reader)) {
      return parse(new BufferedReader(reader));
    }
  }

  public List<PickUp> parse(BufferedReader reader) throws IOException {
    if (spec.hasHeader()) {
      reader.readLine();
    }
    ArrayList<PickUp> result = new ArrayList<>();
    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
      if (line.trim().isEmpty()) {
        break;
      }
      result.add(parseLine(line));
    }
    return result;
  }

  private PickUp parseLine(String line) {
    String[] data = line.split(spec.cvsSplitBy());
    String date = data[1].substring(1, data[1].length() - 1); // trim `"`
    return PickUp.create(data[0], LocalDate.parse(date));
  }

  @AutoValue
  abstract static class Spec {
    static final Spec DEFAULT = create(",", true);

    abstract String cvsSplitBy();

    abstract boolean hasHeader();

    static Spec create(String cvsSplitBy, boolean hasHeader) {
      return new AutoValue_CalendarCsvParser_Spec(cvsSplitBy, hasHeader);
    }
  }
}
