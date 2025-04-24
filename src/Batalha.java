import java.util.*;

public class Batalha {
    private Startup s1, s2;
    private boolean finalizada = false;

    public Batalha(Startup s1, Startup s2) {
        this.s1 = s1;
        this.s2 = s2;
    }


    public boolean isFinalizada() {
        return finalizada;
    }

    public Startup getS1() {
        return s1;
    }

    public Startup getS2() {
        return s2;
    }

    public void aplicarEventosManualmente(Scanner scanner) {
        System.out.println("Registrando eventos para:");
        aplicarEventosPara(s1, scanner);
        aplicarEventosPara(s2, scanner);

        int pontos1 = s1.getPontuacao();
        int pontos2 = s2.getPontuacao();

        System.out.println("Resultado parcial:");
        System.out.println(s1.getNome() + ": " + pontos1);
        System.out.println(s2.getNome() + ": " + pontos2);

        // desempate
        if (pontos1 == pontos2) {
            System.out.println("Empate! Iniciando Shark Fight...");
            if (new Random().nextBoolean()) {
                s1.aplicarEvento("pitch");
                s1.aplicarEvento("tracao");
                s1.aplicarEvento("tracao");
                s1.aplicarEvento("investidor");
                s1.ganharBatalha();
                System.out.println(s1.getNome() + " venceu na Shark Fight!");
            } else {
                s2.aplicarEvento("pitch");
                s2.ganharBatalha();
                System.out.println(s2.getNome() + " venceu na Shark Fight!");
            }
        } else {
            Startup vencedora = (pontos1 > pontos2) ? s1 : s2;
            vencedora.ganharBatalha();
            System.out.println("Vencedora: " + vencedora.getNome());
        }

        finalizada = true;
    }

    private void aplicarEventosPara(Startup s, Scanner scanner) {
        System.out.println("Startup: " + s.getNome());
        List<String> eventos = List.of("pitch", "bugs", "tracao", "investidor", "fakenews");
        for (String evento : eventos) {
            System.out.print("Aconteceu evento '" + evento + "' com essa startup? (s/n): ");
            String resposta = scanner.nextLine().toLowerCase();
            if (resposta.equals("s")) {
                s.aplicarEvento(evento);
            }
        }
    }

    @Override
    public String toString() {
        return s1.getNome() + " vs " + s2.getNome() + (finalizada ? " ✅" : " ❌");
    }
}
