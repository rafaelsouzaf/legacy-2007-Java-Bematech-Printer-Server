package br.com.printer.server.visual;

import snoozesoft.systray4j.*;

import javax.swing.*;
import java.awt.*;

public class Visual implements SysTrayMenuListener {

    private static final JFrame jframe = new JFrame();
    private static Container container;
    private static JTextArea textArea;

    public Visual() {

        // container
        container = jframe.getContentPane();

        // carrega menu
        jframe.setJMenuBar(new br.com.printer.server.visual.Menu().getMenu());

        // carrega painel
        definePainel();

        // define outros parametros
        jframe.setTitle("Server Bematech");
        //jframe.setUndecorated(true);
        jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //  encerra programa se clicar no X

        // create the menu da bandeja
        createMenu();

        //jframe.setIconImage( new ImageIcon(getClass().getResource("printer.gif")).getImage());
        jframe.setSize(600, 280); // tamanho da janela
        jframe.setResizable(false); // impede que usuario redefina o tamanho
        jframe.setLocationRelativeTo(null); // centraliza janela na tela
        jframe.pack(); // ajusta componentes no frame
        jframe.setVisible(false); // imprime programa na tela

    }

    public static void addText(String texto) {

        // se o texto ja conter mais de 1000 caracteres, entao limpa
        if (textArea.getLineCount() > 200)
            textArea.setText("");

        textArea.append(texto);

    }

    public static void alertErro(String texto) {

        JOptionPane pane = new JOptionPane(texto, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = pane.createDialog(jframe, "Erro!");
        dialog.setModal(false);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);

    }

    public static void alertMessage(String texto) {

        JOptionPane pane = new JOptionPane(texto, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(jframe, "Informa��o!");
        dialog.setModal(false);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);

    }

    public static void esconde() {

        jframe.setVisible(false);

    }

    public void definePainel() {

        // Painel padrao
        JPanel painel = new JPanel();
        painel.setLayout(new FlowLayout());

        // adiciona textarea
        textArea = new JTextArea(12, 45);
        textArea.setEditable(false);
        textArea.setAutoscrolls(true);
        //textArea.setBorder(BorderFactory.createTitledBorder("Log de Impress�o"));
        textArea.setMargin(new Insets(5, 5, 5, 5));

        // cria barras de scroll e seta propriedades
        JScrollPane pane = new JScrollPane(textArea);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // adiciona ao painel
        painel.add(pane, SwingConstants.CENTER);

        // adiciona painel ao container
        container.add(painel, BorderLayout.CENTER);

        // atualiza componentes no container
        container.validate();

    }

    public void iconLeftDoubleClicked(SysTrayMenuEvent e) {

        System.out.println("2 clicks");

    }

    public void iconLeftClicked(SysTrayMenuEvent e) {

        System.out.println("1 click");

    }

    void createMenu() {

        // create an exit item
        SysTrayMenuItem itemExit = new SysTrayMenuItem("Fechar", "exit");
        itemExit.addSysTrayMenuListener(this);

        // create an about item
        SysTrayMenuItem itemAbout = new SysTrayMenuItem("Sobre...", "about");
        itemAbout.addSysTrayMenuListener(this);

        SysTrayMenuItem itemMostrar = new SysTrayMenuItem("Mostrar", "Mostrar");
        itemMostrar.addSysTrayMenuListener(this);

        SysTrayMenuItem itemEsconder = new SysTrayMenuItem("Esconder", "Esconder");
        itemEsconder.addSysTrayMenuListener(this);

        // create the main menu
        SysTrayMenu menu = new SysTrayMenu(new SysTrayMenuIcon("printer"), "Impress�o Bematech");

        // insert items
        menu.addItem(itemExit);
        menu.addItem(itemAbout);
        menu.addSeparator();
        menu.addItem(itemEsconder);
        menu.addItem(itemMostrar);

        // don�t forget to assign listeners to the icons
        SysTrayMenuIcon teste = new SysTrayMenuIcon("printer");
        teste.addSysTrayMenuListener(this);

    }

    public void menuItemSelected(SysTrayMenuEvent e) {

        if (e.getActionCommand().equals("exit")) {

            System.exit(0);

        } else if (e.getActionCommand().equals("about")) {

            br.com.printer.server.visual.Menu.sobre();

        } else if (e.getActionCommand().equals("Mostrar")) {

            jframe.setVisible(true);

        } else if (e.getActionCommand().equals("Esconder")) {

            jframe.setVisible(false);

        }

    }

}
