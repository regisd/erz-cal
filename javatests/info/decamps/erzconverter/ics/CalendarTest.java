package info.decamps.erzconverter.ics;

import static com.google.common.truth.Truth.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import org.junit.Test;

public class CalendarTest {

  @Test
  public void toIcs() {
    Calendar calendar =
        Calendar.create("Test", Event.create(LocalDate.of(1979, 07, 14), "Fête à la Bastille"));
    ByteArrayOutputStream out = new ByteArrayOutputStream(256);
    calendar.toIcs(new PrintWriter(out));
    assertThat(new String(out.toByteArray()))
        .isEqualTo(
            "BEGIN:VCALENDAR\n"
                + "VERSION:2.0\n"
                + "PRODID:-//decamps/erzcal//NONSGML v1.0//EN\n"
                + "NAME:Test\n"
                + "DESCRIPTION:Test\n"
                + "BEGIN:VEVENT\n"
                + "DTSTART;VALUE=DATE:19790714\n"
                + "DTEND;VALUE=DATE:19790715\n"
                + "SUMMARY:Fête à la Bastille\n"
                + "END:VEVENT\n"
                + "END:VCALENDAR\n");
  }
}
