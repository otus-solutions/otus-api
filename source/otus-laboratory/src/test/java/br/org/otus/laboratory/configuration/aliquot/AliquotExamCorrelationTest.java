package br.org.otus.laboratory.configuration.aliquot;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Map;

@RunWith(PowerMockRunner.class)
public class AliquotExamCorrelationTest {
    private static final String EXAM_NAME = "[URÉIA - SANGUE]";
    private static final String ALIQUOT_EXAM_CORRELATION = "{\"_id\" : \"5b2d5b936dcabba87ee60cfe\",\"objectType\" : \"AliquotExamCorrelation\", \"aliquots\" : [{\"name\" : \"a\",\"exams\" : [\"URÉIA - SANGUE\"]}]}";

    private AliquotExamCorrelation aliquotExamCorrelation;

    @Test
    public void method_getHashMap_should_map_aliquot_exam_correlation() {
        aliquotExamCorrelation = AliquotExamCorrelation.deserialize(ALIQUOT_EXAM_CORRELATION);
        Map map = aliquotExamCorrelation.getHashMap();
        assertEquals(EXAM_NAME,map.get("a").toString());
    }
}
