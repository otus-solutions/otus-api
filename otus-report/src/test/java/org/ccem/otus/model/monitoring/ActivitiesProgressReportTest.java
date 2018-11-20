package org.ccem.otus.model.monitoring;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class ActivitiesProgressReportTest {
  private static final LinkedList<String> NORMALIZER_LIST = new LinkedList<String>();
  private static  ActivitiesProgressReport PROGRESS_REPORT = null;
  private static final String REPORTJSON = "{\n" +
    "    \"activities\": [\n" +
    "    {\n" +
    "      \"rn\": 5113372,\n" +
    "      \"acronym\": \"ABC\",\n" +
    "      \"status\": 2\n" +
    "    },\n" +
    "    {\n" +
    "      \"rn\": 5113372,\n" +
    "      \"acronym\": \"DEF\",\n" +
    "      \"status\": 2\n" +
    "    }\n" +
    "    ],\n" +
    "    \"rn\": 5113372\n" +
    "  }";

  @Before
  public void setUp() {
    NORMALIZER_LIST.add("HVSD");
    NORMALIZER_LIST.add("PSEC");
    NORMALIZER_LIST.add("ABC");
    NORMALIZER_LIST.add("DEF");

    PROGRESS_REPORT = ActivitiesProgressReport.deserialize(REPORTJSON);
  }

  @Test
  public void normalize_should_build_an_activities_array_of_the_size_of_normalizer_map_values() {
    assertEquals(2, PROGRESS_REPORT.getActivities().size());

    PROGRESS_REPORT.normalize(NORMALIZER_LIST);

    assertEquals(NORMALIZER_LIST.size(), PROGRESS_REPORT.getActivities().size());
  }

  @Test
  public void should_deserialize() {
    assert(PROGRESS_REPORT.getRn().equals((long) 5113372));
  }
}