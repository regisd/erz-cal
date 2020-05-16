package info.decamps.erzconverter.ics;

import com.google.auto.value.AutoValue;
import info.decamps.erzconverter.model.PickUp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AutoValue
public abstract class Event {

  abstract LocalDate date();

  abstract String summary();

  LocalDate followingDate() {
    return date().plusDays(1);
  }

  static Event create(LocalDate date, String summary) {
    return new AutoValue_Event(date, summary);
  }

  public static Event from(PickUp pickup) {
    return create(pickup.date(), String.valueOf(pickup.type()));
  }

  String toIcs() {
    String date = date().format(DateTimeFormatter.BASIC_ISO_DATE);
    String followingDate = followingDate().format(DateTimeFormatter.BASIC_ISO_DATE);
    return String.format(
        // begin event
        "BEGIN:VEVENT\n"
            + // start date – all day event
            "DTSTART;VALUE=DATE:%s\n"
            + // start alone doesn't work on Google calendar
            "DTEND;VALUE=DATE:%s\n"
            + // event summary
            "SUMMARY:%s\n"
            + // end event
            "END:VEVENT\n",
        date, followingDate, summary());
  }
}
