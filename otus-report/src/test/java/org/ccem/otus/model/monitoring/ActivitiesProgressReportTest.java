package org.ccem.otus.model.monitoring;

import org.ccem.otus.service.MonitoringServiceBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ActivitiesProgressReportTest {
  private static final HashMap<String, ActivityFlagReport> NORMALIZER_MAP = new HashMap<>();
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
    NORMALIZER_MAP.put("HVSD", new ActivityFlagReport("HVSD"));
    NORMALIZER_MAP.put("PSEC", new ActivityFlagReport("PSEC"));
    NORMALIZER_MAP.put("ABC", new ActivityFlagReport("ABC"));
    NORMALIZER_MAP.put("DEF", new ActivityFlagReport("DEF"));

    PROGRESS_REPORT = ActivitiesProgressReport.deserialize(REPORTJSON);
  }

  @Test
  public void normalize_should_build_an_activities_array_of_the_size_of_normalizer_map_values() {
    assertEquals(2, PROGRESS_REPORT.getActivities().size());

    PROGRESS_REPORT.normalize(NORMALIZER_MAP);

    assertEquals(NORMALIZER_MAP.size(), PROGRESS_REPORT.getActivities().size());
  }
}