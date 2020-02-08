package br.org.mongodb.codecs;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.ccem.otus.participant.model.Participant;

public class ParticipantCodecProvider implements CodecProvider {

  @SuppressWarnings("unchecked")
  @Override
  public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
    if (clazz == Participant.class) {
      return (Codec<T>) new ParticipantCodec();
    }
    return null;
  }

}
