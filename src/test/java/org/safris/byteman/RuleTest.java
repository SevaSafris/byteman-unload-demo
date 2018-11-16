package org.safris.byteman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RuleTest {
  private String message;

  public Runnable rule1 = () -> {message += " rule1";};
  public Runnable rule2 = () -> {message += " rule2";};

  public String triggerMethod() {
    return message;
  }

  @Before
  public void reset() {
    Manager.reinstallRules();
    message = "default";
  }

  /**
   * Default execution: Both rule1 and rule2 should be triggered.
   */
  @Test
  public void test1() {
    assertEquals("Both rule1 and rule2 should be triggered", "default rule1 rule2", triggerMethod());
  }

  /**
   * Execution after rule1 is unloaded: Rule2 should be triggered.
   */
  @Test
  public void test2() {
    Manager.unloadRule("rule1");
    assertEquals("Rule2 should be triggered", "default rule2", triggerMethod());
  }

  /**
   * Execution after rule1 is unloaded during the execution of rule1: Rule2
   * should be triggered.
   * <p>
   * This test fails. It seems that if rule1 is unloaded, then no subsequent
   * rules are triggered.
   */
  @Test
  public void test3() {
    rule1 = () -> {Manager.unloadRule("rule1");};
    assertEquals("Rule2 should be triggered", "default rule2", triggerMethod());
  }

  /**
   * Execution after rule2 is unloaded during the execution of rule1: Rule2
   * should NOT be triggered.
   * <p>
   * This test fails. It seems that if rule1 is unloaded, then no subsequent
   * rules are executed.
   */
  @Test
  public void test4() {
    rule1 = () -> {Manager.unloadRule("rule2");};
    assertEquals("Rule2 should NOT be triggered", "default", triggerMethod());
  }
}