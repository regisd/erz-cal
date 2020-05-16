package info.decamps.erzconverter.ics;

import com.google.auto.value.AutoValue;
import info.decamps.erzconverter.model.PickUp;
import java.time.LocalDate;

@AutoValue
public abstract class Event {

  abstract LocalDate date();

  static Event create(LocalDate date) {
    return new AutoValue_Event(date);
  }

  public static Event from(PickUp pickup) {
    return create(pickup.date());
  }

  String toIcs() {
    return "BEGIN:VEVENT\n"
        + "DTSTART:19970714T170000Z\n"
        + "DTEND:19970715T035900Z\n"
        + "SUMMARY:Fête à la Bastille\n"
        + "END:VEVENT\n";
  }
}
