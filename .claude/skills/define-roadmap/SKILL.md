---
name: define-roadmap
description: Eine Produkt-ROADMAP entwerfen oder schärfen — die geordnete Abfolge von Zielen/Outcomes, die eine Strategie umsetzt, klar unterschieden von Strategie (dem Ansatz) und Backlog (den Tickets). Einsetzen, sobald von „Roadmap", „Meilensteinen", „Release-Plan", „Now/Next/Later", „GO Product Roadmap" oder „Ideen & Features in einen Plan bringen" die Rede ist — und immer nach einer freigegebenen Strategie, bevor Epics/Tickets entstehen. Zwingt zu ziel-/outcome-orientierten Meilensteinen (Pichlers GO Product Roadmap) mit ehrlicher Unsicherheit (Now/Next/Later) statt Feature-Listen mit Scheinterminen. Sokratisch: die Roadmap wird aus Strategie + Nutzer abgeleitet, nicht erfunden.
---

# Skill: Produkt-Roadmap entwerfen

## Was eine Roadmap ist — und was nicht
Eine **Roadmap** ist die **geordnete Abfolge von Zielen/Outcomes**, die die Strategie in die Zeit übersetzt.

- **Roadmap ≠ Strategie.** Die Strategie ist der Ansatz (das *Wie gewinnen*). Die Roadmap ist die *Reihenfolge der Outcomes* auf diesem Weg.
- **Roadmap ≠ Backlog.** Die Roadmap ist grobkörnig (Ziele), nicht die Ticket-Liste. Tickets kommen später.
- **Roadmap ≠ Feature-Liste mit Daten.** Das ist das häufigste Anti-Pattern: eine Sammlung von Features mit Quartalsstempeln. Eine Roadmap sagt **welches Ziel/welchen Outcome** ein Meilenstein liefert — Features sind nur *Mittel*.
- **Roadmap ≠ Terminvertrag.** Je weiter weg, desto unschärfer — bewusst.

**Kernprinzip:** **Outcome vor Output.** Ein Meilenstein ist an einem *messbaren Ergebnis* erkennbar, nicht an einer Feature-Zahl.

## Voraussetzung
Eine **freigegebene Strategie** (Skill `define-strategy`) — inkl. **Diagnose, Leitlinie und bewusstem Verzicht**. Ohne Strategie ist eine Roadmap eine Wunschliste mit Daten. Fehlt sie, zuerst `define-strategy`. Der **erste vertikale Faden** aus der Strategie ist der natürliche **„Now".**

## Right-Sizing (zuerst lesen)
**Halte es schlank.** Für ein Solo-/Lernvehikel keine 4-Jahres-Gantt. **Nur „Now" ist konkret**, „Next" grob, „Later" eine Richtung. Falsche Termin-Präzision ist Selbstbetrug. Ein Baustein, sauber.

## Der Prozess (sokratisch — ableiten, nicht erfinden)

**Wichtig:** Die Roadmap gehört dem Nutzer/Team. Leite aus Strategie + Nutzer ab, schlage vor — **erfinde keine Ziele** und **verletze nie den Verzicht der Strategie**.

### Schritt 1 — Strategie & Verzicht laden
Diagnose, Leitlinie, ersten Faden und **bewussten Verzicht** aus der Strategie übernehmen. Der Verzicht ist die Leitplanke: was dort „raus/später" ist, darf **nicht** heimlich in die Roadmap zurückkriechen.

### Schritt 2 — Meilensteine als ZIELE formulieren (Pichler GO)
Jeder Meilenstein bekommt:
- **Name** (kurz, einprägsam)
- **Ziel / Outcome** (der Zustand, der erreicht ist — *nicht* eine Feature-Liste)
- **Erfolgsmetrik** (woran man den Outcome misst)
- **Kern-Features** (wenige — die *Mittel* zum Ziel, nicht der Zweck)
- **Zeitfenster** (grob; je weiter weg, desto unschärfer)

### Schritt 3 — Now / Next / Later ordnen
- **Now:** der erste Faden der Strategie, konkret. Genau *ein* klarer Fokus.
- **Next:** was plausibel folgt, grob.
- **Later:** Richtung, keine Details.
Konfidenz nimmt nach hinten ab — das ehrlich so benennen.

### Schritt 4 — Ideen & Features einsortieren (diszipliniert)
Vorhandene Ideen/Features **nicht** alle in die Roadmap pressen. Für jede: gehört sie zu einem Meilenstein-**Ziel**? → einsortieren. Sonst → **Parking Lot** (Later/„vielleicht") oder raus. Der Reiz, „alles" aufzunehmen, ist der Feind — die meisten Ideen gehören in den Parking Lot.

### Schritt 5 — Kohärenz-Check gegen die Strategie
Prüfen: Zieht **jeder** Meilenstein Richtung Diagnose/Leitlinie? Verletzt einer den **Verzicht**? Ist irgendwo Output (Features) als Outcome getarnt? Widersprüche und Fremdkörper raus.

### Schritt 6 — Dem Nutzer zur Freigabe vorlegen (Human-Gate)
Roadmap präsentieren, **plus** ehrliche Notiz, wo sie noch **Wunschliste**, **falsche Termin-Präzision** oder **verkappte Feature-Liste** ist. Erst nach Bestätigung/Korrektur gilt sie als fertig.

## Anti-Patterns (aktiv dagegen prüfen)
- **Feature-Liste mit Daten** statt Ziele/Outcomes. Der Klassiker.
- **Alles ist „Now"** — keine echte Reihenfolge, keine Priorisierung.
- **Falsche Termin-Präzision** weit in der Zukunft (Scheinsicherheit).
- **Verzicht ignoriert** — vertagte/gestrichene Dinge kriechen zurück in die Roadmap.
- **Output als Outcome getarnt** — „X Features geliefert" ist kein Ziel.
- **Roadmap = Backlog** — Tickets statt grobkörniger Ziele.

## Output-Format
```
# Produkt-Roadmap: [Produktname]

_Leitet sich ab aus: [Strategie]. Nur „Now" ist konkret._

## Now  (Fokus, konkret)
**[Meilenstein-Name]**
- Ziel/Outcome: …
- Erfolgsmetrik: …
- Kern-Features (Mittel): …
- Zeitfenster: …

## Next  (grob)
**[Meilenstein-Name]** — Ziel/Outcome · Metrik · grobe Kern-Features · Zeitfenster

## Later  (Richtung, keine Details)
- [Themen/Outcomes als Richtung]

## Parking Lot  (Ideen ohne Zuordnung — bewusst NICHT eingeplant)
- [Idee] — warum (noch) nicht

## Ehrliche Einschätzung
[Wo die Roadmap noch Wunschliste / Scheintermin / verkappte Feature-Liste ist — brutal ehrlich]
```

## Quellen
Roman **Pichler**, **GO Product Roadmap** (goal-oriented: Datum · Name · Ziel · Features · Metrik); Janna **Bastow / ProdPad**, **Now/Next/Later**-Roadmap; Jeff **Patton**, User Story Mapping (Brücke Roadmap → Epics). Outcome-über-Output i. S. v. Melissa Perri.
