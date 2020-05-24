package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.EntityDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceEntityImpl<T> implements DBServiceEntity<T> {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final EntityDao<T> entityDao;

  public DbServiceEntityImpl(EntityDao<T> entityDao) {
    this.entityDao = entityDao;
  }

  @Override
  public long saveEntity(T entity) {
    try (SessionManager sessionManager = entityDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long entityId = entityDao.saveEntity(entity);
        sessionManager.commitSession();

        logger.info("created entity: {}", entityId);
        return entityId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }


  @Override
  public Optional<T> getEntity(long id) {
    try (SessionManager sessionManager = entityDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<T> entityOptional = entityDao.findById(id);

        logger.info("user: {}", entityOptional.orElse(null));
        return entityOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

}
