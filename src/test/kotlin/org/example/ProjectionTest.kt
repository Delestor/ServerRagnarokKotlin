package org.example

import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.criterion.Projection
import org.hibernate.criterion.Projections
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProjectionTest {

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
    fun test_projection(){

        val criteria : Criteria = session.createCriteria(User::class.java)

        val projection : Projection = Projections.rowCount()

        criteria.setProjection(projection)

        val result = criteria.uniqueResult()

        println("Result: $result")
    }
}