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
            "BEGIN:VEVENT\n"
                + "DTSTART;VALUE=DATE:17890714\n"
                + "SUMMARY:Fête à la Bastille\n"
                + "END:VEVENT\n");
  }
}
