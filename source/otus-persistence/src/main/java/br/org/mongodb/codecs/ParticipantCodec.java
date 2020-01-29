package br.org.mongodb.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

public class ParticipantCodec implements Codec<Participant> {

  @Override
  public void encode(BsonWriter writer, Participant value, EncoderContext encoderContext) {
    writer.writeStartDocument();

    writer.writeInt64("recruitmentNumber", value.getRecruitmentNumber());
    writer.writeString("name", value.getName());
    writer.writeString("sex", value.getSex().toString());

    writer.writeStartDocument("birthdate");
    writer.writeString("objectType", value.getBirthdate().getObjectType());
    writer.writeString("value", value.getBirthdate().getFormattedValue());
    writer.writeEndDocument();

    writer.writeStartDocument("fieldCenter");
    writer.writeString("acronym", value.getFieldCenter().getAcronym());

    writer.writeEndDocument();

    writer.writeBoolean("late", value.getLate());
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

    reader.readStartDocument();
    reader.readString("objectType");
    String formattedBirthdate = reader.readString("value");
    participant.setBirthdate(new ImmutableDate(formattedBirthdate));
    reader.readEndDocument();

    reader.readStartDocument();
    String fieldCenterAcronym = reader.readString("acronym");
    FieldCenter fieldCenter = new FieldCenter();
    fieldCenter.setAcronym(fieldCenterAcronym);
    participant.setFieldCenter(fieldCenter);

    reader.readEndDocument();

    Boolean late = reader.readBoolean("late");
    participant.setLate(late);

    reader.readEndDocument();

    return participant;
  }

  @Override
  public Class<Participant> getEncoderClass() {
    return Participant.class;
  }

}
