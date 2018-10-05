package br.org.otus.monitoring.builder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActivityStatusQueryBuilderTest {
  private static ActivityStatusQueryBuilder builder = null;

  @Before
  public void setUp() {
    builder = new ActivityStatusQueryBuilder();
  }

  @Test
  public void build() {
    builder.matchFieldCenter("RS");
    assertEquals(1, builder.build().size());
  }

  @Test
  public void matchFieldCenter() {
    builder.matchFieldCenter("RS");
    assertEquals(1, builder.build().size());
  }

  @Test
  public void limit() {
    builder.limit(10);
    assertEquals(1, builder.build().size());
  }

  @Test
  public void projectLastStatus() {
    builder.projectLastStatus();
    assertEquals(1, builder.build().size());
  }

  @Test
  public void getStatusValue() {
    builder.getStatusValue();
    assertEquals(1, builder.build().size());
  }

  @Test
  public void projectId() {
    builder.projectId();
    assertEquals(1, builder.build().size());
  }

  @Test
  public void sortByDate() {
    builder.sortByDate();
    assertEquals(1, builder.build().size());
  }

  @Test
  public void removeStatusDate() {
    builder.removeStatusDate();
    assertEquals(1, builder.build().size());
  }

  @Test
  public void groupByParticipant() {
    builder.groupByParticipant();
    assertEquals(1, builder.build().size());
  }
}