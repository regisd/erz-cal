package info.decamps.erzconverter.ics;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import org.junit.Test;

public class EventTest {

  @Test
  public void toIcs() {
    Event event = Event.create(LocalDate.of(1789, 07, 14), "Fête à la Bastille");
    assertThat(event.toIcs())
        .isEqualTo(
            "BEGIN:VEVENT\r\n"
                + "DTSTART;VALUE=DATE:17890714\r\n"
                + "DTEND;VALUE=DATE:17890715\r\n"
                + "SUMMARY:Fête à la Bastille\r\n"
                + "END:VEVENT\r\n");
  }
}
