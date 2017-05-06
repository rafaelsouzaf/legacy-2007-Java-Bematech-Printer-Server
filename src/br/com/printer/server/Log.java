package br.com.printer.server;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    public static void grava(String mensagem) {

        try {

            Date data = new Date();
            SimpleDateFormat dataFormatada = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat horaFormatada = new SimpleDateFormat("kk:mm");

            String titulo = dataFormatada.format(data);
            String horaAtual = horaFormatada.format(data);

            File file = new File("log/" + titulo + ".txt");
            RandomAccessFile grava = new RandomAccessFile(file, "rw");

            // envia cursos para ultima linha
            grava.seek(file.length());

            // cria mensagem para grava�ao
            String novaLinha = "\r\n-------------------------------------------\r\n";
            novaLinha = novaLinha + "Hor�rio:  " + horaAtual + "\r\n";
            novaLinha = novaLinha + "Mensagem: " + mensagem;

            // grava novos dados
            grava.writeBytes(novaLinha);

            // fecha arquivo
            grava.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
