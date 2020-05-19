package info.decamps.erzconverter.csv;

import static com.google.common.truth.Truth.assertThat;

import info.decamps.erzconverter.model.PickUp;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;
import org.junit.Test;

public class CalendarCsvParserTest {

  @Test
  public void parse() throws Exception {
    CalendarCsvParser parser = CalendarCsvParser.create(PickUp.Type.BIOABFALL);
    List<PickUp> data =
        parser.parse(
            new StringReader(
                "\"PLZ\",\"Abholdatum\"\n"
                    + "8001,\"2020-01-06\"\n"
                    + "8001,\"2020-01-20\"\n"
                    + "8064,\"2020-12-17\"\n"
                    + "8064,\"2020-12-31\"\n"
                    + "\n"));
    assertThat(data)
        .containsExactly(
            PickUp.create("8001", LocalDate.of(2020, 01, 06), PickUp.Type.BIOABFALL),
            PickUp.create("8001", LocalDate.of(2020, 01, 20), PickUp.Type.BIOABFALL),
            PickUp.create("8064", LocalDate.of(2020, 12, 17), PickUp.Type.BIOABFALL),
            PickUp.create("8064", LocalDate.of(2020, 12, 31), PickUp.Type.BIOABFALL))
        .inOrder();
  }
}
