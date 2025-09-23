package org.example

import org.assertj.core.api.Assertions
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate


/**
 * Unit test for simple App.
 */
class HibernateFullTest {
    private var sessionFactory: SessionFactory? = null

    @BeforeEach
    @Throws(Exception::class)
    protected fun setUp() {
        // A SessionFactory is set up once for an application!
        val registry = StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build()
        try {
            sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()
        } catch (e: Exception) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry)
            println("Error creando SessionFactory: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    @AfterEach
    @Throws(Exception::class)
    protected fun tearDown() {
        if (sessionFactory != null) {
            sessionFactory!!.close()
        }
    }

    @Test
    fun testBasicUsage() {
        // create a couple of events...
        var session = sessionFactory!!.openSession()
        session.beginTransaction()
        session.remove(User("Marco's Friend", LocalDate.now()))
        session.transaction.commit()
        session.close()

        session = sessionFactory!!.openSession()
        session.beginTransaction()
        val result = session.createQuery(
            "select u from User u",
            User::class.java
        ).list()
        for (user in result) {
            println("User (" + user.name + ") : " + user.birthDate)
        }
        session.transaction.commit()
        session.close()
    }

    @Test
    fun marco_is_in_the_house() {
        Assertions.assertThat(1).isGreaterThanOrEqualTo(0)
    }
}
