package com.raul.parkdexnative.data

object CharacterBioMapper {
    fun getBio(characterName: String): String {
        val bios = mapOf(
            "Eric Cartman" to "Eric Theodore Cartman este unul dintre cele patru personaje principale. Cunoscut pentru personalitatea sa agresivă, manipulatorie și adesea diabolică, el este sursa principală de conflict în South Park. În ciuda defectelor sale, este extrem de inteligent și capabil de planuri complexe.",
            "Kyle Broflovski" to "Kyle Broflovski este busola morală a grupului. Fiind extrem de empatic și inteligent, el încearcă adesea să găsească soluția corectă la problemele orașului, lucru care îl aduce constant în conflict direct cu rivalul său, Cartman.",
            "Stan Marsh" to "Stanley 'Stan' Marsh este considerat cel mai normal și logic membru al grupului. El acționează adesea ca vocea rațiunii și este de obicei cel care trage concluziile și învățămintele la finalul aventurilor bizare din South Park.",
            "Kenny McCormick" to "Kenneth 'Kenny' McCormick este faimos pentru geaca sa portocalie care îi acoperă gura, făcându-i vocea de neînțeles. De asemenea, este cunoscut pentru gluma recurentă din primele sezoane, în care murea în moduri brutale în aproape fiecare episod.",
            "Butters Stotch" to "Leopold 'Butters' Stotch este cel mai inocent, naiv și blând copil din South Park. Crescut de niște părinți extrem de stricți, Butters este adesea manipulat de Cartman, dar își păstrează mereu atitudinea optimistă.",
            "Randy Marsh" to "Tatăl lui Stan și un geolog pasionat, Randy este probabil cel mai haotic adult din oraș. El este adesea sursa unora dintre cele mai absurde și hilare decizii luate în South Park, lăsându-se dus de val de orice nouă tendință.",
            "Mr. Garrison" to "Domnul Garrison este profesorul băieților. De-a lungul serialului, el a trecut prin cele mai multe transformări de personalitate, de la a avea o marionetă pe nume Mr. Hat, până la a deveni Președintele Statelor Unite."
        )

        return bios[characterName] ?: "Data from SPAPI does not include a full biography for this character yet. They are a resident of South Park, Colorado."
    }
}