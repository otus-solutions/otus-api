package br.org.otus.communication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class MessageDTOTest {
  private static final String EMAIL = "email@email.com";
  private static final String ID = "5e0658135b4ff40f8916d2b5";
  private static final String MESSAGE_JSON = "{\n" +
    "\"text\": \"Segunda mensagem: não entendi sua pergunta\",\n" +
    "\"sender\": \"5e0658135b4ff40f8916d2b5\"," +
    "\"issueId\":\"9247c29478234234\"\n" +
    "}\n";

  private MessageDTO messageDTO;


  @Test
  public void setSender_method_should_set_object() {
    MessageDTO message = MessageDTO.deserialize(MESSAGE_JSON);
    message.setSender(ID);
    assertEquals(ID, message.getSender());
  }

  @Test
  public void setGroup_method_should_set_object() {
    MessageDTO  message = MessageDTO.deserialize(MESSAGE_JSON);
    message.setIssueId(ID);
    assertEquals(ID, message.getIssueId());
  }

  @Test
  public void serialize_method_should_string() {
    assertTrue(MessageDTO.serialize(messageDTO) instanceof String);
  }

  @Test
  public void deserialize_method_should_MessageDTO() {
    assertTrue(MessageDTO.deserialize(MESSAGE_JSON) instanceof MessageDTO);
  }
}