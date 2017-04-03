package org.ccem.otus.model;

import static org.junit.Assert.assertEquals;

import org.ccem.otus.participant.importation.model.ParticipantImport;
import org.ccem.otus.participant.model.Participant;

import org.junit.Test;
import org.mockito.Mock;

public class ParticipantTest {

	Participant participant = new Participant(5001007l);
	Participant participantRecruitmentNumberNull = new Participant(null);
	Participant participantThat = new Participant(1063154l);
	Participant participantEqualTrue = participant;
	Participant participantNull = null;

	@Mock
	ParticipantImport participantClassDifferent;

	@Test
	public void method_equals_should_return_true_when_partipants_are_equal() {
		assertEquals(true, participant.equals(participantEqualTrue));
	}

	@Test
	public void method_equals_should_return_false_when_past_paramenter_is_null() {
		assertEquals(false, participant.equals(participantNull));
	}

	@Test
	public void method_equals_should_return_false_when_past_paramenter_is_different_class() {
		assertEquals(false, participant.equals(participantClassDifferent));
	}

	@Test
	public void method_equals_must_compare_attribute_number_of_recruitment_when_passed_parameter_is_different() {
		assertEquals(false, participant.equals(participantThat));
	}

	@Test
	public void method_equals_should_return_true_when_recruimentNumbers_are_null_() {
		assertEquals(true, participantRecruitmentNumberNull.equals(participantRecruitmentNumberNull));
	}

	@Test
	public void method_hashCode_sould_codify_recruitmentNumber_if_not_null() {
		assertEquals(5001007, participant.hashCode());

	}

	@Test
	public void method_hashCode_sould_not_codify_recruitmentNumber_is_null() {
		assertEquals(0, participantRecruitmentNumberNull.hashCode());

	}

}
