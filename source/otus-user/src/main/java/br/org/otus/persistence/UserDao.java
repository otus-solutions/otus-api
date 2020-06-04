package br.org.otus.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.model.User;

public interface UserDao {

  User fetchByEmail(String email) throws DataNotFoundException;

  User getById(ObjectId userId) throws DataNotFoundException;

  Boolean emailExists(String email);

  User findAdmin();

  List<User> fetchAll();

  void persist(User user);

  User update(User user);

  void updatePassword(String email, String password);

  Boolean exists(String email);
}
