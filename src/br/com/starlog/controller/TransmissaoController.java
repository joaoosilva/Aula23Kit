package br.com.starlog.controller;

import br.com.starlog.model.CriptoMensagem;
import java.util.ArrayList;
import java.util.List;

/**
 * Orquestra a criação e o armazenamento em memória dos pacotes de transmissão.
 */
public class TransmissaoController {

    private final List<String> bufferTransmissoes = new ArrayList<>();

    /**
     * Orquestra a transmissão da mensagem.
     * 
     * @param remetenteRaw remetente informado pela View
     * @param msgRaw       mensagem informada pela View
     * @param chaveStr     chave informada como texto pela View
     * @return Pacote formatado no padrão: [REMETENTE] #CHECKSUM -> TEXTO_CIFRADO
     */
    public String processarTransmissao(String remetenteRaw, String msgRaw, String chaveStr) {
        final int chave;
        try {
            chave = Integer.parseInt(chaveStr.trim());
        } catch (NumberFormatException | NullPointerException ex) {
            throw new IllegalArgumentException("Chave deve ser um número inteiro válido.", ex);
        }

        CriptoMensagem cripto = new CriptoMensagem(remetenteRaw, msgRaw, chave);
        String pacote = String.format(
                "[%s] #%d -> %s",
                cripto.getRemetente(),
                cripto.calcularChecksum(),
                cripto.cifrar());
        bufferTransmissoes.add(pacote);
        return pacote;
    }

    public int getTotalTransmissoes() {
        return bufferTransmissoes.size();
    }
}
