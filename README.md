
# 📊 Smart Stock Analysis — Predizione Azionaria con DJL (PyTorch)

Questo progetto analizza e predice l’andamento di un titolo azionario tramite una rete neurale MLP costruita con **DJL** e **backend PyTorch**. Include anche un'integrazione con **OpenAI** per l'analisi qualitativa e la generazione di etichette di apprendimento automatico.

---

## 🔧 Tecnologie utilizzate

- ✅ Java 17
- ✅ Spring Boot 3
- ✅ DJL (Deep Java Library) con **PyTorch backend**
- ✅ OpenAI API (Spring AI)
- ✅ Docker + Docker Compose
- ✅ AlphaVantage API
- ✅ CSV come storage incrementale dei dati

---

## ⚙️ Funzionalità principali

- 📈 Predizione del valore normalizzato futuro di un titolo
- 🤖 Addestramento automatico e incrementale del modello MLP
- 🧠 Generazione etichette tramite GPT (trend, buy signal, confidence)
- 🔄 Salvataggio e append dei dati nel CSV per retraining continuo
- 🌐 API REST per richiamare tutte le funzionalità

---

## 🚀 Avvio del progetto

### 1. Prepara le variabili ambiente

Crea un file `.env` o imposta manualmente:

```env
OPENAI_API_KEY=sk-xxx
ALPHAVANTAGE_API_KEY=your_key
```

### 2. Crea le cartelle richieste (una tantum)

```bash
mkdir -p datasets models
```

### 3. Avvio con Docker

```bash
docker-compose up --build
```

L'app sarà disponibile su: [http://localhost:8080](http://localhost:8080)

---

## 📁 Dataset CSV

Ogni chiamata all’API `/predict/alphavantage/advanced` genera una riga nel file:

```
datasets/training_data.csv
```

Questa riga contiene:
- ✅ I 100 feature normalizzati (es. chiusura, RSI, SMA…)
- ✅ Etichetta `trend` generata da OpenAI
- ✅ `buySignal` booleano
- ✅ `confidenceScore` numerico

Il file viene poi usato per **riaddestrare** automaticamente il modello neurale.

---

## 📡 Endpoint API disponibili

| Endpoint | Descrizione |
|----------|-------------|
| `/predict/alphavantage?ticker=SIMBOLO` | Predizione semplice basata su dati storici |
| `/predict/alphavantage/advanced?ticker=SIMBOLO` | 🔥 Predizione + retraining + analisi GPT |
| `/predict/yahoo?ticker=SIMBOLO` | Predizione basata su Yahoo Finance |
| `/model/train?datasetPath=...&modelPath=...` | Training manuale del modello |

---

## 🛠️ Struttura del progetto

```
src/main/java/com/example/smartstockanalysis/
├── controller/
│   └── StockAnalysisController.java
├── model/
│   └── PredictionResult.java
├── service/
│   ├── AlphaVantageService.java
│   ├── YahooFinanceService.java
│   ├── SpringAiService.java
│   ├── training/
│   │   ├── ModelTrainingService.java
│   │   ├── ModelTrainer.java
│   │   └── ModelConfigFactory.java
│   └── prediction/
│       └── AdvancedPredictionService.java
├── utils/
│   ├── DatasetUtils.java
│   ├── FeatureEngineeringUtils.java
│   └── LLMResponseParser.java
```

---

## 🧪 Test futuri / TODO

- [ ] Integrazione salvataggio su database
- [ ] Gestione versioning dataset (es. `training_data-v1.csv`)
- [ ] Valutazione metrica modello su batch storici
- [ ] Esportazione modello preaddestrato
- [ ] Dashboard frontend per visualizzazione predizioni

---

## 📬 Contatti

> Creato da NWB con DJL, Spring AI e tanta curiosità 😎
