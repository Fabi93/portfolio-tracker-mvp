# AI-Pipeline — Strategie (der Weg)

_Status: **In Arbeit** · 2026-08-28_
_Strategie darf sich ändern — die [[VISION]] bleibt konstant._

Umsetzt die Vision: *„Die Maschine baut, das Urteil bleibt bei mir."* → eine Kette spezialisierter Agenten **mit menschlichen Gates**.

## Prozesskette (Arbeitsstand, freigegeben als Zielbild der Kette)

```
Vision → Strategie → Roadmap
  → 🚪 Ticket + DoR                         (Gate: richtiger Slice? — MENSCH)
  → Tech-Approach / Design                  (bewusste Up-front-Entscheidung — Gap 1.2)
  → 🚪 Acceptance Tests (TESTER)            (Gate: fangen die Tests die echte Absicht? — MENSCH)
  → Implementation (DEVELOPER)  — hält AC-Tests grün, hält DoD
  → Unit / Integration / API-Tests
  → 🚪 Review: SonarQube · Clean Code · Clean Architecture · DoD   (finales Gate — MENSCH)
        ↺ bei Fehler: zurück zur betroffenen Stufe (kein linearer Durchlauf)
```

## Konstruktions-Prinzipien (aus der ehrlichen Kritik)
1. **Menschliche Gates explizit markiert.** Mindestens: nach Ticket, nach Acceptance Tests, beim Review. Nordstern = Control, nicht Lights-out.
2. **Design-/Tech-Approach-Gate** zwischen Ticket und Implementation — sonst wählt der Developer-Agent den Ansatz und der 1.2-Gap bleibt offen.
3. **DoD ist nicht nur am Ende.** Jede Stufe hat ein Exit-Kriterium; das Review-DoD ist das *letzte* Netz, nicht das *einzige* (früh billig, spät teuer).
4. **Die Pipeline selbst in vertikalen Scheiben bauen** (S2 angewandt auf den Bau): eine Stufe, bewiesen, dann die nächste. Alle 8 Stufen auf einmal = das eigene „bau das ganze Feature"-Anti-Pattern.

## Offene Entscheidungen (nächster Schritt)
- [ ] **Build-Reihenfolge / erste vertikale Scheibe** — welche Stufe zuerst als Agent? (bewusste Up-front-Entscheidung = Gap-1.2-Rep)
- [ ] **Allgemeingültige DoD** finalisieren (Fabians Calls stehen aus) → Skill `definition-of-done` + portable `DEFINITION_OF_DONE.md`
- [ ] Vehikel bestätigen: `/Users/fabian/kungfu-platform-nx`?

## Bestehende Bausteine
- ✅ **Vision-Stufe:** Agent `product-owner` (via Skill `define-vision`).
- ✅ **Strategie-Stufe:** Agent `product-owner` (via Skill `define-strategy`, 2026-08-28) — Rumelt-Kernel + Playing-to-Win + Pichler, schlank; setzt freigegebene Vision voraus.
- ✅ **Roadmap-Stufe:** Agent `product-owner` (via Skill `define-roadmap`, 2026-08-30) — Pichler GO + Now/Next/Later, outcome-orientiert; setzt freigegebene Strategie voraus.
- ❌ Refinement (Ticket/DoR · Tester · Developer) · Implementation · Review: noch nicht gebaut.

## Verknüpft
- Vision: `./VISION.md`
