# Smart Stock Analysis

> Previsione e interpretazione intelligente dei prezzi azionari usando Java, Spring Boot, DJL e Spring AI (OpenAI GPT-4).

---

## Funzionalità

- ✔️ Raccolta automatica dei dati storici di borsa tramite Yahoo Finance API
- ✔️ Predizione del prezzo futuro con rete neurale LSTM usando DJL
- ✔️ Analisi descrittiva del trend tramite LLM (GPT-4/OpenAI) integrato con Spring AI
- ✔️ API REST esposta con Spring Boot
- ➕ Salvataggio dei dati su database
- ➕ Dashboard frontend

---

## Architettura

```text
[ YahooFinance API ]
        ↓
[ DJL Model (LSTM) ]
        ↓
(Valore predetto)
        ↓
[ Spring AI + OpenAI (GPT-4) ]
        ↓
(Testo interpretativo)
