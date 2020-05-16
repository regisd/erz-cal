package info.decamps.erzconverter.ics;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import org.junit.Test;

public class EventTest {

  @Test
  public void toIcs() {
    Event event = Event.create(LocalDate.of(1789, 07, 14));
    assertThat(event.toIcs())
        .isEqualTo(
            "BEGIN:VEVENT\n"
                + "DTSTART:19970714T170000Z\n"
                + "DTEND:19970715T035900Z\n"
                + "SUMMARY:Fête à la Bastille\n"
                + "END:VEVENT\n");
  }
}
