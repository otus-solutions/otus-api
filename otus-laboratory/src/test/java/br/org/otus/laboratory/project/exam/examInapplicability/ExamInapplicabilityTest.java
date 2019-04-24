package br.org.otus.laboratory.project.exam.examInapplicability;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ExamInapplicabilityTest {
    private static final Long RN = Long.valueOf(7016098);
    private static final String NAME = "CÁLCIO - URINA AMOSTRA ISOLADA";

    private ExamInapplicability examInapplicability;
    private String examInapplicabilityJson;

    @Before
    public void setUp() {
        examInapplicability  = new ExamInapplicability();
        Whitebox.setInternalState(examInapplicability,"recruitmentNumber",RN);
        Whitebox.setInternalState(examInapplicability,"name",NAME);

        examInapplicabilityJson = ExamInapplicability.serialize(examInapplicability);
    }

    @Test
    public void serializeMethod_should_convert_objectModel_to_JsonString() {
         assertTrue(ExamInapplicability.serialize(examInapplicability)instanceof String);
    }

    @Test
    public void deserializeMethod_shold_convert_JsonString_to_objectModel() {
        assertTrue(ExamInapplicability.deserialize(examInapplicabilityJson)instanceof ExamInapplicability);
    }

    @Test
    public void getGsonBuilder_should_return_builder() {
        assertNotNull(ExamInapplicability.getGsonBuilder());
    }

    @Test
    public void getRecruitmentNumberMethod_should_check_in() {
        assertEquals(RN,examInapplicability.getRecruitmentNumber());
    }

    @Test
    public void getNameMethod_should_check_in() {
        assertEquals(NAME,examInapplicability.getName());
    }
}