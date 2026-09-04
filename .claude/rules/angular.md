---
paths:
  - "**/*.{component,service,directive,pipe,guard,resolver,interceptor,module,routes,config}.ts"
  - "**/*.component.html"
---

# Angular Best Practices — allgemeine Regeln

Framework-spezifische Ergänzung zu `clean-code.md` + `typescript.md`. Orientiert an modernem
Angular (17+/Standalone). Nicht projektspezifisch.

## Architektur & Komponenten
- **Standalone Components** (`standalone: true`) für neuen Code — keine neuen `NgModule`s.
- **`ChangeDetectionStrategy.OnPush`** als Default.
- **Smart/Dumb-Trennung:** Container-Komponenten holen Daten, Präsentations-Komponenten bekommen `input()`/geben `output()`. Komponenten klein, eine Verantwortung.
- Keine Geschäftslogik in der Komponente — in **Services** auslagern; Komponente kümmert sich um die View.

## State & Reaktivität
- **Signals** (`signal`, `computed`, `effect`) für lokalen/abgeleiteten Zustand; RxJS dort, wo Streams/Events wirklich passen.
- **`async`-Pipe** im Template statt manuellem `subscribe`. Manuelle Subscriptions mit **`takeUntilDestroyed()`** aufräumen.
- Zustand so **lokal wie möglich** halten; geteilten Zustand über Services/Signals-Stores.

## Dependency Injection
- **`inject()`** oder Konstruktor-Injection; Services `providedIn: 'root'`, wenn app-weit.
- Querschnitts-Themen (Auth, Logging, Fehler) über **HTTP-Interceptors**, nicht in jeder Komponente.

## Templates
- **Neues Control-Flow-Syntax** `@if` / `@for` / `@switch`; bei `@for` **`track`** setzen.
- Keine komplexe Logik im Template — in **pure Pipes** oder `computed()` auslagern.
- Währung/Datum/Zahlen über Angular-Pipes bzw. `Intl` formatieren (Locale-fähig).

## Formulare & HTTP
- **Reactive Forms** (typed) für nicht-triviale Formulare statt Template-Driven.
- **`HttpClient`** mit **typisierten** Responses; keine `any`-Antworten.

## Performance & Routing
- **Lazy Loading** über die Route-Config (`loadComponent`/`loadChildren`).
- Unnötige Change-Detection vermeiden (OnPush + Signals); keine schweren Ausdrücke im Template.

## Barrierefreiheit
- **Semantisches HTML** zuerst; `label`-Verknüpfung, `aria-*` nur ergänzend; sichtbarer Fokus; Tastatur-Bedienbarkeit.
- Zustand nicht nur über Farbe kommunizieren (auch Text/Icon).

## Nicht tun
- **Keine direkte DOM-Manipulation** (`document`, `nativeElement.innerHTML`) — Bindings/`Renderer2`/Signals nutzen.
- Kein `any` in Templates/Components; keine Logik in Konstruktoren (dafür `ngOnInit`/Signals).
