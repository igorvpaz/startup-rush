import java.util.*;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static List<Startup> startups = new ArrayList<>();
    private static List<Batalha> batalhas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);


    private static void cadastrarStartup() {
        if (startups.size() >= 8) {
            System.out.println("Limite de startups atingido.");
            return;
        }

        System.out.print("Nome da startup: ");
        String nome = scanner.nextLine();
        System.out.print("Slogan: ");
        String slogan = scanner.nextLine();
        System.out.print("Ano de fundação: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        startups.add(new Startup(nome, slogan, ano));
        System.out.println("Startup cadastrada com sucesso!");

        if (startups.size() >= 4 && startups.size() % 2 == 0) {
            System.out.println("Você pode iniciar o torneio quando quiser.");
        }
    }

    private static void iniciarTorneio() {
        if (batalhas.isEmpty()) {
            if (startups.size() < 4 || startups.size() > 8 || startups.size() % 2 != 0) {
                System.out.println("Você precisa de 4 a 8 startups e em número par para iniciar o torneio.");
                return;
            }
        } else {
            if (startups.size() < 2 || startups.size() % 2 != 0) {
                System.out.println("Número inválido de startups para nova rodada.");
                return;
            }
        }

        Collections.shuffle(startups);
        batalhas.clear();

        for (int i = 0; i < startups.size(); i += 2) {
            batalhas.add(new Batalha(startups.get(i), startups.get(i + 1)));
        }

        System.out.println("Rodada iniciada com " + batalhas.size() + " batalha(s).");
    }



    private static void administrarBatalha() {
        List<Batalha> pendentes = batalhas.stream()
                .filter(b -> !b.isFinalizada())
                .collect(Collectors.toList());

        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma batalha pendente.");
            return;
        }

        System.out.println("Batalhas pendentes:");
        for (int i = 0; i < pendentes.size(); i++) {
            System.out.println((i + 1) + ". " + pendentes.get(i));
        }

        System.out.print("Escolha uma batalha para administrar: ");
        int escolha = scanner.nextInt() - 1;
        scanner.nextLine();

        if (escolha < 0 || escolha >= pendentes.size()) {
            System.out.println("Escolha inválida.");
            return;
        }

        Batalha batalha = pendentes.get(escolha);
        batalha.aplicarEventosManualmente(scanner);

        boolean todasFinalizadas = batalhas.stream().allMatch(Batalha::isFinalizada);

        if (todasFinalizadas) {
            startups = new ArrayList<>(batalhas.stream()
                    .map(b -> {
                        Startup s1 = b.getS1();
                        Startup s2 = b.getS2();
                        return s1.getPontuacao() > s2.getPontuacao() ? s1 : s2;
                    })
                    .collect(Collectors.toList())
            );

            if (startups.size() > 1) {
                System.out.println("🔁 Avançando para a próxima rodada...");
                iniciarTorneio();
            } else {
                System.out.println("🏁 Torneio encerrado! Veja o relatório para o resultado final.");
            }
        }
    }


    private static void mostrarRelatorio() {
        System.out.println("\n📊 RELATÓRIO GERAL:");

        startups.sort((a, b) -> b.getPontuacao() - a.getPontuacao());

        for (Startup s : startups) {
            System.out.println(s);
        }

        if (startups.size() == 1) {
            System.out.println("🏆 Campeã: " + startups.get(0).getNome());
            System.out.println("Slogan: " + startups.get(0).getSlogan());
        }

        //funcionalidade extra
        System.out.println("\n🏅 Premiações Especiais 🏅");

        Startup maisPitchs = Collections.max(startups, Comparator.comparingInt(Startup::getPitchs));
        Startup maisBugs = Collections.max(startups, Comparator.comparingInt(Startup::getBugs));
        Startup maisFakeNews = Collections.max(startups, Comparator.comparingInt(Startup::getFakeNews));
        Startup maisTracoes = Collections.max(startups, Comparator.comparingInt(Startup::getTracoes));
        Startup maisOdiada = Collections.max(startups, Comparator.comparingInt(Startup::getInvestidoresIrritados));

        System.out.println("🗣 Startup Mais Convincente: " + maisPitchs.getNome() + " (" + maisPitchs.getPitchs() + " pitchs)");
        System.out.println("🐞 Startup Mais Bugada: " + maisBugs.getNome() + " (" + maisBugs.getBugs() + " bugs)");
        System.out.println("🔥 Startup Mais Polêmica: " + maisFakeNews.getNome() + " (" + maisFakeNews.getFakeNews() + " fake news)");
        System.out.println("📈 Maior Tração: " + maisTracoes.getNome() + " (" + maisTracoes.getTracoes() + " trações)");
        System.out.println("😡 Mais Odiada por Investidores: " + maisOdiada.getNome() + " (" + maisOdiada.getInvestidoresIrritados() + " invest. irritados)");

    }




    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n🚀 STARTUP RUSH 🚀");
            System.out.println("1. Cadastrar startup");
            System.out.println("2. Iniciar torneio (sortear batalhas)");
            System.out.println("3. Administrar batalha");
            System.out.println("4. Ver relatório geral");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarStartup();
                case 2 -> iniciarTorneio();
                case 3 -> administrarBatalha();
                case 4 -> mostrarRelatorio();
                case 5 -> System.out.println("Finalizando programa...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 5);
    }
}
