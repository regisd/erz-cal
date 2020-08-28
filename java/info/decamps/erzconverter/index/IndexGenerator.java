package info.decamps.erzconverter.index;

import static java.util.stream.Collectors.toList;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;

public class IndexGenerator {

  private static final String LOG_TAG = "IndexGenerator";
  private final Writer writer;

  public IndexGenerator(Writer writer) {
    this.writer = writer;
  }

  public void generate(Set<String> postCodes) throws ParseException {
    Context velocityContext = new VelocityContext();
    velocityContext.put("postCodes", postCodes.stream().sorted().collect(toList()));

    RuntimeInstance velocityInstance = new RuntimeInstance();
    velocityInstance.setProperty(RuntimeConstants.RUNTIME_REFERENCES_STRICT, "true");
    SimpleNode nodeTree = makeVelocityNode(velocityInstance, "index.md.vm");
    velocityInstance.render(velocityContext, writer, LOG_TAG, nodeTree);
  }

  private SimpleNode makeVelocityNode(RuntimeInstance velocityInstance, String templateName)
      throws ParseException {
    return velocityInstance.parse(
        readResource(getResourcePath() + "/" + templateName), templateName);
  }

  private Reader readResource(String resourceName) {
    InputStream res = getClass().getClassLoader().getResourceAsStream(resourceName);
    if (res == null) {
      throw new ResourceNotFoundException("Java resource not found: " + resourceName);
    }
    return new InputStreamReader(res, StandardCharsets.UTF_8);
  }

  private String getResourcePath() {
    return getClass().getPackageName().replace('.', '/');
  }
}
