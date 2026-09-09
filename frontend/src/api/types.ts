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
