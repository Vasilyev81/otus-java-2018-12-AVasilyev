package ru.otus.l101.executor;

import ru.otus.l101.dao.DataSet;
import ru.otus.l101.dao.UserDataSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DbExecutorImpl implements DbExecutor {
	private final Connection conn;

	public DbExecutorImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public <T extends DataSet> void save(T user) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users (name, age) VALUES (?, ?);");
		preparedStatement.setString(1, ((UserDataSet) user).getName());
		preparedStatement.setInt(2, ((UserDataSet) user).getAge());
		preparedStatement.executeUpdate();
	}

	@Override
	public <T extends DataSet> T load(long id, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
		Constructor[] constructors = clazz.getConstructors();
		Constructor constructor = null;
		for (Constructor constr : constructors) {
			if (constr.getParameterTypes().length > 2) constructor = constr;
		}

		PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, name, age FROM users WHERE id = ?;");
		preparedStatement.setLong(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		long idx = resultSet.getLong("id");
		String name = resultSet.getString("name");
		int age = resultSet.getInt("age");
		assert constructor != null;
		return (T) constructor.newInstance(idx, name, age);
	}
}
