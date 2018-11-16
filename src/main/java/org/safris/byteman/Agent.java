package org.safris.byteman;

import java.lang.instrument.Instrumentation;

import org.jboss.byteman.agent.Main;

public class Agent {
  public static void premain(String agentArgs, final Instrumentation inst) throws Exception {
    if (agentArgs == null || agentArgs.trim().isEmpty())
      agentArgs = "";
    else
      agentArgs += ",";

    agentArgs += "manager:" + Manager.class.getName();
    Main.premain(agentArgs, inst);
  }

  public static void agentmain(final String agentArgs, final Instrumentation inst) throws Exception {
    premain(agentArgs, inst);
  }
}