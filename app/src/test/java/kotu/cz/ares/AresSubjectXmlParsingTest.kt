package kotu.cz.ares

import kotu.cz.ares.model.AresSubject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.simpleframework.xml.core.Persister

class AresSubjectXmlParsingTest {
    @Test
    fun parse_rzp_05219272() {
        val inputStream = javaClass.classLoader.getResourceAsStream("darv_rzp_05219272.xml")
        val serializer = Persister()
        val subject = serializer.read(AresSubject::class.java, inputStream, false)
        assertEquals("Mgr. Tomáš Kotula", subject.name)
        assertEquals("Olšinky", subject.address.street)
        assertEquals("14", subject.address.streetNumber)
        assertEquals("Kravaře", subject.address.city)
        assertEquals("74721", subject.address.postCode)
    }
}
