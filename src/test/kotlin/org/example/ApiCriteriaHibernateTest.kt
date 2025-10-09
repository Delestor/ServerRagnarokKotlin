package org.example

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.criterion.Restrictions
import org.junit.jupiter.api.AfterEach
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ApiCriteriaHibernateTest {

    lateinit var sessionFactory: SessionFactory
    lateinit var session: Session

    @BeforeEach
    fun setUp(){
        sessionFactory = Configuration().configure().buildSessionFactory()
        session = sessionFactory.openSession()
    }

    @AfterEach
    fun tearDown() {
        sessionFactory.close()
        session.close()
    }

    @Test
    fun test_criteria_users_where_name_is_marco() {
        val lista = session.createCriteria(User::class.java)
            .add( Restrictions.eq("name", "marco"))
            .list()

        assertThat((lista[0] as User).name).isEqualTo("marco")
    }

}