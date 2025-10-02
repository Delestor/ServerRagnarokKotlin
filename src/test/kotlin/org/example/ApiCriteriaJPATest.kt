package org.example

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.persistence.TypedQuery


class ApiCriteriaJPATest {

    lateinit var sessionFactory: SessionFactory
    lateinit var session : Session

    @BeforeEach
    fun setUp() {
        sessionFactory = Configuration().configure().buildSessionFactory()
        session = sessionFactory.openSession()
    }

    @AfterEach
    fun tearDown() {
        sessionFactory.close()
        session.close()
    }

    @Test
    fun testCriteriaUsers() {
        val cb = session.criteriaBuilder
        val cq = cb.createQuery(User::class.java)
        val user = cq.from(User::class.java)
        cq.select(user)
        val q: TypedQuery<User> = session.createQuery(cq)
        val allUsers = q.resultList

        for (u in allUsers) {
            println(u.toString())
        }
    }

}