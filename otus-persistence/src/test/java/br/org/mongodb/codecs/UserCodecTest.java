package br.org.mongodb.codecs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.org.otus.model.User;
import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FileUploader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ObjectId.class)
public class UserCodecTest {
    private static final String EMAIL = "teste@teste.com";
    @InjectMocks
    private UserCodec UserCodec;
    @Mock
    private BsonWriter writer;
    @Mock
    private UUID uuid;
    @Mock
    private BsonReader reader;
    @Mock
    private BsonBinary bsonBinary;
    @Mock
    private User user;
    @Mock
    private ObjectId objectId;
    private EncoderContext encoderContext;
    private DecoderContext decoderContext;

    @Test
    public void method_encode_should_evocate_functions_that_started_and_finished_BsonWriterDocument() throws Exception {
        encoderContext = EncoderContext.builder().build();
        when(user.getEmail()).thenReturn(EMAIL);
        when(user.getUuid()).thenReturn(uuid);
        UserCodec.encode(writer, user, encoderContext);
        verify(writer).writeStartDocument();
        verify(writer).writeEndDocument();
    }
}
