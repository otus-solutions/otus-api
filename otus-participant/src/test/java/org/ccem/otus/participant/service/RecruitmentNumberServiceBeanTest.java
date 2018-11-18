package org.ccem.otus.participant.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RecruitmentNumberServiceBeanTest {

  @InjectMocks
  private RecruitmentNumberServiceBean recruitmentNumberService;

  @Test
  public void build() {
    Long aLong = 4000123L;
//    Long result = recruitmentNumberService.get();

//    assertEquals(java.util.Optional.of(4000124L).get(), result);
  }
}