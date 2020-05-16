package info.decamps.erzconverter.ics;

import com.google.auto.value.AutoValue;
import info.decamps.erzconverter.model.PickUp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

  String toIcs(int uid) {
    String date = date().format(DateTimeFormatter.BASIC_ISO_DATE);
    String followingDate = followingDate().format(DateTimeFormatter.BASIC_ISO_DATE);
    return String.format(
        Locale.ENGLISH,
        // begin event
        "BEGIN:VEVENT\r\n"
            + // UID is required https://icalendar.org/iCalendar-RFC-5545/3-6-1-event-component.html
            "UID:%d\r\n"
            + // start date – all day event
            "DTSTART;VALUE=DATE:%s\r\n"
            + // start alone doesn't work on Google calendar
            "DTEND;VALUE=DATE:%s\r\n"
            + // event summary
            "SUMMARY:%s\r\n"
            + // end event
            "END:VEVENT\r\n",
        uid,
        date,
        followingDate,
        summary());
  }
}
