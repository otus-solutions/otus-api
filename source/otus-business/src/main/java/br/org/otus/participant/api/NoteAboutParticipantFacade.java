package br.org.otus.participant.api;

import br.org.otus.model.User;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Authorization;
import br.org.otus.response.info.NotFound;
import br.org.otus.response.info.Validation;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.service.NoteAboutParticipantService;

import javax.inject.Inject;
import java.util.logging.Logger;

public class NoteAboutParticipantFacade {

  private static final Logger LOGGER = Logger.getLogger("br.org.otus.participant.api.NoteAboutParticipantFacade");

  @Inject
  private NoteAboutParticipantService noteAboutParticipantService;

  public String create(User user, String noteAboutParticipantJson){
    try{
      NoteAboutParticipant noteAboutParticipant = (new NoteAboutParticipant()).deserializeNonStatic(noteAboutParticipantJson);
      return noteAboutParticipantService.create(user.get_id(), noteAboutParticipant).toHexString();
    }
    catch (Exception e){
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void update(User user, String noteAboutParticipantJson){
    try{
      noteAboutParticipantService.update(
        user.get_id(),
        (new NoteAboutParticipant()).deserializeNonStatic(noteAboutParticipantJson));
    }
    catch(DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
    catch(ValidationException e){
      LOGGER.severe("User {" + user.get_id() + "} tried delete note about participant not created by him");
      throw new HttpResponseException(Authorization.build("You can't delete the note because you doesn't create it"));
    }
    catch (Exception e){
      LOGGER.severe(e.getMessage());
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void delete(User user, String noteAboutParticipantId){
    try{
      noteAboutParticipantService.delete(user.get_id(), new ObjectId(noteAboutParticipantId));
    }
    catch(DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
    catch(ValidationException e){
      LOGGER.severe("User {" + user.get_id() + "} tried delete note about participant not created by him");
      throw new HttpResponseException(Authorization.build("You can't delete the note because you doesn't create it"));
    }
    catch (Exception e){
      LOGGER.severe(e.getMessage());
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

}
