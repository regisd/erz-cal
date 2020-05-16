package info.decamps.erzconverter.ics;

import static java.util.Collections.singletonList;

import com.google.auto.value.AutoValue;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.util.List;

@AutoValue
public abstract class Calendar {
  abstract String name();

  abstract List<Event> events();

  abstract ZoneId timezone();

  public void toIcs(PrintWriter writer) {
    writer.write("BEGIN:VCALENDAR\r\n");
    writer.write("VERSION:2.0\r\n");
    writer.write("PRODID:-//decamps/erzcal//NONSGML v1.0//EN\r\n");
    writer.write("NAME:" + name() + "\r\n");
    writer.write("DESCRIPTION:" + name() + "\r\n");
    writer.write("TIMEZONE-ID:" + timezone() + "\r\n");
    writer.write("TZID:" + timezone() + "\r\n");
    for (Event event : events()) {
      writer.write(event.toIcs());
    }
    writer.write("END:VCALENDAR\r\n");
    writer.flush();
  }

  public static Builder builder() {
    return new AutoValue_Calendar.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder name(String name);

    public abstract Builder events(List<Event> events);

    public Builder singleEvent(Event event) {
      return events(singletonList(event));
    }

    public abstract Builder timezone(ZoneId timezone);

    public abstract Calendar build();
  }
}
