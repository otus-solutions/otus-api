package org.ccem.otus.model.monitoring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ParticipantExamReportDtoTest {
    private ParticipantExamReportDto participantExamReportDto;
    private String participantExamReportDtoJson;

    @Before
    public void setUp() {
        participantExamReportDto = new ParticipantExamReportDto();

        participantExamReportDtoJson = ParticipantExamReportDto.serialize(participantExamReportDto);
    }

    @Test
    public void serializeMethod_should_convert_objectModel_to_JsonString() {
         assertTrue(ParticipantExamReportDto.serialize(participantExamReportDto)instanceof String);
    }

    @Test
    public void deserializeMethod_shold_convert_JsonString_to_objectModel() {
        assertTrue(ParticipantExamReportDto.deserialize(participantExamReportDtoJson)instanceof ParticipantExamReportDto);
    }

    @Test
    public void getGsonBuilder_should_return_builder() {
        assertNotNull(ParticipantExamReportDto.getGsonBuilder());
    }
}