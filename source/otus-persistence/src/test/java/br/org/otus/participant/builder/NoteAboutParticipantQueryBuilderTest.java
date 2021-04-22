package br.org.otus.participant.builder;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.searchSettingsDto.SearchSettingsDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
public class NoteAboutParticipantQueryBuilderTest {

  private static final ObjectId USER_OID = new ObjectId("5a33cb4a28f10d1043710f00");
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final int SKIP = 0;
  private static final int LIMIT = 5;

  private NoteAboutParticipantQueryBuilder queryBuilder;

  @Mock
  private SearchSettingsDto searchSettingsDto;

  @Before
  public void setUp(){
    queryBuilder = new NoteAboutParticipantQueryBuilder();

    doReturn(SKIP).when(searchSettingsDto).getCurrentQuantity();
    doReturn(LIMIT).when(searchSettingsDto).getQuantityToGet();
  }

  @Test
  public void getByRnQuery_method_test(){
    final String EXPECTED_QUERY = "[{" +
        "        \"$match\": {" +
        "            \"recruitmentNumber\": " + RECRUITMENT_NUMBER.toString() +
        "        }" +
        "    }," +
        "{" +
        "        \"$lookup\": {" +
        "            \"from\": \"user\"," +
        "            \"let\": {" +
        "                \"userId\": \"$userId\"" +
        "            }," +
        "            \"pipeline\": [" +
        "                {" +
        "                    \"$match\": {" +
        "                        \"$expr\": {" +
        "                            \"$eq\": [\"$_id\", \"$$userId\"]" +
        "                        }" +
        "                    }" +
        "                }," +
        "                {" +
        "                    \"$project\": {" +
        "                        \"name\": {" +
        "                            \"$concat\": [\"$name\", \" \", \"$surname\" ]" +
        "                        }" +
        "                    }" +
        "                }" +
        "            ]," +
        "            \"as\": \"user\"" +
        "        }" +
        "    }," +
        "{" +
        "        \"$addFields\": {" +
        "            \"userName\": {" +
        "                \"$ifNull\": [ { \"$arrayElemAt\": [\"$user.name\",0.0] }, null ]" +
        "            }," +
        "            \"isCreator\": {" +
        "                \"$eq\": [ {\"$toString\": \"$userId\"}, \"" + USER_OID.toHexString()+ "\"]" +
        "            }" +
        "        }" +
        "    }," +
        "{" +
        "        \"$project\": {" +
        "            \"userId\": 0.0," +
        "            \"user\": 0.0" +
        "        }" +
        "    }," +
        "{" +
        "      \"$sort\": {" +
        "            \"creationDate\": -1.0" +
        "        }" +
        "    }," +
        "{ \"$skip\": " + SKIP + ".0}," +
        "{ \"$limit\": " + LIMIT + ".0}"  + "]";
    ;
    assertEquals(
        EXPECTED_QUERY.replaceAll(" ", "").replaceAll("\"\"", "\" \""),
        new GsonBuilder().create().toJson(
            queryBuilder.getByRnQuery(USER_OID, RECRUITMENT_NUMBER, searchSettingsDto)
        ));
  }

  @Test
  public void getByRnQuery_method_test_should_consider_filters(){
    Map<String, Object> map = new HashMap();
    map.put("starred", true);

    doReturn(map).when(searchSettingsDto).getFilters();


    final String EXPECTED_QUERY = "[{" +
        "        \"$match\": {" +
        "            \"starred\": " + true + "," +
        "            \"recruitmentNumber\": " + RECRUITMENT_NUMBER.toString() +
        "        }" +
        "    }," +
        "{" +
        "        \"$lookup\": {" +
        "            \"from\": \"user\"," +
        "            \"let\": {" +
        "                \"userId\": \"$userId\"" +
        "            }," +
        "            \"pipeline\": [" +
        "                {" +
        "                    \"$match\": {" +
        "                        \"$expr\": {" +
        "                            \"$eq\": [\"$_id\", \"$$userId\"]" +
        "                        }" +
        "                    }" +
        "                }," +
        "                {" +
        "                    \"$project\": {" +
        "                        \"name\": {" +
        "                            \"$concat\": [\"$name\", \" \", \"$surname\" ]" +
        "                        }" +
        "                    }" +
        "                }" +
        "            ]," +
        "            \"as\": \"user\"" +
        "        }" +
        "    }," +
        "{" +
        "        \"$addFields\": {" +
        "            \"userName\": {" +
        "                \"$ifNull\": [ { \"$arrayElemAt\": [\"$user.name\",0.0] }, null ]" +
        "            }," +
        "            \"isCreator\": {" +
        "                \"$eq\": [ {\"$toString\": \"$userId\"}, \"" + USER_OID.toHexString()+ "\"]" +
        "            }" +
        "        }" +
        "    }," +
        "{" +
        "        \"$project\": {" +
        "            \"userId\": 0.0," +
        "            \"user\": 0.0" +
        "        }" +
        "    }," +
        "{" +
        "      \"$sort\": {" +
        "            \"creationDate\": -1.0" +
        "        }" +
        "    }," +
        "{ \"$skip\": " + SKIP + ".0}," +
        "{ \"$limit\": " + LIMIT + ".0}"  + "]";
    ;
    assertEquals(
        EXPECTED_QUERY.replaceAll(" ", "").replaceAll("\"\"", "\" \""),
        new GsonBuilder().create().toJson(
            queryBuilder.getByRnQuery(USER_OID, RECRUITMENT_NUMBER, searchSettingsDto)
        ));
  }

}
