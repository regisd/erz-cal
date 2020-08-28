package info.decamps.erzconverter.ics;

import static java.util.Collections.singletonList;

import com.google.auto.value.AutoValue;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    writer.write("X-GOOGLE-CALENDAR-CONTENT-TITLE:" + name() + "\r\n");
    writer.write("DESCRIPTION:" + name() + "\r\n");
    writer.write("TIMEZONE-ID:" + timezone() + "\r\n");
    writer.write("TZID:" + timezone() + "\r\n");
    int uid = 0;
    for (Event event : events()) {
      writer.write(event.toIcs(++uid));
    }
    writer.write("END:VCALENDAR\r\n");
    writer.flush();
  }

  public static Builder builder() {
    return new AutoValue_Calendar.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    private static final Comparator<Event> COMPARATOR_EVENT_DATE =
        (o1, o2) -> o1.date().compareTo(o2.date());

    public abstract Builder name(String name);

    abstract List<Event> events();

    public abstract Builder events(List<Event> events);

    public Builder singleEvent(Event event) {
      return events(singletonList(event));
    }

    public abstract Builder timezone(ZoneId timezone);

    abstract Calendar internalBuild();

    public Calendar build() {
      events(events().stream().sorted(COMPARATOR_EVENT_DATE).collect(Collectors.toList()));
      return internalBuild();
    }
  }
}
