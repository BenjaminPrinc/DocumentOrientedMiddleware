# Document Oriented Middleware using MongoDB
**Autor**: Benjamin Princ

**Datum**: 06.05.2024

- [Document Oriented Middleware using MongoDB](#document-oriented-middleware-using-mongodb)
  - [Aufgabenstellung](#aufgabenstellung)
  - [Fragestellung](#fragestellung)
  - [Implementierung GKue](#implementierung-gkue)
    - [1. REST-Zugriff](#1-rest-zugriff)
    - [2. Datenverarbeitung](#2-datenverarbeitung)
    - [3. Produktverwaltung](#3-produktverwaltung)
  - [Implementierung GKv](#implementierung-gkv)
    - [1. Warehouse Simulation anpassen](#1-warehouse-simulation-anpassen)
    - [2. Neue Request erstellen](#2-neue-request-erstellen)
    - [3. Datenverarbeitung erweitern](#3-datenverarbeitung-erweitern)
    - [4. Sinnvolle Fragestellungen](#4-sinnvolle-fragestellungen)
  - [Quellen](#quellen)


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

## Implementierung GKue
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

## Implementierung GKv
Es soll nun ermöglicht werden mehrere Lagerstandorte abzufragen und speichern.
Zudem soll ein Konzept für die kontinuierliche Speicherung erarbeitet werden.

### 1. Warehouse Simulation anpassen
```java
@RequestMapping(value = "/warehouses/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseData[] allWarehousesData() {
        return service.getAllWarehouseData();
    }
```
Diese Methode ermöglicht es über die URL `http://localhost:8080/warehouses/data`, Daten von mehreren Warenhäusern abzufragen.
Im Hintergrund werden beim Aufruf von `service.getAllWarehouseData()` 3 Warenhäuser simuliert die jeweils über 25 Produkte verfügen.

### 2. Neue Request erstellen
Um auf den Datensatz mit mehreren Warenhäusern zuzugreifen wurde in der Klasse [Request.java](src/main/java/headquarter/request/Request.java) die Methode `fetchData()` mit der neuen URL ersetzt.

### 3. Datenverarbeitung erweitern
Um die neue Datenstruktur (Array) verarbeiten zu können wurde in der Klasse [DataProcessingService.java](src/main/java/headquarter/processing/DataProcessingService.java) die Methode `processMultipleData(String rawData)` hinzugefügt.
```java
public void processMultipleData(String rawData){
        try {
            ObjectMapper mapper = new ObjectMapper();
            WarehouseData[] warehousesRaw = mapper.readValue(rawData, WarehouseData[].class);
            for (WarehouseData wd : warehousesRaw) {
```
Mit Hilfe des ObjectMappers werden die rawData in ein Array von WarehouseData gelesen und anschließend wird durch jedes Objekt im Array iteriert.

### 4. Sinnvolle Fragestellungen
1. Welche Produkte, mit einem Lagerstand von 50 oder darüber, besitzt ein Warenhaus? Sortiert nach Kategorie
<br> ` db.productData.find({"warehouseID":"84fbe", "productQuantity":{$gte: 50}}).sort({"productCategory":1});`

2. Wieviele Produkte der Kategorie "Gemüse", sind vorhanden über alle Warenhäuser hinweg, vorhanden?
<br> `db.productData.find({"productCategory":"Gemüse"}).count();`

3. Welche Produkte der Kategorie "Softdrink", welche einen Lagerbestand unter 70 haben, existieren? Absteigend sortiert nach WarehouseID
<br> `db.productData.find({"productCategory":"Softdrink", "productQuantity":{$lt:70}}).sort({"warehouseID":1});`

## Quellen
[1] *NoSQL details*, https://www.mongodb.com/nosql-explained

[2], *NoSQL vs SQL*, https://www.mongodb.com/nosql-explained/nosql-vs-sql

[3], *CAP-Theorem*, https://www.ionos.at/digitalguide/server/knowhow/was-ist-das-cap-theorem/

[4], *Greater-Than MongoDB*, https://www.mongodb.com/docs/manual/reference/operator/query/gte/