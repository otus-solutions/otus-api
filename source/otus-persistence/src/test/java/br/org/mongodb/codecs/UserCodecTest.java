package br.org.mongodb.codecs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import br.org.otus.model.User;
import org.bson.BsonInvalidOperationException;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.UUID;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ObjectId.class, User.class})
public class UserCodecTest {
  private static final String EMAIL = "teste@teste.com";
  private static final String UUIDString = "1-2-3-4-5";
  private static final String PASSWORD = "123456";
  private static final String PHONE = "33655598";
  private static final Boolean ENABLE = true;
  private static final Boolean EXTRACTIONENABLED = true;
  private static final ArrayList EXTRACTIONIPS = new ArrayList();
  private static final Boolean ADMIN = true;
  private static final String NAME = "name";
  private static final String SURNAME = "surname";
  private static final String FIELDCENTERACRONYM = "FC";
  private static final String IPFORTEST = "192.168.0.1";
  private User userSpy = spy(new User());

  @InjectMocks
  private UserCodec UserCodec;
  @Mock
  private BsonWriter writer;
  @Mock
  private UUID uuid;
  @Mock
  private BsonReader reader;
  @Mock
  private User user;
  @Mock
  private FieldCenter FIELDCENTER;

  @Test
  public void method_encode_should_evocate_functions_that_started_and_finished_BsonWriterDocument() throws Exception {
    EncoderContext encoderContext = EncoderContext.builder().build();
    PowerMockito.when(user.getEmail()).thenReturn(EMAIL);
    PowerMockito.when(user.getUuid()).thenReturn(uuid);
    UserCodec.encode(writer, user, encoderContext);
    verify(writer).writeStartDocument();
    verify(writer).writeEndDocument();
  }

  @Test
  public void method_encode_should_evocate_functions_that_insert_user_attributes() throws Exception {
    EXTRACTIONIPS.add(IPFORTEST);
    EncoderContext encoderContext = EncoderContext.builder().build();
    PowerMockito.when(user.getEmail()).thenReturn(EMAIL);
    PowerMockito.when(user.getUuid()).thenReturn(uuid);
    PowerMockito.when(user.getPassword()).thenReturn(PASSWORD);
    PowerMockito.when(user.getPhone()).thenReturn(PHONE);
    PowerMockito.when(user.isEnable()).thenReturn(ENABLE);
    PowerMockito.when(user.isExtractionEnabled()).thenReturn(EXTRACTIONENABLED);
    PowerMockito.when(user.getSurname()).thenReturn(SURNAME);
    PowerMockito.when(user.getName()).thenReturn(NAME);
    PowerMockito.when(user.isAdmin()).thenReturn(ADMIN);
    PowerMockito.when(user.getFieldCenter()).thenReturn(FIELDCENTER);
    PowerMockito.when(user.getFieldCenter().getAcronym()).thenReturn(FIELDCENTERACRONYM);
    PowerMockito.when(user.getExtractionIps()).thenReturn(EXTRACTIONIPS);
    UserCodec.encode(writer, user, encoderContext);
    verify(writer).writeString("password", PASSWORD);
    verify(writer).writeString("phone", PHONE);
    verify(writer).writeBoolean("enable", ENABLE);
    verify(writer).writeBoolean("extraction", EXTRACTIONENABLED);
    verify(writer).writeString("surname", SURNAME);
    verify(writer).writeString("name", NAME);
    verify(writer).writeBoolean("adm", ADMIN);
    verify(writer).writeString("acronym", FIELDCENTERACRONYM);
    verify(writer).writeString(IPFORTEST);
  }

  @Test
  public void method_decode_should_evocate_functions_that_started_and_finished_BsonReaderDocument() throws Exception {
    PowerMockito.when(reader.readString("email")).thenReturn(EMAIL);
    DecoderContext decoderContext = DecoderContext.builder().build();
    PowerMockito.when(reader.readString("uuid")).thenReturn(UUIDString);
    PowerMockito.when(reader.readBsonType()).thenReturn(BsonType.END_OF_DOCUMENT);
    UserCodec.decode(reader, decoderContext);
    verify(reader).readStartDocument();
    verify(reader).readEndDocument();
  }

  @Test
  public void method_decode_should_evocate_functions_that_reade_user_attributes() throws Exception {
    DecoderContext decoderContext = DecoderContext.builder().build();
    PowerMockito.when(reader.readString("password")).thenReturn(PASSWORD);
    PowerMockito.when(reader.readString("phone")).thenReturn(PHONE);
    PowerMockito.when(reader.readBoolean("enable")).thenReturn(ENABLE);
    PowerMockito.when(reader.readBoolean("extraction")).thenReturn(EXTRACTIONENABLED);
    PowerMockito.when(reader.readString("surname")).thenReturn(SURNAME);
    PowerMockito.when(reader.readString("name")).thenReturn(NAME);
    PowerMockito.when(reader.readBoolean("adm")).thenReturn(ADMIN);
    PowerMockito.when(reader.readString("uuid")).thenReturn(UUIDString);
    PowerMockito.when(reader.readString("email")).thenReturn(EMAIL);
    PowerMockito.when(reader.readString("acronym")).thenReturn(FIELDCENTERACRONYM);
    PowerMockito.when(reader.readBsonType()).thenReturn(BsonType.END_OF_DOCUMENT);
    doThrow(new BsonInvalidOperationException("teste")).when(reader).readNull("fieldCenter");
    doThrow(new BsonInvalidOperationException("teste")).when(reader).readNull("extraction");
    User userLocal = UserCodec.decode(reader, decoderContext);
    assertEquals(userLocal.getEmail(), EMAIL);
    assertEquals(userLocal.getPassword(), PASSWORD);
    assertEquals(userLocal.getPhone(), PHONE);
    assertEquals(userLocal.isEnable(), ENABLE);
    assertEquals(userLocal.isExtractionEnabled(), EXTRACTIONENABLED);
    assertEquals(userLocal.getName(), NAME);
    assertEquals(userLocal.getFieldCenter().getAcronym(), FIELDCENTERACRONYM);
    assertEquals(userLocal.getUuid().toString(), UUID.fromString(UUIDString).toString());
  }

  @Test
  public void method_getEncoderClass_should_return_class_user() throws Exception {
    assertEquals(UserCodec.getEncoderClass(), User.class);
  }
}
