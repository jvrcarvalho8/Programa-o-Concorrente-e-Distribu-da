//Inicie o Eclipse IDE: Abra o Eclipse no seu computador12.
//Crie um novo projeto Java: Vá para File > New > Java Project. Dê um nome ao projeto e clique em Finish12.
//Crie uma nova classe: No Package Explorer (lado esquerdo da janela), selecione o projeto que você acabou de criar. Clique com o botão direito na pasta src, selecione New > Class no submenu. Dê um nome à classe e clique em Finish12.
//Escreva o código do programa: Agora você pode copiar o código do cliente que forneci anteriormente e colá-lo na nova classe que você acabou de criar. Lembre-se de substituir o endereço IP e a porta pelos valores corretos do servidor do seu professor.
//Salve o programa: Você pode salvar o programa pressionando Ctrl+S2.
//Execute o programa: Agora, pressione Ctrl+F11 ou clique no menu Run e selecione Run ou clique no botão Run1. Você também pode clicar com o botão direito na classe no Package Explorer ou Project Explorer e selecionar Run As > Java Application3.
//Veja a saída: A saída será exibida na janela do console do Eclipse3.

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Conectado");

            input = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
            return;
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        String line = "";
        while (!line.equals("Fim")) {
            try {
                line = input.readLine();
                out.writeUTF(line);
            } catch (IOException i) {
                System.out.println(i);
            }
        }

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("192.168.1.1", 5000);
    }
}



//------------------------------------------------------------------------------------------



package socket_client;

import java.io.*;
import java.net.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private static final String ENDERECO_SERVIDOR = "10.130.129.103";
    private static final int PORTA_SERVIDOR = 5000;

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Socket conexao = new Socket(ENDERECO_SERVIDOR, PORTA_SERVIDOR);
            ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());

            ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
            BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Conectado ao servidor.");

            System.out.print("Digite seu RA:");
            String seuRa = leitor.readLine();
            System.out.println("Seu RA: " + seuRa);
            saida.writeObject(seuRa);
            saida.flush();

            Thread threadLeitora = new Thread(() -> {
                try {
                    String mensagemServidor;
                    while ((mensagemServidor = (String) entrada.readObject()) != null) {
                        System.out.println(mensagemServidor);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            threadLeitora.start();
            
            String linhaEntrada;
            while ((linhaEntrada = leitor.readLine()) != null) {
                saida.writeObject(linhaEntrada + "\n");
                saida.flush();
            }

            entrada.close();
            saida.close();
            leitor.close();
            conexao.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



//------------------------------------------------------------------------------------------

package socket_client;

import java.io.*;
import java.net.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteUnico {
    private static final String ENDERECO_SERVIDOR = "10.130.129.103";
    private static final int PORTA_SERVIDOR = 5000;

    public static void main(String[] args) {
        try (Socket conexao = new Socket(ENDERECO_SERVIDOR, PORTA_SERVIDOR);
             ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
             BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
             ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream())) {

            System.out.println("Conectado ao servidor.");

            System.out.print("Digite seu RA: ");
            String seuRa = leitor.readLine();
            System.out.println("RA: " + seuRa);
            saida.writeObject(seuRa);
            saida.flush();

            new Thread(() -> {
                try {
                    String mensagemServidor;
                    while ((mensagemServidor = (String) entrada.readObject()) != null) {
                        System.out.println(mensagemServidor);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

            String linhaEntrada;
            while ((linhaEntrada = leitor.readLine()) != null) {
                saida.writeObject(linhaEntrada + "\n");
                saida.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//Usei um bloco try-with-resources para gerenciar automaticamente o fechamento dos recursos.
//Removi a declaração throws ClassNotFoundException do método main.
//Mudei o nome da classe para ClienteUnico.
//Mudei a ordem em que os fluxos de entrada e saída são criados.