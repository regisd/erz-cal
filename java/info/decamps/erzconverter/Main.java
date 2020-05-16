package info.decamps.erzconverter;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.joining;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import info.decamps.erzconverter.csv.CalendarCsvParser;
import info.decamps.erzconverter.ics.Calendar;
import info.decamps.erzconverter.ics.Event;
import info.decamps.erzconverter.model.PickUp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
  @Parameter(names = "--out", description = "Output ics directory")
  String outDir;

  @Parameter(description = "List of input csv calendars")
  List<String> calendars;

  public static void main(String[] args) throws Exception {
    Main main = new Main();
    JCommander.newBuilder().addObject(main).build().parse(args);
    main.run();
  }

  private void run() throws IOException {
    File outputDir = new File(outDir);
    System.out.println("Calendars " + calendars.stream().collect(joining(",")));
    System.out.println("Output in " + outputDir.getAbsolutePath());

    CalendarCsvParser csvParser = CalendarCsvParser.create();
    // TODO(regisd) Parse multiple inputs
    List<PickUp> data = csvParser.parse(new File(calendars.get(0)));

    Map<String, List<PickUp>> pickupsByLocation = splitPickupsByLocation(data);
    for (Map.Entry<String, List<PickUp>> entry : pickupsByLocation.entrySet()) {
      File outFile = new File(outputDir, "ics_" + entry.getKey());
      try (PrintWriter writer = new PrintWriter(new FileOutputStream(outFile))) {
        System.out.println("Out in " + outFile.getAbsolutePath());
        List<Event> events =
            entry.getValue().stream().map(Event::from).collect(Collectors.toList());
        String calendarName = "ERZ Calendar for " + entry.getKey();
        Calendar.create(calendarName, events).toIcs(writer);
      }
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
}
