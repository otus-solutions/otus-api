package br.org.otus.laboratory.participant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.gson.Gson;

@RunWith(JUnit4.class)
public class LaboratoryParticipantTest {

	private LaboratoryParticipant laboratoryParticipant;
	
	@Before
	public void setup() {	
		String json = "" 
				+"{\"laboratory\":{"
					+"\"recruitment_number\":123456,"
					+"\"cq_group\":\"CQ_1\","
					+"\"tubes\":["
					+ "{"
						+"\"code\":261116141,"
						+"\"type\":\"GEL\","
						+"\"aliquotes\":[]"
					+ "}],"
					+"\"exams\":[]"		          
	            +"}}";

		laboratoryParticipant = new Gson().fromJson(json, LaboratoryParticipant.class);

	}

	@Test
	public void should_deserialize_for_TubeDefinition() {
		Long recNumber = laboratoryParticipant.getRecruitmentNumber();
		System.out.println(recNumber);
//		assertThat(tubeDef.get(0).getName(), equalTo("GEL") );
	}
}
