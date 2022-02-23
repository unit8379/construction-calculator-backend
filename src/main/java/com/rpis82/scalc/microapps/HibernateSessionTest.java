package com.rpis82.scalc.microapps;


import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rpis82.scalc.entity.User;
import com.rpis82.scalc.entity.UserState;

/* Класс-сервис для тестирования соединения к БД через Hibernate 
 * Подход работы через фабрику и сессии не тот, который подразумевается
 * при работе со Spring JPA, а тем более со Spring Boot.
 * Так что не стоит удивляться вылетающим исключениям из основного контекста Spring JPA.
 * Этот класс существует только для тестирования работы ORM Hibernate'а. */

@Component("sessionTest")
@Transactional
public class HibernateSessionTest {
	
	private SessionFactory hibernateFactory;

	@Autowired
	public void someService(EntityManagerFactory factory) {
		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		
		// разворачиваем SessionFactory Hibernate'а из EntityManagerFactory объекта, который создал Spring JPA
		this.hibernateFactory = factory.unwrap(SessionFactory.class);
	}
	
	public void createUser() {
		
		// фабрика производит экземпляр сессии
		Session session = hibernateFactory.getCurrentSession();
		
		try {
			int theId = 1;
			UserState tempUserState = session.get(UserState.class, theId);
			
			User tempUser = new User("Анастасия", "Багдасарян", "Эдуардовна", "+7(845)1113214", "nastya_bagd@ya.ru", "aebagd", "qwerty123");
			tempUser.setUserState(tempUserState);
			
			// Из объекта сессии начинается транзакция
			session.beginTransaction();
			
			// В БД сохранится User
			System.out.println("Saving user: " + tempUser + " and its associated user state.");
			session.save(tempUser);
			
			// Эта транзакция фиксируется коммитом
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			// закрытие ресурсов
			session.close();
			hibernateFactory.close();
		}
	}
}
