package br.com.starlog.amador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import br.com.starlog.controller.TransmissaoController;

/**
 * CAMADA VIEW: coleta os dados e apresenta o resultado do Controller.
 */
public class TelaCaoticaLegada extends JFrame {

    private final JTextField txtRemetente;
    private final JTextField txtMensagem;
    private final JTextField txtChaveCifra;
    private final JButton btnDespacharCifrado;
    private final JTextArea areaLog;

    private final TransmissaoController controller = new TransmissaoController();

    public TelaCaoticaLegada() {
        super("SISTEMA LEGADO CORROMPIDO - STARLOG");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Remetente da Frota:"));
        txtRemetente = new JTextField(20);
        add(txtRemetente);

        add(new JLabel("Mensagem Secreta:"));
        txtMensagem = new JTextField(20);
        add(txtMensagem);

        add(new JLabel("Chave de Deslocamento (César 1 a 25):"));
        txtChaveCifra = new JTextField(5);
        add(txtChaveCifra);

        btnDespacharCifrado = new JButton("Cifrar e Transmitir");
        add(btnDespacharCifrado);

        areaLog = new JTextArea(10, 35);
        add(new JScrollPane(areaLog));

        btnDespacharCifrado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pacote = controller.processarTransmissao(
                            txtRemetente.getText(),
                            txtMensagem.getText(),
                            txtChaveCifra.getText());
                    areaLog.append(pacote + "\n");
                    JOptionPane.showMessageDialog(TelaCaoticaLegada.this,
                            "Transmissão efetuada com sucesso!");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(TelaCaoticaLegada.this,
                            ex.getMessage(),
                            "Erro de transmissão",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCaoticaLegada().setVisible(true));
    }
}
