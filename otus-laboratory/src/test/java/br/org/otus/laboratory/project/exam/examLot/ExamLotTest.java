package br.org.otus.laboratory.project.exam.examLot;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examLot.validators.ExamLotValidator;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExamLot.class)
public class ExamLotTest {

    private static final String BIOCHEMICAL_SERUM = "BIOCHEMICAL_SERUM";
    private static final String LOT_CODE = "2131244534";
    private static final int LOT_HASHCODE = 1101407489;
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2017, 02, 23, 13, 26);
    private static final String OBJECT_TYPE = "ExamLot";
    private static final String OPERATOR = "test";
    private static final ObjectId OBJECT_ID = new ObjectId();

    @InjectMocks
    private ExamLot examLot;
    @Mock
    private Aliquot aliquot1;
    @Mock
    private Aliquot aliquot2;
    @Mock
    private FieldCenter fieldCenter1;
    @Mock
    private ArrayList<Aliquot> aliquotList;

    @Test
    public void getters_and_setters() {
        aliquotList = new ArrayList<>();
        aliquotList.add(aliquot1);

        examLot = new ExamLot();
        Whitebox.setInternalState(examLot,"_id",OBJECT_ID);
        examLot.setAliquotList(aliquotList);
        examLot.setAliquotName(BIOCHEMICAL_SERUM);
        examLot.setCode(LOT_CODE);
        examLot.setFieldCenter(fieldCenter1);
        examLot.setRealizationDate(LOCAL_DATE_TIME);
        examLot.setOperator(OPERATOR);

        assertEquals(aliquotList,examLot.getAliquotList());
        assertEquals(BIOCHEMICAL_SERUM,examLot.getAliquotName());
        assertEquals(LOT_CODE,examLot.getCode());
        assertEquals(fieldCenter1,examLot.getFieldCenter());
        assertEquals(LOCAL_DATE_TIME,examLot.getRealizationDate());
        assertEquals(OPERATOR,examLot.getOperator());
        assertEquals(OBJECT_TYPE,examLot.getObjectType());
        assertEquals(OBJECT_ID,examLot.getLotId());
    }

    @Test
    public void method_getAliquotCodeList_should_return_array_of_aliquot_codes() {
        when(aliquot1.getCode()).thenReturn("354005011");
        when(aliquot2.getCode()).thenReturn("354005012");
        ArrayList<String> caodeList = new ArrayList<>();
        caodeList.add("354005011");
        caodeList.add("354005012");
        aliquotList = new ArrayList<>();
        aliquotList.add(aliquot1);
        aliquotList.add(aliquot2);
        examLot.setAliquotList(aliquotList);
        assertEquals(caodeList,examLot.getAliquotCodeList());
    }

    @Test
    public void method_getNewAliquotCodeList_should_return_array_of_new_aliquot_code() {
        when(aliquot1.getCode()).thenReturn("354005011");
        ArrayList<String> caodeList = new ArrayList<>();
        caodeList.add("354005011");
        caodeList.add("354005012");
        ArrayList<String> resultArray = new ArrayList<>();
        resultArray.add("354005012");
        aliquotList = new ArrayList<>();
        aliquotList.add(aliquot1);
        examLot.setAliquotList(aliquotList);
        assertEquals(resultArray,examLot.getNewAliquotCodeList(caodeList));
    }

    @Test
    public void method_getNewAliquotCodeList_should_return_empty_array() {
        when(aliquot1.getCode()).thenReturn("354005011");
        ArrayList<String> caodeList = new ArrayList<>();
        caodeList.add("354005011");
        aliquotList = new ArrayList<>();
        aliquotList.add(aliquot1);
        examLot.setAliquotList(aliquotList);
        assertEquals(new ArrayList<>(),examLot.getNewAliquotCodeList(caodeList));
    }

    @Test
    public void method_getRemovedAliquotCodeList_should_return_array_of_removed_aliquot_code() {
        when(aliquot1.getCode()).thenReturn("354005011");
        when(aliquot2.getCode()).thenReturn("354005012");
        ArrayList<String> caodeList = new ArrayList<>();
        caodeList.add("354005011");
        ArrayList<String> resultArray = new ArrayList<>();
        resultArray.add("354005012");
        aliquotList = new ArrayList<>();
        aliquotList.add(aliquot1);
        aliquotList.add(aliquot2);
        examLot.setAliquotList(aliquotList);
        assertEquals(resultArray,examLot.getRemovedAliquotCodeList(caodeList));
    }

    @Test
    public void method_getRemovedAliquotCodeList_should_return_array_with_all_aliquots() {
        when(aliquot1.getCode()).thenReturn("354005011");
        when(aliquot2.getCode()).thenReturn("354005012");
        ArrayList<String> caodeList = new ArrayList<>();
        ArrayList<String> resultArray = new ArrayList<>();
        resultArray.add("354005011");
        resultArray.add("354005012");
        aliquotList = new ArrayList<>();
        aliquotList.add(aliquot1);
        aliquotList.add(aliquot2);
        examLot.setAliquotList(aliquotList);
        assertEquals(resultArray,examLot.getRemovedAliquotCodeList(caodeList));
    }

    @Test
    public void method_hashCode_should_return_lot_code_hashCode() {
        examLot.setCode(LOT_CODE);
        assertEquals(LOT_HASHCODE,examLot.hashCode());
    }
}
