import entity.Eventos;

import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        int opcao = 0;

        while(opcao!=3){
            System.out.println("----- MENU -----");
            System.out.println("1 - Criar Evento\n" +
                               "2 - Acompanhar Evento\n" +
                               "3 - Sair");

            opcao = entrada.nextInt();

            switch (opcao){
                case 1:
                    criarEvento();
                    break;
                case 2:
                    //acompanharEvento();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção Inválida!");
            }
        }
    }

    public static void criarEvento(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Digite o seu CPF: ");
        String cpf = entrada.nextLine();

        System.out.println("Forneça o tipo do evento:");
        String tipo = entrada.nextLine();

        System.out.println("Digite a data do evento (aaaa-mm-dd):");
        String data = entrada.nextLine();

        System.out.println("Digite o endereço do local do evento:");
        String endereco = entrada.nextLine();

        System.out.println("Forneça a descrição do evento: ");
        String descricao = entrada.nextLine();

        System.out.println("Informe a quantidade de pessoas: ");
        int quantidadePessoas = entrada.nextInt();

        Eventos evento = new EventoBuilder()
                .setSolicitante(cpf)
                .setTipo(tipo)
                .setData(Date.valueOf(data))
                .setEndereco(endereco)
                .setDescricao(descricao)
                .setQtdPessoas(quantidadePessoas)
                .builder();

        DAO.salvarDados(evento);
    }
}
