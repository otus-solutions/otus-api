package org.ccem.otus.model.monitoring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ParticipantExamReportDtoTest {
    private List<String> EXAM_NAME = new ArrayList<>();
    private String PARTICIPANT_EXAMS = "{\"participantExams\":[]}";
    private ParticipantExamReportDto participantExamReportDto;
    private String participantExamReportDtoJson;

    @Before
    public void setUp() {
        participantExamReportDto = new ParticipantExamReportDto(EXAM_NAME);

        participantExamReportDtoJson = ParticipantExamReportDto.serialize(participantExamReportDto);
    }

    @Test
    public void participantExamReportDtoMethod_should_execute() {
        participantExamReportDto = ParticipantExamReportDto.deserialize(participantExamReportDtoJson);
        assertEquals(PARTICIPANT_EXAMS, ParticipantExamReportDto.serialize(participantExamReportDto));
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