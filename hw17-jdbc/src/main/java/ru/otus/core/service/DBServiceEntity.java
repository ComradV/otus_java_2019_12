package ru.otus.core.service;

import ru.otus.core.model.User;

import java.util.Optional;

public interface DBServiceEntity<T> {

  long saveEntity(T entity);

  Optional<T> getEntity(long id);

}
