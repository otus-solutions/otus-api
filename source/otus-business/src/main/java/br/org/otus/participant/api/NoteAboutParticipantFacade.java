package br.org.otus.participant.api;

import br.org.otus.model.User;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Authorization;
import br.org.otus.response.info.Validation;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.service.NoteAboutParticipantService;

import javax.inject.Inject;

public class NoteAboutParticipantFacade {

  @Inject
  private NoteAboutParticipantService noteAboutParticipantService;

  public String create(User user, String noteAboutParticipantJson){
    try{
      NoteAboutParticipant noteAboutParticipant = (new NoteAboutParticipant()).deserializeNonStatic(noteAboutParticipantJson);
      noteAboutParticipant.setUserId(user.get_id());

//      return noteAboutParticipantService.create(noteAboutParticipant);

      return null;
    }
    catch (Exception e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void update(User user, String noteAboutParticipantJson){
    try{
//      NoteAboutParticipant noteAboutParticipant = (new NoteAboutParticipant()).deserializeNonStatic(noteAboutParticipantJson);
//      checkIfUserIsTheNoteCreator(user.get_id(), noteAboutParticipant);

//      return noteAboutParticipantService.create(noteAboutParticipant);

    }
    catch(SecurityException e){
      throw new HttpResponseException(Authorization.build(e.getMessage()));
    }
    catch (Exception e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void delete(User user, String noteAboutParticipantId){
    try{



    }
    catch(SecurityException e){
      throw new HttpResponseException(Authorization.build(e.getMessage()));
    }
    catch (Exception e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }


  private void checkIfUserIsTheNoteCreator(ObjectId userId, NoteAboutParticipant noteAboutParticipant){
    if(!noteAboutParticipant.getUserId().equals(userId)){
      throw new SecurityException("User is not the creator of note about participant");
    }
  }
}
