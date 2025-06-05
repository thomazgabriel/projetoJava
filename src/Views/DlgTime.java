package views;

import entities.TimeEntity;
import services.CampeonatoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DlgTime extends JDialog {

    private JTextField txtApelido;
    private JTextField txtNome;
    private JButton btnSalvar;
    private JButton btnCancelar;

    private CampeonatoService campeonatoService;
    private TimeEntity timeExistente;
    private boolean resposta;
    public DlgTime() {
        campeonatoService = new CampeonatoService();
        timeExistente = null;
        resposta = false;

        setSize(350, 250);
        setLayout(new BorderLayout());
        setResizable(false);
        initComponents();

        setModal(true); // necessario para bloquear a janela
        UIConfig.updateUIWithAutoScale(this);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Painel principal com centralização vertical
        JPanel painelCentral = new JPanel(new BorderLayout());

        // Painel de campos com GridBagLayout
        JPanel pnlCampos = new JPanel(new GridBagLayout());
        pnlCampos.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Espaço entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblApelido = new JLabel("Apelido:");
        JLabel lblNome = new JLabel("Nome:");

        txtApelido = new JTextField();
        txtNome = new JTextField();

        Dimension campoSize = new Dimension(200, 30);
        txtApelido.setPreferredSize(campoSize);
        txtNome.setPreferredSize(campoSize);

        // Linha 1 - Apelido
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        pnlCampos.add(lblApelido, gbc);

        gbc.gridx = 1;
        pnlCampos.add(txtApelido, gbc);

        // Linha 2 - Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        pnlCampos.add(lblNome, gbc);

        gbc.gridx = 1;
        pnlCampos.add(txtNome, gbc);

        painelCentral.add(Box.createVerticalGlue(), BorderLayout.NORTH);
        painelCentral.add(pnlCampos, BorderLayout.CENTER);
        painelCentral.add(Box.createVerticalGlue(), BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);

        // Painel de botões
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");  
        btnCancelar.setPreferredSize(new Dimension(150,30));
        btnSalvar.setPreferredSize(new Dimension(150,30));
        btnSalvar.addActionListener(this::acionou);
        btnCancelar.addActionListener(this::acionou);

        pnlBotoes.add(btnSalvar);
        pnlBotoes.add(btnCancelar);

        add(pnlBotoes, BorderLayout.SOUTH);

        pack(); //calcula o menor tamanha para mostrar todos os elementos
    }

    private boolean executar(String  apelido){
        timeExistente = apelido==null?null:campeonatoService.buscarTime(apelido);
        setTitle(timeExistente == null ? "Inserir Time" : "Editar Time");
        setLocationRelativeTo(getParent());
        preencherCampos();
        resposta = false;
        setVisible(true); 
        return resposta;
    }

    private void preencherCampos() {
        if (timeExistente == null)
        {
            txtApelido.setText("");
            txtApelido.setEnabled(true);
            txtNome.setText("");
        }
        else
        {
            txtApelido.setText(timeExistente.getApelido());
            txtApelido.setEnabled(false); // Apelido não deve ser alterado na edição
            txtNome.setText(timeExistente.getNome());
        }
    }

    private void acionou(ActionEvent e) {
        try {
            if (e.getSource() == btnCancelar)
                resposta=false;
            else if (e.getSource() == btnSalvar)
            {
                String apelido = txtApelido.getText().trim();
                String nome = txtNome.getText().trim();

                if (timeExistente == null) {
                    TimeEntity novoTime = new TimeEntity(0, apelido, nome, 0, 0, 0);
                    campeonatoService.inserirTime(novoTime);
                    JOptionPane.showMessageDialog(this, "Time inserido com sucesso.");
                } else {
                    timeExistente.setNome(nome); // Apenas o nome pode mudar
                    campeonatoService.editarTime(timeExistente);
                    JOptionPane.showMessageDialog(this, "Time atualizado com sucesso.");
                }
                resposta = true;
            }
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static DlgTime _instancia = null;
    public static boolean showDialog(){
        if (_instancia==null)
            _instancia = new DlgTime();
        return _instancia.executar(null);
    }
    public static boolean showDialog(String apelido){
         if (_instancia==null)
            _instancia = new DlgTime();
        return _instancia.executar(apelido);
    }

    public static void  main(String s[])
    {
        DlgTime.showDialog();
        /*if (DlgTime.showDialog("Flamengo"))
          System.out.println("ja formatei!");   
        else
          System.out.println("nao formatei!");   */
    }
}
