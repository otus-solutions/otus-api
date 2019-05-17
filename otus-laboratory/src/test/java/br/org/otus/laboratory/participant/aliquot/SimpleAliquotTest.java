package br.org.otus.laboratory.participant.aliquot;

import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotContainer;
import br.org.otus.laboratory.configuration.collect.aliquot.enums.AliquotRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class SimpleAliquotTest {
    public static final String OBJECT_TYPE = "Aliquot";
    public static final String CODE = "334000000";
    public static final String NAME = "BIOCHEMICAL_SERUM";
    public static final String ALIQUOT_JSON_STRING = "{\"objectType\":\"Aliquot\",\"code\":\"334000000\",\"name\":\"BIOCHEMICAL_SERUM\",\"container\":\"PALLET\",\"role\":\"EXAM\",\"aliquotCollectionData\":{\"objectType\":\"AliquotCollectionData\",\"metadata\":\"\",\"operator\":\"nando.souza97@hotmail.com\",\"time\":\"2017-10-09T18:30:06.811Z\",\"processing\":null},\"aliquotHistory\":" +
            "[{" +
            "\"objectType\":\"AliquotEvent\"," +
            "\"type\":\"CONVERTED STORAGE\"," +
            "\"userEmail\":\"otus@gmail.com\"," +
            "\"description\":\"Falta de material para completar os exames\"," +
            "\"date\":\"2019-05-14T12:36:23.631Z\"" +
            "}]" +
            "}";

    @Mock
    private SimpleAliquot simpleAliquotFromJson;

    @Before
    public void setUp() {
        simpleAliquotFromJson = SimpleAliquot.deserialize(ALIQUOT_JSON_STRING);
    }

    @Test
    public void method_getObjectType_should_return_objectType() {
        assertEquals(OBJECT_TYPE, simpleAliquotFromJson.getObjectType());
    }

    @Test
    public void method_getCode_should_return_code() {
        assertEquals(CODE, simpleAliquotFromJson.getCode());
    }

    @Test
    public void method_getContainer_should_return_container() {
        assertEquals(AliquotContainer.PALLET, simpleAliquotFromJson.getContainer());
    }

    @Test
    public void method_getRole_should_return_role() {
        assertEquals(AliquotRole.EXAM, simpleAliquotFromJson.getRole());
    }

    @Test
    public void method_getName_should_return_name() {
        assertEquals(NAME, simpleAliquotFromJson.getName());
    }

    @Test
    public void method_getAliquotCollectionData_should_return_CollectionData() {
        assertEquals("AliquotCollectionData", simpleAliquotFromJson.getAliquotCollectionData().getObjectType());
    }

    @Test
    public void method_serialize_should_return_aliquotJson() {
        assertEquals(SimpleAliquot.serialize(simpleAliquotFromJson), ALIQUOT_JSON_STRING);
    }

    @Test
    public void getAliquotHistory_should_verify_size_array() {
        assertEquals(1, simpleAliquotFromJson.getAliquotHistory().size());
        assertEquals("CONVERTED STORAGE", simpleAliquotFromJson.getAliquotHistory().get(0).getType());
    }

}
