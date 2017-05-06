package br.com.printer.server;

import br.com.printer.server.visual.Visual;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

// Essa classe tem a fun��o de iniciar um daemon
// que ir� aguardar conexoes via socket. Para cada
// conexao recebida, ir� iniciar uma Thread e passar
// o texto recebido para a classe de Printer.
public class Server implements Runnable {

    private static Visual visual;
    private static Printer printer;
    private Socket socket;

    // m�todo que executa o programa, nele sao instanciados
    // a classe de impresao e a propria classe servidor
    public static void main(String args[]) {

        // cria instancia da classe visual
        visual = new Visual();

        // cria instancia da classe de printer
        printer = new Printer();

        // instancia a pr�pria classe
        Server server = new Server();

        // abre conexoes
        server.listener();

    }

    // metodo que ir� iniciar o servidor, recebendo conexoes
    // dos sockets e abrindo uma nova Thread para printer
    public void listener() {

        try {

            System.out.println("- Iniciou servidor");
            Log.grava("Server iniciou");
            Visual.addText("Iniciou Server Bematech.\r\n\r\n");

            ServerSocket server = new ServerSocket(15900);
            while (true) {

                socket = server.accept();
                new Thread(this).start();

            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.grava("ERRO listener(): " + e);

        }

        System.out.println("- Server fechou");
        Log.grava("Server fechou");

    }

    // metodo run() eh obrigat�rio quando se implementa
    // a interface Runnable. Nele fica a parte do c�digo que
    // ser� executada na nova Thread.
    public void run() {

        try {

            // avisa que conectou
            System.out.println("Conectou IP: " + socket.getLocalAddress());

            String mensagem;
            String textoCompleto = "";

            // L� as frases que o socket envia.
            BufferedReader leitor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((mensagem = leitor.readLine()) != null) {

                if (mensagem.equalsIgnoreCase("EXIT171"))
                    break;
                else
                    textoCompleto += mensagem + "\r\n";

            }

            // chama metodo imprime para imprimir o texto
            if (printer.imprime(textoCompleto)) {

                System.out.println("Texto impresso: " + textoCompleto);
                Visual.addText("Texto:\r\n" + textoCompleto);

            } else {

                Log.grava("Nao foi poss�vel imprimir o texto: " + textoCompleto);
                Visual.addText("Erro ao imprimir: \r\n" + textoCompleto);

            }

            // fecha conexoes
            leitor.close();
            socket.close();

        } catch (Exception e) {

            e.printStackTrace();
            Log.grava("ERRO run(): " + e);

        }

    }

}
