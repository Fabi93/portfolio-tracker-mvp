package com.fintech.portfolio.application;

import com.fintech.portfolio.domain.Holding;
import com.fintech.portfolio.domain.HoldingsCalculator;
import com.fintech.portfolio.domain.Transaction;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

/**
 * Verarbeitet Transaktionen (Ausbaustufe 1) und leitet daraus den aktuellen Bestand ab.
 * In-memory (MVP). Ein Verkauf über den Bestand hinaus wird beim Hinzufügen abgelehnt.
 */
@ApplicationScoped
public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();

    /** Fügt eine Transaktion hinzu; wirft {@link IllegalArgumentException}, wenn ein SELL den Bestand überschreitet. */
    public void add(Transaction transaction) {
        List<Transaction> candidate = new ArrayList<>(transactions);
        candidate.add(transaction);
        HoldingsCalculator.from(candidate); // validiert (Übermenge-Verkauf) vor dem Persistieren
        transactions.add(transaction);
    }

    public List<Transaction> transactions() {
        return List.copyOf(transactions);
    }

    /** Setzt die (In-Memory-)Transaktionshistorie zurück. */
    public void clear() {
        transactions.clear();
    }

    public List<Holding> holdings() {
        return HoldingsCalculator.from(transactions);
    }
}
