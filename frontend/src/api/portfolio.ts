import type {
  AddPositionRequest,
  AddTransactionRequest,
  BenchmarkComparison,
  Holding,
  MoneyResponse,
  Position,
} from './types'

// Backend-Basis-URL; im Dev auf Quarkus (:8080). Über VITE_API_BASE überschreibbar.
const API_BASE = (import.meta.env.VITE_API_BASE as string | undefined) ?? 'http://localhost:8080'

async function ensureOk(res: Response): Promise<Response> {
  if (res.ok) return res
  if (res.status === 400) throw new Error('Ungültige Eingaben')
  throw new Error(`Serverfehler (${res.status})`)
}

export async function addPosition(request: AddPositionRequest): Promise<void> {
  const res = await fetch(`${API_BASE}/api/portfolio/positions`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(request),
  })
  await ensureOk(res)
}

export async function listPositions(): Promise<Position[]> {
  const res = await fetch(`${API_BASE}/api/portfolio/positions`)
  await ensureOk(res)
  return res.json() as Promise<Position[]>
}

export async function getValue(): Promise<MoneyResponse> {
  const res = await fetch(`${API_BASE}/api/portfolio/value`)
  await ensureOk(res)
  return res.json() as Promise<MoneyResponse>
}

export async function getPerformance(): Promise<MoneyResponse> {
  const res = await fetch(`${API_BASE}/api/portfolio/performance`)
  await ensureOk(res)
  return res.json() as Promise<MoneyResponse>
}

export async function addTransaction(request: AddTransactionRequest): Promise<void> {
  const res = await fetch(`${API_BASE}/api/portfolio/transactions`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(request),
  })
  await ensureOk(res)
}

export async function listHoldings(): Promise<Holding[]> {
  const res = await fetch(`${API_BASE}/api/portfolio/holdings`)
  await ensureOk(res)
  return res.json() as Promise<Holding[]>
}

export async function compareBenchmark(benchmarkId: string): Promise<BenchmarkComparison> {
  const res = await fetch(`${API_BASE}/api/portfolio/benchmark?benchmarkId=${encodeURIComponent(benchmarkId)}`)
  await ensureOk(res)
  return res.json() as Promise<BenchmarkComparison>
}
