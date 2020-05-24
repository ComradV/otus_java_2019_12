package ru.otus.core;

import org.javatuples.Quartet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.ReflectionHelper;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCMapper<T> {
  private static Logger logger = LoggerFactory.getLogger(JDBCMapper.class);

  private final Class<T> type;
  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<T> dbExecutor;
  private String createQueryText;
  private String updateQueryText;
  private String selectQueryText;
  private List<Quartet<Field, String, String, Integer>> fieldsInfo;
  private Field idField;
  Constructor<T> constructorWithId;

  public JDBCMapper(Class<T> type, SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) throws NoSuchMethodException {
    this.fieldsInfo = ReflectionHelper.getFields(type);
    this.idField = ReflectionHelper.getIdField(type);
    this.idField.setAccessible(true);

    constructorWithId  = type.getDeclaredConstructor(long.class);
    constructorWithId.setAccessible(true);

    this.type = type;
    this.createQueryText = getCreateQueryText();
    this.updateQueryText = getUpdateQueryText();
    this.selectQueryText = getSelectQueryText();
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;

  }

  private String getCreateQueryText() {
    StringBuilder sbFields = new StringBuilder();
    StringBuilder sbHolders = new StringBuilder();
    boolean firstField = true;
    for (Quartet<Field, String, String, Integer> fieldInfo : fieldsInfo) {
      if (fieldInfo.getValue0() != idField) {
        if (!firstField){ sbFields.append(","); sbHolders.append(",");}
        sbFields.append(fieldInfo.getValue1());
        sbHolders.append("?");
        firstField = false;
      }
    }
    return "insert into " + type.getSimpleName() +
            " (" + sbFields.toString() + ")"+
            " values ("+sbHolders.toString()+")";
  }
  private String getUpdateQueryText() {
    StringBuilder sbFields = new StringBuilder();
    boolean firstField = true;
    for (Quartet<Field, String, String, Integer> fieldInfo : fieldsInfo) {
      if (fieldInfo.getValue0() != idField) {
        if (!firstField) { sbFields.append(","); }
        sbFields.append(fieldInfo.getValue1()+"=?");
        firstField = false;
      }
    }
    return "update " + type.getSimpleName() +
            " set " + sbFields.toString() +
            " where " + idField.getName() +
            " = ?";
  }

  private String getSelectQueryText() {
    StringBuilder sbFields = new StringBuilder();
    boolean firstField = true;
    for (Quartet<Field, String, String, Integer> fieldInfo : fieldsInfo) {
      if (!firstField){ sbFields.append(",");}
      sbFields.append(fieldInfo.getValue1());
      firstField = false;
    }
    return "select " + sbFields.toString() +
            " from " + type.getSimpleName() +
            " where " + idField.getName() +
            " = ?";
  }

  public void create(T objectData) throws SQLException, IllegalAccessException {
    List<String> parameters = new ArrayList<>();
    Field currentField;
    for (Quartet<Field, String, String, Integer> fieldInfo : fieldsInfo) {
      currentField = fieldInfo.getValue0();
      if (currentField != idField) {
        currentField.setAccessible(true);
        parameters.add(currentField.get(objectData).toString());
      }
    }
    sessionManager.beginSession();
    long id = dbExecutor.insertRecord(getConnection(), createQueryText, parameters);
    idField.set(objectData, id);
    sessionManager.commitSession();
  }

  public void update(T objectData) throws SQLException, IllegalAccessException {
    List<String> parameters = new ArrayList<>();
    for (Quartet<Field, String, String, Integer> fieldInfo : fieldsInfo) {
      if (fieldInfo.getValue0() != idField) {
        parameters.add(fieldInfo.getValue0().get(objectData).toString());
      }
    }
    parameters.add(idField.get(objectData).toString());
    sessionManager.beginSession();
    dbExecutor.updateRecord(getConnection(), updateQueryText, parameters);
    sessionManager.commitSession();
  }

  public Optional<T> load(long id){//TODO make me!
    try {
      sessionManager.beginSession();
      return dbExecutor.selectRecord(getConnection(), selectQueryText, id, resultSet -> {
        try {
          if (resultSet.next()) {
            T loadedEntity = (T)constructorWithId.newInstance(id);
            Field field;
            for (Quartet<Field, String, String, Integer> fieldInfo: fieldsInfo){
              field = fieldInfo.getValue0();
              field.setAccessible(true);
              if(field != idField){
                field.set(loadedEntity, resultSet.getObject(fieldInfo.getValue1()));
              }
            }
            return loadedEntity;
          }
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
          throw new JDBCMapperException(e);
        }
        return null;
      });
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      sessionManager.rollbackSession();
    }
    return Optional.empty();
  }

  public static String getCreateTableQuery(Class type) {//returns string like "create table user(id long auto_increment, name varchar(50))"
    StringBuilder sb = new StringBuilder("create table " + type.getSimpleName() +
            "(" + ReflectionHelper.getIdFieldName(type) + " long auto_increment");
    for(Quartet<Field, String, String, Integer> fieldInfo: ReflectionHelper.getFields(type)){
      sb.append(", " + fieldInfo.getValue1() + " " + fieldInfo.getValue2());
      if(fieldInfo.getValue3() > 0){
        sb.append("(" + fieldInfo.getValue3() + ")");
      }
    }
    return sb.append(")").toString();
  }
  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }

  public SessionManager getSessionManager() {
    return sessionManager;
  }

}
