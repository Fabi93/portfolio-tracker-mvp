---
paths:
  - "**/*.java"
---

# Java Best Practices — allgemeine Regeln

Sprachspezifische Ergänzung zu `clean-code.md`. Orientiert an etablierten Java-Best-Practices
(u. a. Effective Java) und modernen Java-Idiomen (17/21). Nicht projektspezifisch.

## Objekterzeugung & Abhängigkeiten
- **Konstruktor-Injection** für Abhängigkeiten (keine Field-Injection); Felder `final`.
- **Builder** bei vielen (v. a. optionalen) Parametern statt Teleskop-Konstruktoren.
- Erwäge **statische Factory-Methoden** (`of`, `from`, `valueOf`) mit sprechenden Namen.
- Keine unnötige Objekterzeugung in Hot Paths; keine überflüssigen Autoboxing-Konvertierungen.

## Unveränderlichkeit
- **Bevorzuge Immutability.** Felder `private final`; keine Setter, wenn vermeidbar.
- **`record`** für reine Datenträger (bringt equals/hashCode/toString/Zugriff mit).
- **Defensive Kopien** bei veränderlichen Feldern in Ein-/Ausgabe.

## Typen, Klassen, Interfaces
- **Komposition vor Vererbung.** Vererbung nur bei echtem „is-a".
- **Gegen Interfaces programmieren** (Deklaration `List`/`Map`, nicht `ArrayList`/`HashMap`).
- **Sichtbarkeit minimieren**; keine öffentlichen veränderlichen Felder.
- **`sealed`** für bewusst geschlossene Hierarchien; **`enum`** statt `int`-Konstanten (mit `EnumMap`/`EnumSet`).
- `equals`/`hashCode` **zusammen** überschreiben und Verträge einhalten; `toString` sinnvoll (bei `record` automatisch).

## Null, Optional & Fehler
- **Nie `null` zurückgeben** für Collections/Streams → leere Instanz. `Optional<T>` als Rückgabetyp für „kann fehlen".
- **`Optional` nicht** als Feld/Parameter/in Collections verwenden.
- `Objects.requireNonNull(...)` für Precondition-Checks; `Objects.equals`/`Objects.hash` nutzen.
- **Exceptions statt Fehlercodes**; Runtime-Exceptions für Programmierfehler, Checked nur für sinnvoll behandelbare Fälle.
- **Exceptions nicht** für normalen Kontrollfluss. Nie leere `catch`-Blöcke; ursprüngliche `cause` bewahren.
- **`try-with-resources`** für alles `AutoCloseable`.

## Collections & Streams
- Streams für Transformationen — aber **ohne Seiteneffekte** und nicht überstrapaziert (eine simple Schleife darf simpel bleiben).
- `isEmpty()` statt `size() == 0`; passende Collection wählen (`List`/`Set`/`Map`, `LinkedList` selten sinnvoll).
- Unveränderliche Sichten (`List.of`, `List.copyOf`) bevorzugen, wo passend.

## Nebenläufigkeit
- Immutability ist die beste Thread-Safety. Sonst `java.util.concurrent` statt eigenem `synchronized`-Gefrickel.
- Keine Synchronisation auf veränderlichen/öffentlichen Locks; kein `Thread.stop`/`finalize`.

## Moderne Java-Idiome (mit Maß)
- **Switch Expressions** & **Pattern Matching** (`instanceof`, `switch`) statt langer if-else-Kaskaden.
- **Text Blocks** für mehrzeilige Strings; `var` nur, wenn der Typ rechts klar ersichtlich ist.
- `StringBuilder` in Schleifen statt String-Konkatenation.

## Sonstiges
- **Keine Magic Numbers** — benannte Konstanten (`static final`).
- **Logging-Framework** (SLF4J) statt `System.out`/`printStackTrace`.
- Konsistente, aussagekräftige Package-Struktur; keine zyklischen Abhängigkeiten.
