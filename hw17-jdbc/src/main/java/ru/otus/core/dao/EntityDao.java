package ru.otus.core.dao;

import java.util.Optional;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

public interface EntityDao<T> {
  Optional<T> findById(long id);

  long saveEntity(T entity);

  SessionManager getSessionManager();
}
