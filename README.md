# TDT4140-Server

##**Hvordan bruke modellene?**

Har laget modellene *Appointment*, *Calendar*, *Person* og *Gruppe*. 
Alle implementerer interface **Model** som inneholder funksjonene:


  * **create()**: Tar nåværende objekt og lager en ny entitet i databasen med egenskapene til objektet.
  * **read(int i)**: Tar nåværende objekt og gir den verdier fra en entitet i databasen med id'en gitt i parameteren.
  * **update()**
  * **delete()**
  * **toJSON()**

Alle modellene har en tom konstruktor (om du vil lage en entitet fra grunnen av eller bruke et tomt objekt), og en konstruktor som tar inn et *JSONObjekt* der man gir objektet verdiene fra *JSONObjekt*et (Brukes nar man skal konvertere data hentet fra klienten til et objekt i serveren).
