package br.com.printer.server;

import br.com.printer.server.visual.Visual;

import javax.print.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

// Essa classe � a responsavel pela impressao. Ela detecta a impressora
// instalada, recebe o texto e o imprime.
public class Printer {

    // variavel estatica porque ser� utilizada por inumeras threads
    private static PrintService impressora;

    public Printer() {

        detectaImpressoras();

    }

    // O metodo verifica se existe impressora conectada e a
    // define como padrao.
    public void detectaImpressoras() {

        try {

            DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);
            for (PrintService p : ps) {

                System.out.println("Impressora encontrada: " + p.getName());

                if (p.getName().contains("Text") || p.getName().contains("Generic")) {

                    Visual.addText("Impressora encontrada: " + p.getName());
                    impressora = p;
                    break;

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.grava("ERRO detectaImpressoras(): " + e);

        }

    }

    public synchronized boolean imprime(String texto) {

        // se nao existir impressora, entao avisa usuario
        // senao imprime texto
        if (impressora == null) {

            String msg = "Nennhuma impressora foi encontrada. Instale uma impressora padr�o \r\n(Generic Text Only) e reinicie o programa.";

            Log.grava("Nennhuma impressora foi encontrada.");
            // System.out.println(msg);
            Visual.alertErro(msg);

        } else {

            try {

                DocPrintJob dpj = impressora.createPrintJob();
                InputStream stream = new ByteArrayInputStream(texto.getBytes());

                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(stream, flavor, null);
                dpj.print(doc, null);

                return true;

            } catch (PrintException e) {

                e.printStackTrace();
                Log.grava("ERRO imprime(): " + e);
                Visual.addText("Erro ao imprimir: \r\n\r\n" + e);

            }

        }

        return false;

    }

}
