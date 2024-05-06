# "*TEMPLATE*"

## Aufgabenstellung
Die detaillierte [Aufgabenstellung](TASK.md) beschreibt die notwendigen Schritte zur Realisierung.

## Fragestellung
1. *Nennen Sie 4 Vorteile eines NoSQL Repository im Gegensatz zu einem relationalen DBMS*
<br>Skalierbarkeit, Flexibler, Lese und Schreibvorgänge sind sehr perfomant, Möglichkeit einer verteilten Datenbankarchitektur (Große Anzahl an Knoten)

2. *Nennen Sie 4 Nachteile eines NoSQL Repository im Gegensatz zu einem relationalen DBMS*
<br>Geringere Transaktionsunterstützung "ACID" ( -> Inkonsistenz, Datenverlust), Schlechter für komplexe Abfragen, wenige Standardisierungen, geringere Unterstützung

3. *Welche Schwierigkeiten ergeben sich bei der Zusammenführung der Daten?*
<br> Datenstrukturen können Inkonsistent werden, wodurch einzelne Daten fehlen, unvollständig oder in unterschiedlichen Formaten verfügbar sein können.

4. *Welche Arten von NoSQL Datenbanken gibt es?*
5. *Nennen Sie einen Vertreter für jede Art?*
<br> Dokumentenorientiert(MongoDB), Spaltenorientiert(Apache Cassandra), Key-Value(Redis), Graphdatenbanken(Neo4j)

6. *Beschreiben Sie die Abkürzungen CA, CP und AP in Bezug auf das CAP Theorem*
<br>CA -> Konsistenz & Verfügbarkeit (Consistency & Availability)
<br>CP -> Konsistenz & Ausfalltoleranz (Consistency & Partition tolerance)
<br>AP -> Verfügbarkeit & Ausfalltoleranz (Availability & Partition tolerance)

7. *Mit welchem Befehl koennen Sie den Lagerstand eines Produktes aller Lagerstandorte anzeigen.*
<br> `db.productData.find( { "productID": "ProduktID" } )`

8. *Mit welchem Befehl koennen Sie den Lagerstand eines Produktes eines bestimmten Lagerstandortes anzeigen.*
<br> `db.productData.find( { "warehouseID": "WarehouseID", "productID": "ProduktID" } )`

## Implementierung
Die alte Aufgabe *WarehouseMOM* wird als Grundlage verwendet um mit diesem Programm auf die
REST Schnittstelle zuzugreifen.

### 1. REST-Zugriff
````java
public String fetchData() {
        String url = "http://localhost:8080/warehouse/001/data";
        return restTemplate.getForObject(url, String.class);
}
````
Diese Methode fragt die URL ab und gibt die empfangenen Daten als String zurück.

### 2. Datenverarbeitung
Anschließend werden mit der Klasse [DataProcessingService.java](/src/main/java/headquarter/processing/DataProcessingService.java) 
die empfangen Daten verarbeitet. Dafür stehen die Methoden `processData(String rawData)` und `processProducts(List<ProductData> productDataList)` zu Verfügung.

### 3. Produktverwaltung
Im Datenverarbeitungsprozess wird überprüft ob ein Produkt bereits bekannt ist (anhand seiner ID).
Ist es bereits vorhanden, werden die alten Produktdaten mit den neu übergebenen Werten überschrieben.
Sollte es komplett neu sein, wird ein neues Produkt im Repository angelegt.

## Quellen
[1] *NoSQL details*, https://www.mongodb.com/nosql-explained

[2], *NoSQL vs SQL*, https://www.mongodb.com/nosql-explained/nosql-vs-sql

[3], *CAP-Theorem*, https://www.ionos.at/digitalguide/server/knowhow/was-ist-das-cap-theorem/