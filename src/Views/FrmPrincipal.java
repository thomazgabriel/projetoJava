package views;

import models.TimeModel;
import services.CampeonatoService;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.util.List;

public class FrmPrincipal extends JFrame {

    private CampeonatoService campeonatoService;
    private JTable tabelaTimes;
    private DefaultTableModel tabelaModel;
    private JButton btnZerarCampeonato;
    private JButton btnInserirTime;
    private JButton btnInserirJogo;

    public FrmPrincipal() {
        campeonatoService = new CampeonatoService();

        setSize(800, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de Campeonato");

        initComponents();
        UIConfig.updateUIWithAutoScale(this);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Painel superior com os botões de ação
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));

        btnZerarCampeonato = new JButton("Reiniciar Campeonato");
        btnInserirTime = new JButton("Inserir Time");
        btnInserirJogo = new JButton("Inserir Jogo");

        btnZerarCampeonato.setPreferredSize(new Dimension(180, 30));
        btnInserirTime.setPreferredSize(new Dimension(150, 30));
        btnInserirJogo.setPreferredSize(new Dimension(150, 30));

        btnZerarCampeonato.addActionListener(this::acionou);
        btnInserirTime.addActionListener(this::acionou);
        btnInserirJogo.addActionListener(this::acionou);

        painelSuperior.add(btnZerarCampeonato);
        painelSuperior.add(btnInserirTime);
        painelSuperior.add(btnInserirJogo);

        add(painelSuperior, BorderLayout.NORTH);

        // Tabela de times
        criarTabela();

        JScrollPane scrollPane = new JScrollPane(tabelaTimes);
        add(scrollPane, BorderLayout.CENTER);

        pack();
    }
    private void atualizarTabela(){
        List<TimeModel> times = campeonatoService.obterClassificacao(); // Listando os times com base na classificação
        tabelaModel.setRowCount(0);
        for (TimeModel time : times) {
            Object[] row = {
                    time.getPosicao(),
                    time.getApelido(),
                    time.getNome(),
                    time.getPontos(),
                    time.getGolsMarcados(),
                    time.getGolsSofridos(),
                    time.getSaldoGols(),
                    new InfoButtonAction("Editar",time),
                    new InfoButtonAction("Remover",time)
            };
            tabelaModel.addRow(row);
        }
    }
    private void criarTabela() {
        List<TimeModel> times = campeonatoService.obterClassificacao(); // Listando os times com base na classificação
        String[] colunas = {"#", "Apelido", "Nome", "Pts", "GP", "GC", "SG", "Editar", "Remover"};
        tabelaModel = new DefaultTableModel(colunas, 0);

        for (TimeModel time : times) {
            Object[] row = {
                    time.getPosicao(),
                    time.getApelido(),
                    time.getNome(),
                    time.getPontos(),
                    time.getGolsMarcados(),
                    time.getGolsSofridos(),
                    time.getSaldoGols(),
                    new InfoButtonAction("Editar",time),
                    new InfoButtonAction("Remover",time)
            };
            tabelaModel.addRow(row);
        }

        tabelaTimes = new JTable(tabelaModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7 || column == 8; // Apenas os botões "Editar" e "Remover" são clicáveis
            }
        };


        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tabelaTimes.getColumn("#").setCellRenderer(rightRenderer);
        tabelaTimes.getColumn("Pts").setCellRenderer(rightRenderer);
        tabelaTimes.getColumn("GP").setCellRenderer(rightRenderer);
        tabelaTimes.getColumn("GC").setCellRenderer(rightRenderer);
        tabelaTimes.getColumn("SG").setCellRenderer(rightRenderer);

        tabelaTimes.getColumn("#").setPreferredWidth(40);   // largura ideal
        tabelaTimes.getColumn("Apelido").setPreferredWidth(150);   // largura ideal
        tabelaTimes.getColumn("Nome").setPreferredWidth(300);   // largura ideal
        tabelaTimes.getColumn("Pts").setPreferredWidth(40);   // largura ideal
        tabelaTimes.getColumn("GP").setPreferredWidth(40);   // largura ideal
        tabelaTimes.getColumn("GC").setPreferredWidth(40);   // largura ideal
        tabelaTimes.getColumn("SG").setPreferredWidth(40);   // largura ideal
        tabelaTimes.getColumn("Editar").setPreferredWidth(120);   // largura ideal
        tabelaTimes.getColumn("Remover").setPreferredWidth(120);   // largura ideal
        //tabelaTimes.getColumnModel().getColumn(0).setMinWidth(50);         // largura mínima
        //tabelaTimes.getColumnModel().getColumn(0).setMaxWidth(100);

        tabelaTimes.setRowHeight(30); // Ajustando a altura das linhas
        // Renderer
        tabelaTimes.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        tabelaTimes.getColumn("Remover").setCellRenderer(new ButtonRenderer());

        // Editor
        tabelaTimes.getColumn("Editar").setCellEditor(new ButtonEditor(e -> {
            String apelido = e.getActionCommand();
            editarTime(apelido);
        }));

        tabelaTimes.getColumn("Remover").setCellEditor(new ButtonEditor(e -> {
            String apelido = e.getActionCommand();
            removerTime(apelido);
        }));
    }

    private void editarTime(String apelido) {
        if (DlgTime.showDialog(apelido))
            atualizarTabela(); 
    }

    private void removerTime(String apelido) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja remover o time " + apelido + "?",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            campeonatoService.removerTime(apelido);
            JOptionPane.showMessageDialog(this, "Time removido com sucesso.");
            atualizarTabela();
        }
    }

    private void acionou(ActionEvent e) {
        Object origem = e.getSource();

        try {
            if (origem == btnZerarCampeonato) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja reiniciar o campeonato?",
                        "Confirmar Zeramento",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    campeonatoService.reiniciarCampeonato();
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Campeonato zerado com sucesso.");
                }

            } else if (origem == btnInserirTime) {
                if (DlgTime.showDialog()) {
                    atualizarTabela();
                }
            } else if (origem == btnInserirJogo) {
                DlgInserirJogo.showDialog();
                atualizarTabela();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmPrincipal frmPrincipal = new FrmPrincipal();
            frmPrincipal.setVisible(true);
        });
    }

    class InfoButtonAction{
        private String action;
        private TimeModel time;
        public InfoButtonAction(String action, TimeModel time)
        {
            this.action = action;
            this.time = time;
        }
        public String toString(){
            return action;
        }
        public String getAction(){
            return action;
        }
        public TimeModel getTime(){
            return time;
        }
    }

    // Renderizador de botões para a tabela (Editar/Remover)
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
             UIConfig.updateUIWithAutoScale(this);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText(value != null ? value.toString() : "");
            return this;
        }
    }
    class ButtonEditor extends DefaultCellEditor {
        private final JButton button = new JButton();
        private Object valueOld;
        private boolean isPushed;
        private final ActionListener action;

        public ButtonEditor(ActionListener action) {
            super(new JCheckBox()); // editor dummy
            this.action = action;

            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
            UIConfig.updateUIWithAutoScale(button);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                    boolean isSelected, int row, int column) {
            valueOld = value;
            button.setText(value.toString());
            button.setActionCommand(((InfoButtonAction)value).getTime().getApelido()); // Passa a linha como "comando"
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                action.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, button.getActionCommand()));
            }
            isPushed = false;
            return valueOld; 
        }
    }
}
