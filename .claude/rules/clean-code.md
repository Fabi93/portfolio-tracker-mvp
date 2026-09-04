---
paths:
  - "**/*.{java,kt,ts,tsx,js,jsx,py,go,cs,rb,php,scala,swift,rs,cpp,cc,c,h,hpp}"
---

# Clean Code — allgemeine Regeln (nach Robert C. Martin)

Sprach-agnostische Prinzipien für sauberen Code. In eigenen Worten formuliert, als
handlungsleitende Regeln. Gilt für jede Code-Datei (path-gescopt), nicht projektspezifisch.

## Namen
- Namen müssen die **Absicht** verraten: warum es existiert, was es tut, wie es benutzt wird.
- Aussprechbar und suchbar; keine kryptischen Abkürzungen, keine Ein-Buchstaben-Namen (außer kurze Schleifenzähler).
- Keine Typ-/Encoding-Präfixe (kein Ungarian, kein `m_`, kein `I`-Interface-Präfix).
- Ein Wort pro Konzept (nicht `get`/`fetch`/`retrieve` gemischt). Klassen = Substantive, Methoden = Verben.
- Keine Desinformation: der Name darf nicht das Gegenteil suggerieren (`list`, das keine Liste ist).

## Funktionen
- **Klein.** Eine Funktion tut **genau eine Sache** — und zwar vollständig.
- **Ein Abstraktionslevel** pro Funktion (nicht High-Level-Policy und Low-Level-Details mischen).
- **Wenige Parameter:** 0–2 ideal, 3 vermeiden, >3 → in ein Objekt bündeln.
- **Keine Flag-Parameter** (`doThing(true)`): signalisiert >1 Aufgabe → aufteilen.
- **Keine versteckten Seiteneffekte.** Der Name muss alles beschreiben, was die Funktion tut.
- **Command-Query-Separation:** eine Funktion *tut* etwas **oder** *beantwortet* etwas — nicht beides.
- Fehlerbehandlung ist eine eigene Aufgabe: `try/catch`-Blöcke in eigene Funktionen extrahieren.
- Bevorzuge **Rückgabewerte/Exceptions** gegenüber Ausgabe-Parametern.

## Kommentare
- Erste Wahl ist **ausdrucksstarker Code**, nicht der Kommentar. Ein Kommentar ist oft ein Eingeständnis, dass der Code nicht klar genug ist.
- Gute Kommentare: Absicht/Begründung, Warnung vor Konsequenzen, `TODO`, rechtliche Header, öffentliche API-Docs.
- Verboten: **auskommentierter Code** (löschen — dafür gibt es Git), redundante, irreführende oder veraltete Kommentare, Changelog-/Positions-Marker.

## Formatierung
- Zusammengehöriges vertikal nah beieinander; Abhängiges dort, wo es benutzt wird.
- Kleine Dateien sind leichter zu lesen als große. Konsistenter Team-Stil > persönlicher Stil.

## Fehlerbehandlung
- **Exceptions statt Fehlercodes.** Kein Prüfen von Rückgabe-Codes durch den Aufrufer.
- **Kein `null` zurückgeben und kein `null` übergeben** — nutze leere Collections, Optionals oder Special-Case-Objekte.
- Exceptions mit **Kontext** werfen (was, wo, warum). Fehlerbehandlung von der Kern-Logik trennen.

## Objekte vs. Datenstrukturen
- **Tell, don't ask:** Objekten sagen, was zu tun ist, statt ihren Zustand abzufragen und extern zu entscheiden.
- **Law of Demeter:** nicht durch Objektketten navigieren (`a.getB().getC().doIt()`).
- Objekte verbergen Daten und exponieren Verhalten; Datenstrukturen exponieren Daten und haben kein Verhalten. Nicht vermischen (keine „Hybride").

## Klassen & Design (SOLID)
- **SRP:** eine Klasse hat **einen** Grund, sich zu ändern. Klein und hoch kohäsiv.
- **OCP:** offen für Erweiterung, geschlossen für Änderung.
- **LSP:** Subtypen müssen ihren Basistyp ersetzbar erfüllen.
- **ISP:** schmale, fokussierte Interfaces statt „fat interfaces".
- **DIP:** von Abstraktionen abhängen, nicht von konkreten Implementierungen.
- Sichtbarkeit minimieren; Kapselung wahren.

## Tests
- **TDD-Rhythmus:** erst der (rote) Test, dann der Code, dann Refactoring.
- **FIRST:** Fast · Independent · Repeatable · Self-validating · Timely.
- **Ein Konzept pro Test**; aussagekräftige Testnamen; Tests so sauber halten wie Produktivcode.
- Tests sind Doku und Sicherheitsnetz — Duplication/Unlesbarkeit dort ist genauso teuer.

## Übergreifende Prinzipien
- **DRY** — Wissen genau einmal ausdrücken. **KISS** — die einfachste Lösung, die funktioniert. **YAGNI** — nichts auf Vorrat bauen.
- **Boy-Scout-Rule:** den Code sauberer hinterlassen, als du ihn vorgefunden hast.
- **Magische Zahlen/Strings** durch benannte Konstanten ersetzen.

## Code Smells (frühe Warnsignale)
Lange Funktionen · große Klassen · lange Parameterlisten · Duplication · toter Code ·
verschachtelte Bedingungen/tiefe Einrückung · unklare Namen · Feature Envy · primitive obsession ·
kommentierte „Erklärungen" für unklaren Code.
