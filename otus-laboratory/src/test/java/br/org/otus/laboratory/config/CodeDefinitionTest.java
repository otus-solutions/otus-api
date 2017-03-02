package br.org.otus.laboratory.config;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class CodeDefinitionTest {

	private CodeDefinition codeDefinitions;
	private String json = "";
	
	@Before
	public void setup(){
		String json = "" 
				+ "{\"tube\": 1,"
				+ "\"pallet\": 2," 
				+ "\"cryotube\": 3,"
				+ "\"lastInsertion\":0}" ;

		codeDefinitions = new Gson().fromJson(json, CodeDefinition.class);

	}
	
	@Test
	public void test_me(){
		System.out.println(codeDefinitions.getLastInsertion());
	}
}
