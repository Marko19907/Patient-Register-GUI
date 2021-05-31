# Vurderingskriterier

Når vi vurderer arbeidskravet “Mappe - Del 2” vektlegger vi følgende momenter:

* Maven:
    * Er prosjektet et Maven-prosjekt med fornuftige prosjekt-verdier og gyldig katalogstruktur?
    * Kan vi kjøre Maven-kommandoer for å bygge, installere og teste uten at det feiler?


* Versjonskontroll med git:
    * Er prosjektet underlagt versjonskontroll med lokalt repo?
    * Er det lokale repoet koblet mot et sentralt repo?
    * Finnes det minst én commit per kodeoppgave?
    * Beskriver commit-meldingene endringene på en kort og konsis måte?
    * Har alle endringer blitt lastet opp til sentralt repo?


* Oppfylles kravene til det grafiske brukergrensesnittet (GUI)
* Er filhåndtering (lesing og skriving av CSV-filer) implementert iht oppgavebeskrivelsen?
* Anvendes Factory-pattern til å opprette GUI-elementer, og er designmønstret kodet på en fornuftig måte?
* Er databaseløsningen satt opp iht oppgavebeskrivelsen og fungerer den som forventet?


* Enhetstesting:
    * Har forretningskritisk kode egne enhetstester?
    * Har enhetstestene beskrivende navn som dokumenterer hva testen gjør?
    * Følger de mønstret Arrange-Act-Assert?
    * Tas det hensyn til både positive og negative tilfeller?


* Kodekvalitet:
    * Er koden godt dokumentert med JavaDoc og kommentarer der det er fornuftig?
    * Er koden robust (verifiseres parametere før de brukes, håndteres unntak og feil på en rimelig måte mm)?
    * Har koden en fornuftig struktur og oppdeling, og er det lett å gjøre endringer og utvidelser i kodebasen?
    * Har variabler, metoder og klasser gode beskrivende navn?