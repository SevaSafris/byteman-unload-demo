package org.safris.byteman;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

import org.jboss.byteman.agent.Retransformer;

public class Manager {
  private static Retransformer transformer;

  public static void initialize(final Retransformer trans) {
    transformer = trans;
  }

  private static void loadRules(final ClassLoader classLoader) {
    final URL script = classLoader.getResource("script.btm");
    final StringWriter sw = new StringWriter();
    try (final PrintWriter pw = new PrintWriter(sw)) {
      transformer.installScript(Arrays.asList(getText(script)), Arrays.asList(script.toString()), pw);
    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String getText(final URL url) throws IOException {
    final StringBuilder builder = new StringBuilder();
    try (final InputStream in = url.openStream()) {
      final byte[] bytes = new byte[1024];
      for (int len; (len = in.read(bytes)) != -1; builder.append(new String(bytes, 0, len)));
    }

    return builder.toString();
  }

  public static void unloadRule(final String ruleName) {
    final StringWriter sw = new StringWriter();
    try (final PrintWriter pw = new PrintWriter(sw)) {
      transformer.removeScripts(Collections.singletonList("RULE " + ruleName), pw);
    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void reinstallRules() {
    loadRules(ClassLoader.getSystemClassLoader());
  }
}