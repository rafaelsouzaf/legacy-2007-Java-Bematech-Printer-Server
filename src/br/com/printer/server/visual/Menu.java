package br.com.printer.server.visual;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener {

    // barra do menu
    private JMenuBar barraMenu = new JMenuBar();

    // opcao do menu
    private JMenu arquivo = new JMenu("Arquivo");
    private JMenuItem arquivoEsconder = new JMenuItem("Esconder");
    private JMenuItem arquivoSair = new JMenuItem("Sair");

    // opcao do menu
    private JMenu ajuda = new JMenu("Ajuda");
    private JMenuItem ajudaSobre = new JMenuItem("Sobre");

    public Menu() {

        // menu
        arquivo.add(arquivoEsconder);
        arquivo.add(arquivoSair);
        ajuda.add(ajudaSobre);

        // adiciona menu a barra
        barraMenu.add(arquivo);
        barraMenu.add(ajuda);

        // adiciona eventos
        arquivoSair.addActionListener(this);
        ajudaSobre.addActionListener(this);
        arquivoEsconder.addActionListener(this);

    }

    public static void sobre() {

        String sobre = "Desenvolvido por Rafael S. Fijalkowski\r\n\r\n"
                + "A fun��o desse programa � monitorar a porta 15900 e encaminhar\r\nos textos recebidos para a impressora fiscal Bematech.";

        JOptionPane.showMessageDialog(null, sobre, "Sobre", JOptionPane.INFORMATION_MESSAGE);

    }

    public JMenuBar getMenu() {

        return barraMenu;

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == arquivoSair) {

            System.exit(1);

        } else if (e.getSource() == ajudaSobre) {

            sobre();

        } else if (e.getSource() == arquivoEsconder) {

            Visual.esconde();

        }

    }

}
