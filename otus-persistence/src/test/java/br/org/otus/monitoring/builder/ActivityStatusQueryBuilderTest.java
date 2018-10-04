package br.org.otus.monitoring.builder;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityStatusQueryBuilderTest {
  private static final ActivityStatusQueryBuilder BUILDER = new ActivityStatusQueryBuilder();


  @Test
  public void build() {
  }

  @Test
  public void matchFieldCenter() {
    BUILDER.matchFieldCenter("RS");
  }

  @Test
  public void limit() {
  }

  @Test
  public void projectLastStatus() {
  }

  @Test
  public void getStatusValue() {
  }

  @Test
  public void projectId() {
  }

  @Test
  public void sortByDate() {
  }

  @Test
  public void removeStatusDate() {
  }

  @Test
  public void groupByParticipant() {
  }
}