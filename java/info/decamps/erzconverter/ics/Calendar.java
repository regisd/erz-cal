package info.decamps.erzconverter.ics;

import static java.util.Collections.singletonList;

import com.google.auto.value.AutoValue;
import java.io.PrintWriter;
import java.util.List;

@AutoValue
public abstract class Calendar {
  abstract String name();

  abstract List<Event> events();

  public static Calendar create(String name, List<Event> events) {
    return new AutoValue_Calendar(name, events);
  }

  public static Calendar create(String name, Event singleEvent) {
    return create(name, singletonList(singleEvent));
  }

  public void toIcs(PrintWriter writer) {
    writer.write("BEGIN:VCALENDAR\n");
    writer.write("VERSION:2.0\n");
    writer.write("PRODID:-//decamps/erzcal//NONSGML v1.0//EN\n");
    writer.write("NAME:" + name() + "\n");
    writer.write("DESCRIPTION:" + name() + "\n");
    for (Event event : events()) {
      writer.write(event.toIcs());
    }
    writer.write("END:VCALENDAR\n");
    writer.flush();
  }
}
