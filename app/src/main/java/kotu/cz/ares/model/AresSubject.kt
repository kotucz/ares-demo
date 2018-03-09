package kotu.cz.ares.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root
data class AresSubject(
    @Path("Odpoved/Vypis_RZP/ZAU")
    @field:Path("Odpoved/Vypis_RZP/ZAU")
    @Element(name = "OF")
    @field:Element(name = "OF")
    val name: String,

    @Path("Odpoved/Vypis_RZP/Adresy")
    @field:Path("Odpoved/Vypis_RZP/Adresy")
    @Element(name = "A")
    @field:Element(name = "A")
    val address: Address
)

data class Address(
    @Element(name = "N")
    @field:Element(name = "N")
    val city: String
)