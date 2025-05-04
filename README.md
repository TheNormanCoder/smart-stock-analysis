# Smart Stock Analysis

> Previsione e interpretazione intelligente dei prezzi azionari usando Java, Spring Boot, DJL, Alpha Vantage e Spring AI (OpenAI GPT-4).

---

## Funzionalità

- ✔️ Raccolta automatica dei dati storici tramite:
    - Yahoo Finance (con limitazioni di rate limit)
    - **Alpha Vantage API (raccomandato)**
- ✔️ Predizione del prezzo futuro con rete neurale semplice usando DJL (MXNet backend)
- ✔️ Analisi descrittiva del trend tramite LLM (GPT-4/OpenAI) integrato con Spring AI
- ✔️ API REST esposta con Spring Boot
- ➕ Salvataggio dei dati su database
- ➕ Dashboard frontend (in sviluppo)

---

## Architettura

```text
[ Alpha Vantage API ]
        ↓
[ DJL Model (es. Linear) ]
        ↓
(Valore predetto)
        ↓
[ Spring AI + OpenAI (GPT-4) ]
        ↓
(Testo interpretativo)
```

---

## Esecuzione con Docker

### Prerequisiti
- Docker installato sul sistema
- Docker Compose installato
- API key valide per:
    - OpenAI (`OPENAI_API_KEY`)
    - Alpha Vantage (`ALPHAVANTAGE_API_KEY`)

### Setup e avvio

1. **Clona il repository**
   ```bash
   git clone <url-repository>
   cd smart-stock-analysis
   ```

2. **Esporta le variabili d'ambiente**
   ```bash
   export OPENAI_API_KEY=la-tua-api-key
   export ALPHAVANTAGE_API_KEY=la-tua-api-key
   ```

3. **Costruisci e avvia l'applicazione**
   ```bash
   docker-compose up --build
   ```

4. **Accedi all'applicazione**
   Apri il browser all'indirizzo: [http://localhost:8080](http://localhost:8080)

---

### Endpoint API disponibili

- `/predict/yahoofinace?ticker=SIMBOLO` — Previsione utilizzando Yahoo Finance (soggetto a limiti)
- `/predict/alphavantage?ticker=SIMBOLO` — ✅ **Previsione consigliata con Alpha Vantage**
- `/explain?ticker=SIMBOLO` — Interpretazione dei dati tramite GPT-4

---

### Note per lo sviluppo

- Esecuzione locale:
  ```bash
  mvn spring-boot:run
  ```
- Esecuzione test:
  ```bash
  mvn test
  ```

---

## Requisiti

- Java 17+
- Maven 3.8+
- Docker + Docker Compose
- API key di:
    - OpenAI (`OPENAI_API_KEY`)
    - Alpha Vantage (`ALPHAVANTAGE_API_KEY`)