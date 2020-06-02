package br.org.otus.communication.issuedto;

import br.org.otus.communication.IssueMessageDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class IssueMessageDTOTest {
  private static final String EMAIL = "email@email.com";
  private static final String MESSAGE_JSON = "{\n" +
    "\"objectType\": \"Issue\",\n" +
    "\"sender\": \"email do token\",\n" +
    "\"title\": \"Não consigo preencher a atividade TCLEC\",\n" +
    "\"message\": \"Quando tento responder uma pergunta, não consigo inserir a resposta\",\n" +
    "\"creationDate\": \"22/01/20\",\n" +
    "\"status\": \"OPEN\"\n" +
    "}";

  private IssueMessageDTO issueMessageDTO;

  @Before
  public void setUp() {
    issueMessageDTO = new IssueMessageDTO();
  }

  @Test
  public void setSender_method_should() {
    IssueMessageDTO  issueMessage = issueMessageDTO.deserialize(MESSAGE_JSON);
    issueMessage.setSender(EMAIL);
    assertEquals(EMAIL, issueMessage.getSender());
  }

  @Test
  public void serialize_method_should() {
    assertTrue(IssueMessageDTO.serialize(issueMessageDTO) instanceof String);
  }

  @Test
  public void deserialize_method_should() {
    assertTrue(IssueMessageDTO.deserialize(MESSAGE_JSON) instanceof IssueMessageDTO);
  }

}