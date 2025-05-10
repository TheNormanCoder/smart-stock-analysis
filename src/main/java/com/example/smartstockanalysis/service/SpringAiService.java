package com.example.smartstockanalysis.service;

import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletion;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionMessage;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionMessage.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringAiService {

    @Autowired
    private OpenAiApi openAiApi;

    public String analyzeStock(String ticker, List<Double> normalizedPrices, double prediction) {
        String prompt = String.format("""
            Analizza il titolo %s sulla base dei dati di chiusura normalizzati:
            %s
            Predizione (valore normalizzato): %.4f
            Scrivi un commento in 3-5 righe sul trend del titolo.
            """,
                ticker,
                normalizedPrices.toString(),
                prediction
        );

        ChatCompletionRequest request = new ChatCompletionRequest(
                List.of(new ChatCompletionMessage(prompt, Role.USER)),
                "gpt-4o-mini",
                0.2
        );

        try {
            ChatCompletion response = openAiApi.chatCompletionEntity(request).getBody();
            return response.choices().get(0).message().content();
        } catch (Exception e) {
            e.printStackTrace();
            String cause = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            return String.format("Analisi non disponibile al momento. Causa: %s", cause);
        }
    }
}