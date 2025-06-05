package views;

import entities.JogoEntity;
import services.CampeonatoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.event.*;

public class DlgInserirJogo extends JDialog {

    private JComboBox<String> cmbTimeCasa;
    private JComboBox<String> cmbTimeVisitante;
    private JSpinner spnGolsCasa;
    private JSpinner spnGolsVisitante;
    private JButton btnSalvar;
    private JButton btnCancelar;

    private CampeonatoService campeonatoService;
    private boolean resposta;

    public DlgInserirJogo() {
        campeonatoService = new CampeonatoService();
        resposta = false;

        setTitle("Inserir Jogo");
        setModal(true);
        setSize(520, 200);
        setResizable(false);
        initComponents();
        UIConfig.updateUIWithAutoScale(this);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        List<String> apelidos = campeonatoService.listarApelidos();
        cmbTimeCasa = new JComboBox<>(apelidos.toArray(new String[0]));
        cmbTimeVisitante = new JComboBox<>(apelidos.toArray(new String[0]));

        SpinnerNumberModel modelCasa = new SpinnerNumberModel(0, 0, 50, 1);
        SpinnerNumberModel modelVisitante = new SpinnerNumberModel(0, 0, 50, 1);
        spnGolsCasa = new JSpinner(modelCasa);
        spnGolsVisitante = new JSpinner(modelVisitante);

        Dimension dimCombo = new Dimension(150, 30);
        cmbTimeCasa.setPreferredSize(dimCombo);
        cmbTimeVisitante.setPreferredSize(dimCombo);
        spnGolsCasa.setPreferredSize(new Dimension(50, 30));
        spnGolsVisitante.setPreferredSize(new Dimension(50, 30));

        // ---------- Primeira linha: labels alinhados ----------
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painelCentral.add(new JLabel("Time da Casa"), gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.EAST;
        painelCentral.add(new JLabel("Time Visitante",SwingConstants.RIGHT), gbc);

        // ---------- Segunda linha: componentes ----------
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        painelCentral.add(cmbTimeCasa, gbc);

        gbc.gridx = 1;
        painelCentral.add(spnGolsCasa, gbc);

        gbc.gridx = 2;
        painelCentral.add(new JLabel("X"), gbc);

        gbc.gridx = 3;
        painelCentral.add(spnGolsVisitante, gbc);

        gbc.gridx = 4;
        painelCentral.add(cmbTimeVisitante, gbc);

        // ---------- Botões ----------
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        btnSalvar.setPreferredSize(new Dimension(150, 30));
        btnCancelar.setPreferredSize(new Dimension(150, 30));

        btnSalvar.addActionListener(this::acionou);
        btnCancelar.addActionListener(this::acionou);

        pnlBotoes.add(btnSalvar);
        pnlBotoes.add(btnCancelar);

        // ---------- Monta tudo ----------
        add(painelCentral, BorderLayout.CENTER);
        add(pnlBotoes, BorderLayout.SOUTH);
        pack();
    }

    private void acionou(ActionEvent e) {
        if (e.getSource() == btnCancelar) {
            resposta = false;
            setVisible(false);
            return;
        }

        try {
            String apelidoCasa = (String) cmbTimeCasa.getSelectedItem();
            String apelidoVisitante = (String) cmbTimeVisitante.getSelectedItem();

            if (apelidoCasa == null || apelidoVisitante == null) {
                throw new Exception("Selecione ambos os times.");
            }

            if (apelidoCasa.equals(apelidoVisitante)) {
                throw new Exception("Os times não podem ser iguais.");
            }

            int golsCasa = (int) spnGolsCasa.getValue();
            int golsVisitante = (int) spnGolsVisitante.getValue();

            JogoEntity jogo = new JogoEntity(apelidoCasa, apelidoVisitante, golsCasa, golsVisitante);
            campeonatoService.inserirJogo(jogo);
            JOptionPane.showMessageDialog(this, "Jogo registrado com sucesso.");
            resposta = true;
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static DlgInserirJogo _instancia = null;

    public static boolean showDialog() {
        if (_instancia == null)
            _instancia = new DlgInserirJogo();
        return _instancia.executar();
    }

    private boolean executar() {
        setVisible(true);
        return resposta;
    }

    public static void main(String[] args) {
        DlgInserirJogo.showDialog();
    }
}
