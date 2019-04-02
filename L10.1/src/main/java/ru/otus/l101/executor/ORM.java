package ru.otus.l101.executor;

import ru.otus.l101.dao.DataSet;
import ru.otus.l101.dbutils.DbHelper;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class ORM {
	private static DbExecutor executor;
	private static DbHelper dbHelper;

	public void initDB() throws OrmException {
		try {
			dbHelper = new DbHelper();
			executor = new DbExecutorImpl(dbHelper.getConnection(), dbHelper);
			dbHelper.createDefaultTable();
		} catch (SQLException e) {
			throw new OrmException(e);
		}
	}

	public <T extends DataSet> void loadClassStructure(Class<T> clazz) throws OrmException {
		try {
			executor.analyzeClass(clazz);
		} catch (SQLException e) {
			throw new OrmException(e);
		}
	}

	public <T extends DataSet> void saveUser(T user) throws OrmException {
		try {
			executor.save(user);
		} catch (SQLException e) {
			throw new OrmException(e);
		}
	}

	public <T extends DataSet> T loadUsersById(long id, Class<T> clazz) throws OrmException {
		T user;
		try {
			user = executor.load(id, clazz);
		} catch (InvocationTargetException | NoSuchMethodException | InstantiationException | SQLException | IllegalAccessException e) {
			throw new OrmException(e);
		}
		return user;
	}

	public void clearDB() throws OrmException {
		try {
			dbHelper.dropAllTables();
			dbHelper.closeConnection();
		} catch (SQLException e) {
			throw new OrmException(e);
		}
	}
}
