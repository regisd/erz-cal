package info.decamps.erzconverter.ics;

import static com.google.common.truth.Truth.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.Test;

public class CalendarTest {

  @Test
  public void toIcs() {
    Calendar calendar =
        Calendar.create(Collections.singletonList(Event.create(LocalDate.of(1979, 07, 14))));
    ByteArrayOutputStream out = new ByteArrayOutputStream(256);
    calendar.toIcs(new PrintWriter(out));
    assertThat(new String(out.toByteArray()))
        .isEqualTo(
            "BEGIN:VCALENDAR\n"
                + "VERSION:2.0\n"
                + "PRODID:-//hacksw/handcal//NONSGML v1.0//EN\n"
                + "BEGIN:VEVENT\n"
                + "DTSTART:19970714T170000Z\n"
                + "DTEND:19970715T035900Z\n"
                + "SUMMARY:Fête à la Bastille\n"
                + "END:VEVENT\n"
                + "END:VCALENDAR\n");
  }
}
