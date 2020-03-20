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

import java.util.HashMap;
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
    checkDtoItemPositionExistenceForAdd(participantContactDto);
    updateEmail(participantContactDto);
  }

  @Override
  public void addNonMainAddress(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    checkDtoItemPositionExistenceForAdd(participantContactDto);
    updateAddress(participantContactDto);
  }

  @Override
  public void addNonMainPhoneNumber(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    checkDtoItemPositionExistenceForAdd(participantContactDto);
    updatePhoneNumber(participantContactDto);
  }

  private void checkDtoItemPositionExistenceForAdd(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    int lastItemRanking = getParticipantContact(participantContactDto.getObjectId())
      .getParticipantContactItemSetByType(participantContactDto.getType())
      .getPositionOfLastItem().getRanking();
    int dtoItemRanking = ParticipantContactPositionOptions.fromString(participantContactDto.getPosition()).getRanking();

    if(dtoItemRanking <= lastItemRanking){
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
      new Document("$set", new Document(fieldToUpdate, Document.parse(ParticipantContactItem.serialize(item))))
    );
    if(updateResult.getMatchedCount() == 0){
      throw new DataNotFoundException("Participant contact with id { " + participantContactDto.getIdStr() + " } was not found");
    }
  }

  @Override
  public void swapMainContactWithSecondary(ParticipantContactDto participantContactDto) throws DataNotFoundException {
    String contactType = participantContactDto.getType();
    String mainFieldToUpdate = contactType + "." + ParticipantContactPositionOptions.MAIN.getName();
    String secondaryFieldToUpdate = contactType + "." + participantContactDto.getPosition();

    ParticipantContact participantContact = getParticipantContact(participantContactDto.getObjectId());
    String mainValueJson = ParticipantContactItem.serialize(participantContact
      .getParticipantContactItemSetByType(contactType)
      .getMain());
    String nonMainValueJson = ParticipantContactItem.serialize(participantContact
      .getParticipantContactItemSetByType(contactType)
      .getItemByPosition(ParticipantContactPositionOptions.fromString(participantContactDto.getPosition())));

    if(nonMainValueJson.equals("null")){
      throw new DataNotFoundException(String.format("Participant contact with id { %s } does not have %s %s",
        participantContactDto.getIdStr(), participantContactDto.getPosition(), contactType));
    }

    UpdateResult updateResult = collection.updateOne(
      eq(ID_FIELD_NAME, participantContactDto.getObjectId()),
      new Document("$set", new HashMap<String, Object>() {{
        put(mainFieldToUpdate, Document.parse(nonMainValueJson));
        put(secondaryFieldToUpdate, Document.parse(mainValueJson));
      }}));

    if (updateResult.getMatchedCount() == 0) {
      throw new DataNotFoundException("Participant contact with id { " + participantContactDto.getIdStr() + " } was not found");
    }
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
    ParticipantContact participantContact = getParticipantContact(participantContactDto.getObjectId());
    String contactType = participantContactDto.getType();

    ParticipantContactPositionOptions lastItemPosition = participantContact
      .getParticipantContactItemSetByType(contactType)
      .getPositionOfLastItem();
    int lastItemRanking = lastItemPosition.getRanking();
    int itemRankingToDelete = ParticipantContactPositionOptions.fromString(participantContactDto.getPosition()).getRanking();
    if(itemRankingToDelete > lastItemRanking){
      throw new DataFormatException(String.format("Its not possible delete %s at %s position", contactType, participantContactDto.getPosition()));
    }

    for (int ranking = itemRankingToDelete+1; ranking <= lastItemRanking; ranking++) {
      decreaseItemPosition(participantContact, contactType, ranking);
    }

    String fieldToDelete = contactType + "." + lastItemPosition.getName();
    UpdateResult updateResult = collection.updateOne(
      eq(ID_FIELD_NAME, participantContactDto.getObjectId()),
      new Document("$unset", new Document(fieldToDelete, ""))
    );
    if(updateResult.getMatchedCount() == 0){
      throw new DataNotFoundException("Participant contact with id { " + participantContactDto.getIdStr() + " } was not found");
    }
  }

  private void decreaseItemPosition(ParticipantContact participantContact, String contactType, int positionRanking){
    String prevFieldToUpdate = contactType + "." + ParticipantContactPositionOptions.fromInt(positionRanking-1).getName();
    String itemValueJson = ParticipantContactItem.serialize(participantContact
      .getParticipantContactItemSetByType(contactType)
      .getItemByPosition(ParticipantContactPositionOptions.fromInt(positionRanking)));
    collection.updateOne(
      eq(ID_FIELD_NAME, participantContact.getObjectId()),
      new Document("$set", new Document(prevFieldToUpdate, Document.parse(itemValueJson))
    ));
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
