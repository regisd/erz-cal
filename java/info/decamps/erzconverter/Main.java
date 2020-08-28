package info.decamps.erzconverter;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import info.decamps.erzconverter.csv.CalendarCsvParser;
import info.decamps.erzconverter.ics.Calendar;
import info.decamps.erzconverter.ics.Event;
import info.decamps.erzconverter.index.IndexGenerator;
import info.decamps.erzconverter.model.PickUp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.velocity.runtime.parser.ParseException;

public class Main {

  ZoneId TZ_ZURICH = ZoneId.of("Europe/Zurich");

  @Parameter(names = "--out", description = "Output ics directory")
  String outDir;

  @Parameter(names = "--bioabfall", description = "Input calendar for organic waste")
  String organicCalendar;

  @Parameter(names = "--karton", description = "Input calendar for cardboard waste")
  String cardboardCalendar;

  @Parameter(names = "--papier", description = "Input calendar for paper waste")
  String paperCalendar;

  public static void main(String[] args) throws Exception {
    Main main = new Main();
    JCommander.newBuilder().addObject(main).build().parse(args);
    main.run();
  }

  private void run() throws IOException, ParseException {
    File outputDir = new File(outDir);
    System.out.println("Calendars " + String.join(",", organicCalendar));
    System.out.println("Output in " + outputDir.getAbsolutePath());

    generateCalendarsOfType(outputDir, PickUp.Type.BIOABFALL, organicCalendar);
    generateCalendarsOfType(outputDir, PickUp.Type.KARTON, cardboardCalendar);
    generateCalendarsOfType(outputDir, PickUp.Type.PAPIER, paperCalendar);

    generateIndex(outputDir);
  }

  private void generateCalendarsOfType(File outputDir, PickUp.Type type, String calendar)
      throws IOException {
    List<PickUp> data = parseCalendar(type, calendar);
    Map<String, List<PickUp>> pickupsByLocation = splitPickupsByLocation(data);
    File subdir = new File(outputDir, type.name());
    subdir.mkdir();
    generateIcs(subdir, pickupsByLocation);
  }

  private void generateIndex(File outputDir) throws IOException, ParseException {
    // TODO Optimize: Avoid double parsing
    List<PickUp> data =
        Stream.of(
                parseCalendar(PickUp.Type.BIOABFALL, organicCalendar),
                parseCalendar(PickUp.Type.KARTON, cardboardCalendar),
                parseCalendar(PickUp.Type.PAPIER, paperCalendar))
            .flatMap(Collection::stream)
            .collect(toList());
    Map<String, List<PickUp>> pickupsByLocation = splitPickupsByLocation(data);
    try (Writer writer = new FileWriter(outputDir + "/index.md")) {
      new IndexGenerator(writer).generate(pickupsByLocation.keySet());
    }
  }

  private Map<String, List<PickUp>> splitPickupsByLocation(List<PickUp> data) {
    Function<PickUp, String> keyMapper = PickUp::postcode;
    Function<PickUp, List<PickUp>> valueMapper = pickUp -> new ArrayList<>(singleton(pickUp));
    BinaryOperator<List<PickUp>> mergeFunction =
        (pickUps, pickUps2) -> {
          pickUps.addAll(pickUps2);
          return pickUps;
        };
    return data.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
  }

  private List<PickUp> parseCalendar(PickUp.Type type, String csvFilename) throws IOException {
    if (csvFilename == null) {
      return new ArrayList<>(/*initialCapacity=*/ 0);
    }
    CalendarCsvParser csvParser = CalendarCsvParser.create(type);
    return csvParser.parse(new File(csvFilename));
  }

  private void generateIcs(File outputDir, Map<String, List<PickUp>> pickupsByLocation)
      throws FileNotFoundException {
    for (Map.Entry<String, List<PickUp>> entry : pickupsByLocation.entrySet()) {
      String postCode = entry.getKey();
      List<PickUp> pickups = entry.getValue();
      File outFile = new File(outputDir, "erz_" + postCode + ".ics");
      generateIcs(outFile, postCode, pickups);
    }
  }

  private void generateIcs(File outFile, String postCode, List<PickUp> pickups)
      throws FileNotFoundException {
    try (PrintWriter writer = new PrintWriter(new FileOutputStream(outFile))) {
      System.out.println("Out in " + outFile.getAbsolutePath());
      List<Event> events = pickups.stream().map(Event::from).collect(toList());
      String calendarName = "ERZ Calendar for " + postCode;
      Calendar.builder()
          .timezone(TZ_ZURICH)
          .name(calendarName)
          .events(events)
          .build()
          .toIcs(writer);
    }
  }
}
