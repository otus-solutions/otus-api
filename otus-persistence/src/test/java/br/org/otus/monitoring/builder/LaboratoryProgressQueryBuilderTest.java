package br.org.otus.monitoring.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LaboratoryProgressQueryBuilderTest {
    private static String CENTER = "RS";
    private static ArrayList<String> CODELIST;

    private static Gson builder;

    @Before
    public void setUp() {
        builder = new GsonBuilder().create();
        CODELIST = new ArrayList<>();
        CODELIST.add("12345678");
    }

    @Test
    public void getQuantitativeByTypeOfAliquotsFirstPartialResultQuery() {
        String expectedQuery = "[{\"$match\":{\"role\":\"EXAM\",\"fieldCenter.acronym\":\"RS\"}},{\"$group\":{\"_id\":\"$name\",\"aliquots\":{\"$push\":{\"code\":\"$code\",\"transported\":{\"$cond\":{\"if\":{\"$ne\":[\"$transportationLotId\",null]},\"then\":1.0,\"else\":0.0}},\"prepared\":{\"$cond\":{\"if\":{\"$ifNull\":[\"$examLotId\",false]},\"then\":1.0,\"else\":{\"$cond\":{\"if\":{\"$ifNull\":[\"$examLotData.id\",false]},\"then\":1.0,\"else\":0.0}}}}}}}},{\"$unwind\":\"$aliquots\"},{\"$group\":{\"_id\":\"$_id\",\"transported\":{\"$sum\":\"$aliquots.transported\"},\"prepared\":{\"$sum\":\"$aliquots.prepared\"}}},{\"$group\":{\"_id\":{},\"quantitativeByTypeOfAliquots\":{\"$push\":{\"title\":\"$_id\",\"transported\":\"$transported\",\"prepared\":\"$prepared\"}}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().getQuantitativeByTypeOfAliquotsFirstPartialResultQuery(CENTER)));
    }

    @Test
    public void getPendingResultsByAliquotFirstPartialResultQuery() {
        String expectedQuery = "[{\"$match\":{\"code\":{\"$nin\":[\"12345678\"]},\"fieldCenter.acronym\":\"RS\"}},{\"$group\":{\"_id\":\"$name\",\"waiting\":{\"$sum\":1.0}}},{\"$group\":{\"_id\":{},\"pendingResultsByAliquot\":{\"$push\":{\"title\":\"$_id\",\"waiting\":\"$waiting\"}}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotFirstPartialResultQuery(CODELIST, CENTER)));
    }

    @Test
    public void getPendingResultsByAliquotSecondPartialResultQuery() {
        String expectedQuery = "[{\"$match\":{\"code\":{\"$in\":[\"12345678\"]},\"fieldCenter.acronym\":\"RS\"}},{\"$group\":{\"_id\":\"$name\",\"received\":{\"$sum\":1.0}}},{\"$group\":{\"_id\":{},\"pendingResultsByAliquot\":{\"$push\":{\"title\":\"$_id\",\"received\":\"$received\"}}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotSecondPartialResultQuery(CODELIST,CENTER)));
    }

    @Test
    public void getQuantitativeByTypeOfAliquotsSecondPartialResultQuery() {
        String expectedQuery = "[{\"$match\":{\"code\":{\"$in\":[\"12345678\"]}}},{\"$group\":{\"_id\":\"$name\",\"received\":{\"$sum\":1.0}}},{\"$group\":{\"_id\":{},\"quantitativeByTypeOfAliquots\":{\"$push\":{\"title\":\"$_id\",\"received\":\"$received\"}}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().getQuantitativeByTypeOfAliquotsSecondPartialResultQuery(CODELIST)));
    }

    @Test
    public void getAliquotCodesInExamLotQuery() {
        String expectedQuery = "[{\"$match\":{\"aliquotCode\":{\"$in\":[\"12345678\"]}}},{\"$group\":{\"_id\":\"$examId\",\"aliquotCodes\":{\"$addToSet\":\"$aliquotCode\"}}},{\"$unwind\":\"$aliquotCodes\"},{\"$group\":{\"_id\":{},\"aliquotCodes\":{\"$addToSet\":\"$aliquotCodes\"}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().getAliquotCodesInExamLotQuery(CODELIST)));
    }

    @Test
    public void getPendingAliquotsCsvDataQuery() {
        String expectedQuery = "[{\"$match\":{\"code\":{\"$nin\":[\"12345678\"]},\"fieldCenter.acronym\":\"RS\",\"role\":\"EXAM\"}},{\"$project\":{\"code\":\"$code\",\"transported\":{\"$cond\":{\"if\":{\"$ne\":[\"$transportationLotId\",null]},\"then\":1.0,\"else\":0.0}},\"prepared\":{\"$cond\":{\"if\":{\"$ifNull\":[\"$examLotId\",false]},\"then\":1.0,\"else\":{\"$cond\":{\"if\":{\"$ifNull\":[\"$examLotData.id\",false]},\"then\":1.0,\"else\":0.0}}}}}},{\"$group\":{\"_id\":{},\"pendingAliquotsCsvData\":{\"$push\":{\"aliquot\":\"$code\",\"transported\":\"$transported\",\"prepared\":\"$prepared\"}}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().getPendingAliquotsCsvDataQuery(CODELIST,CENTER)));
    }

    @Test
    public void getDataByExamQuery() {
        String expectedQuery = "[{\"$match\":{\"aliquotCode\":{\"$in\":[\"12345678\"]}}},{\"$group\":{\"_id\":{\"examId\":\"$examId\",\"examName\":\"$examName\"}}},{\"$group\":{\"_id\":\"$_id.examName\",\"received\":{\"$sum\":1.0}}},{\"$group\":{\"_id\":{},\"examsQuantitative\":{\"$push\":{\"title\":\"$_id\",\"exams\":\"$received\"}}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().getDataByExamQuery(CODELIST)));
    }

    @Test
    public void fetchAllAliquotCodesQuery() {
        String expectedQuery = "[{\"$match\":{\"fieldCenter.acronym\":\"RS\"}},{\"$group\":{\"_id\":{},\"aliquotCodes\":{\"$addToSet\":\"$code\"}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesQuery(CENTER)));
    }

    @Test
    public void fetchAllAliquotCodesInExamsQuery() {
        String expectedQuery = "[{\"$match\":{\"aliquotValid\":true}},{\"$group\":{\"_id\":\"$examId\",\"aliquotCodes\":{\"$addToSet\":\"$aliquotCode\"}}},{\"$unwind\":\"$aliquotCodes\"},{\"$group\":{\"_id\":{},\"aliquotCodes\":{\"$addToSet\":\"$aliquotCodes\"}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery()));
    }

    @Test
    public void getCSVOfOrphansByExamQuery() {
        String expectedQuery = "[{\"$match\":{\"aliquotValid\":false}},{\"$group\":{\"_id\":{\"examId\":\"$examId\",\"aliquotCode\":\"$aliquotCode\",\"examName\":\"$examName\"}}},{\"$group\":{\"_id\":{},\"orphanExamsCsvData\":{\"$push\":{\"aliquotCode\":\"$_id.aliquotCode\",\"examName\":\"$_id.examName\"}}}}]";
        assertEquals(expectedQuery, builder.toJson(new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery()));
    }
}
