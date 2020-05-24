package ru.otus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.JDBCMapper;
import ru.otus.core.model.Account;
import ru.otus.core.model.MyUser;
import ru.otus.jdbc.DbExecutor;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

/**
 * @author comradv
 * created on 13.05.20
 */
public class MyServiceDemo {
  private static Logger logger = LoggerFactory.getLogger(MyServiceDemo.class);

  public static void main(String[] args) throws Exception {
    testMyUser();

    testAccount();

  }

  private static void testMyUser() throws SQLException, NoSuchMethodException, IllegalAccessException {
    DataSource dataSource = new DataSourceH2();
//    MyServiceDemo demo = new MyServiceDemo();

    createTable(dataSource, JDBCMapper.getCreateTableQuery(MyUser.class));

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

    DbExecutor<MyUser> dbExecutorMyUser = new DbExecutor<>();

    JDBCMapper<MyUser> myUserMapper = new JDBCMapper<>(MyUser.class, sessionManager, dbExecutorMyUser);

    MyUser myUser = new MyUser(0, "userName1", 31);
    myUserMapper.create(myUser);

    Optional<MyUser> loadedUser = myUserMapper.load(1);
    System.out.println(loadedUser);

    myUser.setAge(35);
    myUserMapper.update(myUser);

    loadedUser = myUserMapper.load(1);
    System.out.println(loadedUser);
  }

  private static void testAccount() throws SQLException, NoSuchMethodException, IllegalAccessException {
    DataSource dataSource = new DataSourceH2();

    createTable(dataSource, JDBCMapper.getCreateTableQuery(Account.class));

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

    DbExecutor<Account> dbExecutorMyUser = new DbExecutor<>();

    JDBCMapper<Account> myUserMapper = new JDBCMapper<>(Account.class, sessionManager, dbExecutorMyUser);

    Account account = new Account(0, "type", 31);
    myUserMapper.create(account);

    Optional<Account> loadedUser = myUserMapper.load(1);
    System.out.println(loadedUser);

    account.setRest(35);
    myUserMapper.update(account);

    loadedUser = myUserMapper.load(1);
    System.out.println(loadedUser);
  }

  private static void createTable(DataSource dataSource, String queryText) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement(queryText)) {
      pst.executeUpdate();
    }
    System.out.println("table created");
  }
}
