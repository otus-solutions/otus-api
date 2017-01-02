package br.org.mongodb.codecs;

import java.util.Date;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.Participant;
import org.ccem.otus.model.Sex;

public class ParticipantCodec implements Codec<Participant> {

	@Override
	public void encode(BsonWriter writer, Participant value, EncoderContext encoderContext) {
		writer.writeStartDocument();

		writer.writeInt64("recruitmentNumber", value.getRecruitmentNumber());
		writer.writeString("name", value.getName());
		writer.writeString("sex", value.getSex().toString());
		writer.writeDateTime("birthdate", value.getBirthdate().getTime());

		writer.writeStartDocument("fieldCenter");
		writer.writeString("acronym", value.getFieldCenter().getAcronym());
		writer.writeEndDocument();

		writer.writeEndDocument();
	}

	@Override
	public Participant decode(BsonReader reader, DecoderContext decoderContext) {
		reader.readStartDocument();
		reader.readObjectId("_id");
		
		long recruitmentNumber = reader.readInt64("recruitmentNumber");
		Participant participant = new Participant(recruitmentNumber);
		
		String name = reader.readString("name");
		participant.setName(name);
		
		String sex = reader.readString("sex");
		participant.setSex(Sex.valueOf(sex));
		
		long dateTime = reader.readDateTime("birthdate");
		participant.setBirthdate(new Date(dateTime));
		
		reader.readStartDocument();
		String fieldCenterAcronym = reader.readString("acronym");
		FieldCenter fieldCenter = new FieldCenter();
		fieldCenter.setAcronym(fieldCenterAcronym);
		participant.setFieldCenter(fieldCenter);
		reader.readEndDocument();
		
		reader.readEndDocument();
			
		return participant;
	}

	@Override
	public Class<Participant> getEncoderClass() {
		return Participant.class;
	}

}
