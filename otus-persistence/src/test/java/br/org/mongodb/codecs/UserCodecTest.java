package br.org.mongodb.codecs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import br.org.otus.model.User;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ObjectId.class, User.class})
public class UserCodecTest {
    private static final String EMAIL = "teste@teste.com";
    private static final String UUIDString = "1-2-3-4-5";

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

    private EncoderContext encoderContext;
    private DecoderContext decoderContext;

    @Test
    public void method_encode_should_evocate_functions_that_started_and_finished_BsonWriterDocument() throws Exception {
        encoderContext = EncoderContext.builder().build();
        PowerMockito.when(user.getEmail()).thenReturn(EMAIL);
        PowerMockito.when(user.getUuid()).thenReturn(uuid);
        UserCodec.encode(writer, user, encoderContext);
        verify(writer).writeStartDocument();
        verify(writer).writeEndDocument();
    }

    @Test
    public void method_decode_should_evocate_functions_that_started_and_finished_BsonReaderDocument() throws Exception {
        decoderContext = decoderContext.builder().build();
        PowerMockito.when(reader.readString("uuid")).thenReturn(UUIDString);
        UserCodec.decode(reader,  decoderContext);
        verify(reader).readStartDocument();
        verify(reader).readEndDocument();
    }

    @Test
    public void method_getEncoderClass_should_return_class_user() throws Exception {
        assertEquals(UserCodec.getEncoderClass(), User.class);
    }
}
