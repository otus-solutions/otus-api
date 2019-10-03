package br.org.mongodb.codecs;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.ccem.otus.model.FileUploader;
import org.ccem.otus.participant.model.Participant;

import br.org.otus.model.User;

public class OtusCodecProvider implements CodecProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
		if (clazz == Participant.class) {
			return (Codec<T>) new ParticipantCodec();
		}
		if (clazz == User.class) {
			return (Codec<T>) new UserCodec();
		}
		if (clazz == FileUploader.class) {
			return (Codec<T>) new FileUploaderCodec();
		}
		return null;
	}

}
