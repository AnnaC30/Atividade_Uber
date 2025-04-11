import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Uber {

    static final String ARQUIVO = "viagens.txt";

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Viagem> viagens = carregarViagens();

        try (Scanner input = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n--- MENU ---");
                System.out.println("1. Adicionar viagem");
                System.out.println("2. Exibir todas as viagens");
                System.out.println("3. Consultar viagens de um motorista");
                System.out.println("0. Sair");
                System.out.print("Escolha: ");
                int opcao = Integer.parseInt(input.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.print("Nome do motorista: ");
                        String nome = input.nextLine();

                        System.out.print("Data (yyyy-MM-dd): ");
                        String dataStr = input.nextLine();
                        LocalDate data = LocalDate.parse(dataStr, formatter);

                        System.out.print("Valor da viagem: ");
                        double valor = Double.parseDouble(input.nextLine());

                        System.out.print("Quilometragem: ");
                        int km = Integer.parseInt(input.nextLine());

                        viagens.add(new Viagem(nome, data, valor, km));
                        System.out.println("Viagem adicionada!");
                        break;

                    case 2:
                        System.out.println("\n--- Todas as viagens ---");
                        for (Viagem v : viagens) {
                            System.out.println(v);
                        }
                        break;

                    case 3:
                        System.out.print("Nome do motorista: ");
                        String nomeFiltro = input.nextLine();
                        System.out.println("\n--- Submenu ---");
                        System.out.println("1. Exibir todas as viagens");
                        System.out.println("2. Exibir valor total das viagens");
                        System.out.println("3. Exibir valor total por data");
                        System.out.print("Escolha: ");
                        int subopcao = Integer.parseInt(input.nextLine());

                        if (subopcao == 1) {
                            for (Viagem v : viagens) {
                                if (v.nomeMotorista.equalsIgnoreCase(nomeFiltro)) {
                                    System.out.println(v);
                                }
                            }
                        } else if (subopcao == 2) {
                            double total = 0;
                            for (Viagem v : viagens) {
                                if (v.nomeMotorista.equalsIgnoreCase(nomeFiltro)) {
                                    total += v.valor;
                                }
                            }
                            System.out.println("Valor total das viagens: R$" + total);
                        } else if (subopcao == 3) {
                            System.out.print("Digite a data (yyyy-MM-dd): ");
                            String dataConsulta = input.nextLine();
                            LocalDate dataBusca = LocalDate.parse(dataConsulta, formatter);
                            double total = 0;
                            for (Viagem v : viagens) {
                                if (v.nomeMotorista.equalsIgnoreCase(nomeFiltro) && v.data.equals(dataBusca)) {
                                    total += v.valor;
                                }
                            }
                            System.out.println("Valor total em " + dataBusca + ": R$" + total);
                        } else {
                            System.out.println("Opção inválida!");
                        }
                        break;

                    case 0:
                        salvarViagens(viagens);
                        System.out.println("Saindo...");
                        return;

                    default:
                        System.out.println("Opção inválida!");
                }
            }
        }
    }

    public static List<Viagem> carregarViagens() {
        List<Viagem> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (arquivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    lista.add(Viagem.fromString(linha));
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler arquivo: " + e.getMessage());
            }
        }

        return lista;
    }

    public static void salvarViagens(List<Viagem> viagens) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Viagem v : viagens) {
                pw.println(v.formatarParaArquivo());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}
