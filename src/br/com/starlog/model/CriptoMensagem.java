package br.com.starlog.model;

import java.util.Locale;

/**
 * Modelo de domínio de uma mensagem criptografada.
 */
public class CriptoMensagem {
    private final String remetente;
    private final String mensagem;
    private final int chaveDeslocamento;

    public CriptoMensagem(String remetente, String mensagem, int chaveDeslocamento) {
        if (remetente == null || remetente.trim().isEmpty()) {
            throw new IllegalArgumentException("Remetente não pode ser nulo ou vazio.");
        }
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem não pode ser nula ou vazia.");
        }
        if (chaveDeslocamento < 1 || chaveDeslocamento > 25) {
            throw new IllegalArgumentException("Chave deve estar entre 1 e 25.");
        }

        this.remetente = remetente.trim().toUpperCase(Locale.ROOT);
        this.mensagem = mensagem.trim().toUpperCase(Locale.ROOT);
        this.chaveDeslocamento = chaveDeslocamento;
    }

    public String getRemetente() {
        return remetente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getChaveDeslocamento() {
        return chaveDeslocamento;
    }

    /**
     * Aplica o algoritmo da Cifra de César com deslocamento circular ('A'..'Z').
     * Preserva espaços e pontuações sem alteração.
     */
    public String cifrar() {
        StringBuilder cifrada = new StringBuilder(mensagem.length());
        for (int i = 0; i < mensagem.length(); i++) {
            char caractere = mensagem.charAt(i);
            if (caractere >= 'A' && caractere <= 'Z') {
                caractere = (char) ('A' + (caractere - 'A' + chaveDeslocamento) % 26);
            }
            cifrada.append(caractere);
        }
        return cifrada.toString();
    }

    /**
     * Calcula o checksum somando o valor ordinal ASCII de cada caractere da mensagem em maiúsculo.
     */
    public int calcularChecksum() {
        int checksum = 0;
        for (int i = 0; i < mensagem.length(); i++) {
            checksum += mensagem.charAt(i);
        }
        return checksum;
    }
}
