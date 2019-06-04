package ru.otus.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.datasets.*;

import javax.persistence.criteria.*;
import java.util.List;

class DataSetDAO {
	private Session session;

	public DataSetDAO(Session session) {
		this.session = session;
	}

	public <T extends DataSet> void save(T dataSet) {
		session.save(dataSet);
	}

	public UserDataSet read(long id) {
		return session.load(UserDataSet.class, id);
	}

	public UserDataSet readByName(String name) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
		Root<UserDataSet> from = criteria.from(UserDataSet.class);
		criteria.where(builder.equal(from.get("name"), name));
		Query<UserDataSet> query = session.createQuery(criteria);
		return query.uniqueResult();
	}

	public List<UserDataSet> readAll() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
		criteria.from(UserDataSet.class);
		return session.createQuery(criteria).list();
	}
}
