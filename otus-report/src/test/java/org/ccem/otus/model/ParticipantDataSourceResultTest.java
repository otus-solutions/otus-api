package org.ccem.otus.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.google.gson.GsonBuilder;


@RunWith(PowerMockRunner.class)
//@PrepareForTest(ParticipantDataSourceResult.class)
public class ParticipantDataSourceResultTest {
	
	private ParticipantDataSourceResult participantDataSourceResult;
	private FieldCenter fieldCenterInstance;
	private ImmutableDate imutableDateInstance;
	private String participantDatasourceJson;
	private String participantDatasourceString ="{\"recruitmentNumber\":543535,\"name\":\"Joao\",\"sex\":\"masc\"}";
	
//	{"recruitmentNumber":543535,"name":"Joao","sex":"masc","fieldCenter":{"acronym":"RS"}}
	
	@Before
	public void setUp() {
		fieldCenterInstance  = new FieldCenter();
		Whitebox.setInternalState(fieldCenterInstance, "acronym", "RS");
		
		imutableDateInstance = new ImmutableDate(LocalDate.now());		
		
		
		participantDataSourceResult = new ParticipantDataSourceResult();
		Whitebox.setInternalState(participantDataSourceResult, "name", "Joao");
		Whitebox.setInternalState(participantDataSourceResult, "sex", "masc");
		Whitebox.setInternalState(participantDataSourceResult, "fieldCenter", fieldCenterInstance);
		Whitebox.setInternalState(participantDataSourceResult, "birthdate", imutableDateInstance);
		
		participantDatasourceJson = new GsonBuilder().create().toJson(participantDataSourceResult);
		
		System.out.println(participantDatasourceJson);
		System.out.println(imutableDateInstance);
	}
	
	

	@Test
	public void methodSerialize() {
//		assertEquals(participantDatasourceString,participantDataSourceResult.serialize(participantDataSourceResult));
		
		
		
		
	}

	@Test
	public void methodDeserialize() {
		
	}

}
