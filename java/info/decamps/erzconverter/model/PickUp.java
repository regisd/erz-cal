package info.decamps.erzconverter.model;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;

@AutoValue
public abstract class PickUp {
  public abstract String postcode();

  public abstract LocalDate date();

  public static PickUp create(String postcode, LocalDate date) {
    return new AutoValue_PickUp(postcode, date);
  }
}
