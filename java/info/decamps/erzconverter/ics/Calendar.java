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
    writer.write("BEGIN:VCALENDAR\n");
    writer.write("VERSION:2.0\n");
    writer.write("PRODID:-//decamps/erzcal//NONSGML v1.0//EN\n");
    writer.write("NAME:" + name() + "\n");
    writer.write("DESCRIPTION:" + name() + "\n");
    writer.write("TIMEZONE-ID:" + timezone() + "\n");
    writer.write("TZID:" + timezone() + "\n");
    for (Event event : events()) {
      writer.write(event.toIcs());
    }
    writer.write("END:VCALENDAR\n");
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
