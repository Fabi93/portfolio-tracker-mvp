---
paths:
  - "**/*.{ts,tsx}"
---

# TypeScript Best Practices — allgemeine Regeln

Sprachspezifische Ergänzung zu `clean-code.md`. Nicht projektspezifisch.

## Typsicherheit
- **`strict` an** (inkl. `noImplicitAny`, `strictNullChecks`, `noUncheckedIndexedAccess`). Kein Abschalten pro Datei.
- **Kein `any`.** Wenn der Typ unbekannt ist → `unknown` + gezieltes Narrowing/Type-Guard.
- **Type Assertions (`as`) und Non-null (`!`) vermeiden** — sie umgehen den Compiler. Lieber echte Guards.
- **Öffentliche API** (exportierte Funktionen/Rückgaben) explizit typisieren; interne Locals dürfen Inferenz nutzen.

## Typen modellieren
- **Union-Literals / `as const`** statt numerischer `enum`s, wo möglich.
- **Discriminated Unions** + **exhaustive** `switch` (mit `never`-Check im `default`) für Varianten.
- **`readonly`** und `ReadonlyArray` für unveränderliche Daten; `const` als Default.
- **Utility Types** nutzen (`Partial`, `Pick`, `Omit`, `Record`, `Required`) statt Typen zu duplizieren.
- Schmale, fokussierte Interfaces (ISP) — keine „God-Types".

## Null & Undefined
- **Optional Chaining `?.`** und **Nullish Coalescing `??`** statt manueller Checks / `||`-Fallen.
- Klar zwischen `null`, `undefined` und „nicht vorhanden" unterscheiden — konsistent im Projekt.

## Sprache & Stil
- **`===`/`!==`** immer (nie `==`). `const` vor `let`; nie `var`.
- **Named Exports** bevorzugen (konsistente Auto-Imports); Default-Exports meiden.
- Reine Funktionen, Immutability, `map`/`filter`/`reduce` **ohne Seiteneffekte**.

## Asynchronität
- **`async/await`** statt verschachtelter `.then()`-Ketten; Fehler mit `try/catch` behandeln.
- **Keine floating Promises** — jedes Promise `await`en, zurückgeben oder bewusst `void`-markieren.
- Nebenläufiges parallelisieren (`Promise.all`), wenn unabhängig.

## Fehler
- Nur `Error`(-Subklassen) werfen, keine Strings/Objekte. Kontext in der Message; `cause` bewahren.
