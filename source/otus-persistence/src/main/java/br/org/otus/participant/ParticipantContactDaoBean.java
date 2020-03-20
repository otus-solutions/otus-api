package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.*;
import org.ccem.otus.participant.persistence.ParticipantContactDao;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;

import java.util.zip.DataFormatException;

import static com.mongodb.client.model.Filters.eq;

public class ParticipantContactDaoBean extends MongoGenericDao<Document> implements ParticipantContactDao {

  private static final String COLLECTION_NAME = "participant_contact";
  private static final String RECRUITMENT_NUMBER_FIELD_NAME = "recruitmentNumber";

  public ParticipantContactDaoBean(){
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ObjectId create(ParticipantContact participantContact) {
    Document parsed = Document.parse(ParticipantContact.serialize(participantContact));
    collection.insertOne(parsed);
    return parsed.getObjectId(ID_FIELD_NAME);
  }

  @Override
  public void addNonMainEmail(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    checkDtoItemPositionExistence(participantContactDto);
    updateEmail(participantContactDto);
  }

  @Override
  public void addNonMainAddress(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    checkDtoItemPositionExistence(participantContactDto);
    updateAddress(participantContactDto);
  }

  @Override
  public void addNonMainPhoneNumber(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    checkDtoItemPositionExistence(participantContactDto);
    updatePhoneNumber(participantContactDto);
  }

  private void checkDtoItemPositionExistence(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    int lastItemRanking = getParticipantContact(participantContactDto.getObjectId())
      .getParticipantContactItemSetByType(participantContactDto.getType())
      .getPositionOfLastItem().getRanking();
    int newItemRanking = ParticipantContactPositionOptions.fromString(participantContactDto.getPosition()).getRanking();

    if(newItemRanking <= lastItemRanking){
      throw new DataFormatException(String.format("Its not possible add %s at %s position",
        participantContactDto.getType(), participantContactDto.getPosition()));
    }
  }

  @Override
  public void updateEmail(ParticipantContactDto participantContactDto) throws DataNotFoundException {
    ParticipantContactItem item = new ParticipantContactItem<Email>();
    item.setValue(new Email());
    updateContact(ParticipantContactTypeOptions.EMAIL, item, participantContactDto);
  }

  @Override
  public void updateAddress(ParticipantContactDto participantContactDto) throws DataNotFoundException {
    ParticipantContactItem item = new ParticipantContactItem<Address>();
    item.setValue(new Address());
    updateContact(ParticipantContactTypeOptions.ADDRESS, item, participantContactDto);
  }

  @Override
  public void updatePhoneNumber(ParticipantContactDto participantContactDto) throws DataNotFoundException {
    ParticipantContactItem item = new ParticipantContactItem<PhoneNumber>();
    item.setValue(new PhoneNumber());
    updateContact(ParticipantContactTypeOptions.PHONE, item, participantContactDto);
  }

  private void updateContact(ParticipantContactTypeOptions contactType, ParticipantContactItem item, ParticipantContactDto participantContactDto) throws DataNotFoundException {
    item.setFromLinkedTreeMap(participantContactDto.getContactItem());
    String fieldToUpdate = contactType.getName() + "." + participantContactDto.getPosition();
    UpdateResult updateResult = collection.updateOne(
      eq(ID_FIELD_NAME, participantContactDto.getObjectId()),
      new Document("$set",
        new Document(fieldToUpdate, Document.parse(ParticipantContactItem.serialize(item)))
      ));
    if(updateResult.getMatchedCount() == 0){
      throw new DataNotFoundException("Participant contact with id { " + participantContactDto.getIdStr() + " } was not found");
    }
  }



  @Override
  public void swapMainContactWithSecondary(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
//    ParticipantContact participantContact = get(participantContactDto.getObjectId());
//    String mainFieldToUpdate = extractMainFieldNameFromDtoType(participantContactDto.getType());
//    String secondaryFieldToUpdate = extractSecondaryFieldNameWithIndexFromDto(participantContactDto);
//
//    UpdateResult updateResult = collection.updateOne(
//      eq(ID_FIELD_NAME, participantContactDto.getObjectId()),
//      new Document("$set",
//        new HashMap<String, Object>() {
//          {
//            put(mainFieldToUpdate, getSecondaryParticipantContactItemFromDto(participantContact, participantContactDto).getAllMyAttributes());
//            put(secondaryFieldToUpdate, participantContact.getParticipantContactsItemByType(participantContactDto.getType()).getMain().getAllMyAttributes());
//          }
//        }
//      )
//    );
//
//    if(updateResult.getMatchedCount() == 0){
//      throw new DataNotFoundException("Participant contact with id { " + participantContactDto.getIdStr() + " } was not found");
//    }
  }

  @Override
  public void delete(ObjectId participantContactOID) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(eq(ID_FIELD_NAME, participantContactOID));
    if(deleteResult.getDeletedCount() == 0){
      throw new DataNotFoundException("Participant contact with id { " + participantContactOID.toString() + " } was not found");
    }
  }

  @Override
  public void deleteNonMainContact(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
//    ParticipantContact participantContact = get(participantContactDto.getObjectId());
//    String fieldToUpdate = extractSecondaryFieldNameFromDtoType(participantContactDto.getType());
//
//    UpdateResult updateResult = collection.updateOne(
//      eq(ID_FIELD_NAME, participantContactDto.getObjectId()),
//      new Document("$pull",
//        new Document(fieldToUpdate, getSecondaryParticipantContactItemFromDto(participantContact, participantContactDto).getContactValueAttribute()))
//    );
//
//    if(updateResult.getMatchedCount() == 0){
//      throw new DataNotFoundException("Participant contact with id { " + participantContactDto.getIdStr() + " } was not found");
//    }
  }

  @Override
  public ParticipantContact getParticipantContact(ObjectId participantContactOID) throws DataNotFoundException {
    try{
      Document result = collection.find(eq(ID_FIELD_NAME, participantContactOID)).first();
      return ParticipantContact.deserialize(result.toJson());
    }
    catch (NullPointerException e){
      throw new DataNotFoundException("No participant contact found for OID {" + participantContactOID.toString() + "}");
    }
  }

  @Override
  public ParticipantContact getParticipantContactByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
    try{
      Document result = collection.find(eq(RECRUITMENT_NUMBER_FIELD_NAME, recruitmentNumber)).first();
      return ParticipantContact.deserialize(result.toJson());
    }
    catch (NullPointerException e){
      throw new DataNotFoundException("No participant contact found with recruitment number {" + recruitmentNumber.toString() + "}");
    }
  }


}
