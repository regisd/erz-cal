package info.decamps.erzconverter.index;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

public class IndexGeneratorTest {

  @Test
  public void generate() throws Exception {
    StringWriter writer = new StringWriter();
    new IndexGenerator(writer).generate(ImmutableSet.of("123", "124"));
    File golden = new File("javatests/info/decamps/erzconverter/index/index.golden.md");
    if (!golden.isFile()) {
      throw new FileNotFoundException("Golden file missing: " + golden.getAbsolutePath());
    }
    assertThat(writer.toString()).isEqualTo(Files.toString(golden, StandardCharsets.UTF_8));
  }
}
