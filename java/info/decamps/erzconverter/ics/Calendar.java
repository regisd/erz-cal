package info.decamps.erzconverter.ics;

import com.google.auto.value.AutoValue;
import java.io.PrintWriter;
import java.util.List;

@AutoValue
public abstract class Calendar {
  abstract List<Event> events();

  public static Calendar create(List<Event> events) {
    return new AutoValue_Calendar(events);
  }

  public void toIcs(PrintWriter writer) {
    writer.write("BEGIN:VCALENDAR\n");
    writer.write("VERSION:2.0\n");
    writer.write("PRODID:-//hacksw/handcal//NONSGML v1.0//EN\n");
    for (Event event : events()) {
      writer.write(event.toIcs());
    }
    writer.write("END:VCALENDAR\n");
    writer.flush();
  }
}
