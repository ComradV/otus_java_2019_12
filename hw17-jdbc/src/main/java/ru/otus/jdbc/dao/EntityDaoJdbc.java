package ru.otus.jdbc.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

import jdk.jshell.spi.ExecutionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.JDBCMapper;
import ru.otus.core.dao.EntityDao;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.jdbc.DbExecutor;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

public class EntityDaoJdbc<T> implements EntityDao<T> {
  private static Logger logger = LoggerFactory.getLogger(EntityDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<T> dbExecutor;
  private final JDBCMapper<T> jdbcMapper;

  public EntityDaoJdbc(Class<T> type, SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) throws NoSuchMethodException {
    this.jdbcMapper = new JDBCMapper<T>(type, sessionManager, dbExecutor);

    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
  }


  @Override
  public Optional<T> findById(long id) {
    try {
      return jdbcMapper.load(id);

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }


  @Override
  public long saveEntity(T entity) {
    try {
//      return dbExecutor.insertRecord(getConnection(), "insert into user(name) values (?)", Collections.singletonList(entity.getName()));//TODO здесь изменить для выполнения ДЗ!
//      return dbExecutor.insertRecord()
      throw new UnsupportedOperationException();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public SessionManager getSessionManager() {
    return jdbcMapper.getSessionManager();
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }

}
