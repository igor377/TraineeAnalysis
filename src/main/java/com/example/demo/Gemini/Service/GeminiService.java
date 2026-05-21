package com.example.demo.Gemini.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.genai.Client;

@Service
public class GeminiService {

    private final Client ai;

    public GeminiService(@Value("${gemini.api.key}") String apiKey) {
        this.ai = Client.builder().apiKey(apiKey).build();
    }

    public String pedirResposta(String mensagem) {
        try {
            var response = this.ai.models.generateContent("gemini-2.5-flash", mensagem, null);
            return response.text();
        } catch (Exception e) {
            return "Erro ao chamar o Gemini: " + e.getMessage();
        }
    }
}