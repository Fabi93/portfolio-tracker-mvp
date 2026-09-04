# Product Requirements Document (PRD): Minimal Portfolio Tracker

> Unverändert übernommen aus der Vorlage (`PRD_Portfolio_Tracker_v2.md`). Dient als Eingang der Refinement-Stufe.

## 1. Kontext & Vision
Inspiriert von Plattformen wie *justETF* wollen wir privaten Anlegern eine einfache Möglichkeit bieten, ihr Portfolio manuell zu erfassen und die Performance auf einen Blick zu bewerten.

In dieser ersten Iteration (MVP) konzentrieren wir uns auf den Kernnutzen: Die Erfassung von Bestandspositionen und die Ermittlung des aktuellen Portfoliowerts sowie der Rendite basierend auf simulierten Marktkursen. Dieses PRD dient als Basis für die Implementierung der zentralen Geschäftslogik.

## 2. Scope für das MVP (Fokus: 30-Minuten Implementierung)

Das MVP fokussiert sich auf eine **bestandsbasierte Erfassung** (Positions) und verzichtet zunächst auf komplexe Transaktionshistorien.

### 2.1 User Stories
* **US1: Position hinzufügen**
  Als Anleger möchte ich eine Position (bestehend aus Wertpapier-Kennnummer, Stückzahl und initialem Kaufkurs) zu meinem Portfolio hinzufügen.
* **US2: Portfoliowert berechnen**
  Als Anleger möchte ich den aktuellen Gesamtwert meines Portfolios (in EUR) ermitteln, indem die Stückzahlen mit aktuellen Marktkursen multipliziert werden.
* **US3: Gewinn/Verlust (Performance) ausweisen**
  Als Anleger möchte ich den absoluten Gewinn oder Verlust (in EUR) meines Portfolios sehen. Die Formel lautet: `(Aktueller Gesamtwert) - (Gesamter historischer Kaufwert)`.

## 3. Ausbaustufen (Future Scope)
*Diese Stufen dienen als Diskussionsgrundlage für das Systemdesign oder als Erweiterung. Wir möchten gemeinsam mit dem mitgebrachten agentischem Setup Erweiterungen an dem System vornehmen.*

### Ausbaustufe 1: Transaktionsbasierte Erfassung (Event Sourcing Light)
* **Kontext:** Anleger bauen Positionen oft über Sparpläne (viele kleine Käufe) auf oder verkaufen Teilpositionen.
* **Anforderung:** Statt starrer "Positionen" verarbeitet das System eine Liste von "Transaktionen" (Typ: `BUY` oder `SELL`, mit Datum, Menge und Kurs).
* **Logik:** Das System muss aus den Transaktionen dynamisch den aktuellen Bestand (Quantity) und den durchschnittlichen Einstiegskurs (Average Buy-In) berechnen.

### Ausbaustufe 2: Benchmark-Vergleich
* **Kontext:** Ein Gewinn von 5% ist schlecht, wenn der Markt im gleichen Zeitraum 15% gemacht hat.
* **Anforderung:** Integration eines Vergleichs. Der Nutzer gibt eine Benchmark-ID (z.B. MSCI World ETF) an.
* **Logik:** Das System berechnet die prozentuale Rendite des Portfolios und subtrahiert davon die prozentuale Rendite des Benchmarks. Ergebnis ist die Out- oder Underperformance.
