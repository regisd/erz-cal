package info.decamps.erzconverter.index;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableSet;
import java.io.StringWriter;
import org.junit.Test;

public class IndexGeneratorTest {

  @Test
  public void generate() throws Exception {
    StringWriter writer = new StringWriter();
    new IndexGenerator().generate(ImmutableSet.of("123", "124"), writer);
    assertThat(writer.toString()).isEqualTo("Hello World!\n");
  }
}
