// Typen entsprechen dem Kontrakt in openapi.yaml (Beträge als number im JSON).

export interface AddPositionRequest {
  isin: string
  quantity: number
  buyInPrice: number
}

export interface Position {
  isin: string
  quantity: number
  buyInPrice: number
}

export interface MoneyResponse {
  amount: number
  currency: string
}

// Ausbaustufe 1 — Transaktionen & Bestände
export type TransactionType = 'BUY' | 'SELL'

export interface AddTransactionRequest {
  type: TransactionType
  isin: string
  date: string // ISO-Datum (YYYY-MM-DD)
  quantity: number
  price: number
}

export interface Holding {
  isin: string
  quantity: number
  averageBuyIn: number
}

// Ausbaustufe 2 — Benchmark-Vergleich
export interface BenchmarkComparison {
  benchmarkId: string
  portfolioReturnPct: number
  benchmarkReturnPct: number
  outperformancePct: number
}
