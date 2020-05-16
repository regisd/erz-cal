package info.decamps.erzconverter.ics;

import static com.google.common.truth.Truth.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.Test;

public class CalendarTest {

  @Test
  public void toIcs() {
    Calendar calendar =
        Calendar.builder()
            .name("Test")
            .timezone(ZoneId.of("Europe/Paris"))
            .singleEvent(Event.create(LocalDate.of(1979, 07, 14), "Fête à la Bastille"))
            .build();
    ByteArrayOutputStream out = new ByteArrayOutputStream(256);
    calendar.toIcs(new PrintWriter(out));
    assertThat(new String(out.toByteArray()))
        .isEqualTo(
            "BEGIN:VCALENDAR\r\n"
                + "VERSION:2.0\r\n"
                + "PRODID:-//decamps/erzcal//NONSGML v1.0//EN\r\n"
                + "NAME:Test\r\n"
                + "DESCRIPTION:Test\r\n"
                + "TIMEZONE-ID:Europe/Paris\r\n"
                + "TZID:Europe/Paris\r\n"
                + "BEGIN:VEVENT\r\n"
                + "UID:1\r\n"
                + "DTSTART;VALUE=DATE:19790714\r\n"
                + "DTEND;VALUE=DATE:19790715\r\n"
                + "SUMMARY:Fête à la Bastille\r\n"
                + "END:VEVENT\r\n"
                + "END:VCALENDAR\r\n");
  }
}
