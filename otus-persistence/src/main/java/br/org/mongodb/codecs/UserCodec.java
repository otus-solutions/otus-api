package br.org.mongodb.codecs;

import java.util.ArrayList;
import java.util.UUID;

import org.bson.BsonInvalidOperationException;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.ccem.otus.model.FieldCenter;

import br.org.otus.model.User;

import javax.print.DocFlavor;

public class UserCodec implements Codec<User> {

	@Override
	public void encode(BsonWriter writer, User user, EncoderContext arg2) {
		writer.writeStartDocument();

		writer.writeString("password", user.getPassword());
		writer.writeString("phone", user.getPhone());
		writer.writeBoolean("enable", user.isEnable());
		writer.writeString("surname", user.getSurname());
		writer.writeString("name", user.getName());
		writer.writeBoolean("adm", user.isAdmin());
		writer.writeString("uuid", user.getUuid().toString());
		writer.writeString("email", user.getEmail());

		if (user.getFieldCenter() == null) {
			writer.writeNull("fieldCenter");
		} else {
			writer.writeStartDocument("fieldCenter");
			writer.writeString("acronym", user.getFieldCenter().getAcronym());
			writer.writeEndDocument();
		}

		writer.writeBoolean("extraction", user.isExtractionEnabled());
		if (user.getExtractionToken() == "") {
			writer.writeNull("extractionToken");
		} else {
			writer.writeString("extractionToken", user.getExtractionToken());
		}
		if (user.getExtractionIps().isEmpty()) {
			writer.writeNull("extractionIps");
		} else {
			writer.writeStartArray("extractionIps");
			user.getExtractionIps().forEach(object -> writer.writeString(object.toString()));
			writer.writeEndArray();
		}

		writer.writeEndDocument();
	}

	@Override
	public User decode(BsonReader reader, DecoderContext decoderContext) {
		reader.readStartDocument();

		reader.readObjectId("_id");
		String password = reader.readString("password");
		String phone = reader.readString("phone");
		boolean enable = reader.readBoolean("enable");
		String surname = reader.readString("surname");
		String name = reader.readString("name");
		boolean adm = reader.readBoolean("adm");
		String uuid = reader.readString("uuid");
		String email = reader.readString("email");

		String fieldCenterAcronym = "";
		try {
			reader.readNull("fieldCenter");
		} catch (BsonInvalidOperationException e) {
			reader.readStartDocument();
			fieldCenterAcronym = reader.readString("acronym");
			reader.readEndDocument();
		}

		boolean extraction = reader.readBoolean("extraction");

		String extractionToken = "";
		try {
			reader.readNull("extractionToken");
		} catch (BsonInvalidOperationException ef) {
			extractionToken = reader.readString();
		}

		ArrayList extractionIps = new ArrayList();
		try {
			reader.readNull("extractionIps");
		} catch (BsonInvalidOperationException ef) {
			reader.readStartArray();
			while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
				extractionIps.add(reader.readString());
			}
			reader.readEndArray();
		}

		reader.readEndDocument();

		User user = new User(UUID.fromString(uuid));
		user.setPassword(password);
		user.setPhone(phone);

		if (adm == true) {
			user.becomesAdm();
		}

		if (enable == true) {
			user.enable();
		}

		if (extraction == true) {
			user.enableExtraction();
			user.setExtractionToken(extractionToken);
			user.setExtractionIps(extractionIps);
		} else if (!extractionIps.isEmpty()) {
			user.setExtractionIps(extractionIps);
		}

		FieldCenter fieldCenter = new FieldCenter();
		if (!fieldCenterAcronym.isEmpty()) {
			fieldCenter.setAcronym(fieldCenterAcronym);
		}
		user.setFieldCenter(fieldCenter);
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);

		return user;
	}

	@Override
	public Class<User> getEncoderClass() {
		return User.class;
	}

}