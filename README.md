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
```

---

## Esecuzione con Docker

### Prerequisiti
- Docker installato sul sistema
- Docker Compose installato sul sistema
- Una API key valida di OpenAI

### Setup e avvio

1. **Clona il repository**
   ```bash
   git clone <url-repository>
   cd smart-stock-analysis
   ```

2. **Configura la tua API key di OpenAI**
   ```bash
   export OPENAI_API_KEY=la-tua-api-key
   ```

3. **Costruisci e avvia il container**
   ```bash
   docker-compose up --build
   ```

4. **Accedi all'applicazione**

   L'applicazione sarà disponibile all'indirizzo: http://localhost:8080

### Endpoint API disponibili

- `/predict?ticker=SIMBOLO` - Ottieni una previsione per il simbolo specificato

### Note per lo sviluppo

- Per eseguire l'applicazione in modalità sviluppo senza Docker:
  ```bash
  mvn spring-boot:run
  ```

- Per eseguire i test:
  ```bash
  mvn test
  ```

---

## Requisiti

- Java 17 o superiore
- Maven 3.8 o superiore
- API key di OpenAI (da configurare come variabile d'ambiente `OPENAI_API_KEY`)