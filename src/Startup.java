public class Startup {
    private String nome;
    private String slogan;
    private int ano;

    private int pontuacao = 70;

    private int pitchs, bugs, tracoes, investidoresIrritados, fakeNews;

    public Startup(String nome, String slogan, int ano) {
        this.nome = nome;
        this.slogan = slogan;
        this.ano = ano;
    }

    public void aplicarEvento(String evento) {
        switch (evento) {
            case "pitch" -> {
                pontuacao += 6;
                pitchs++;
            }
            case "bugs" -> {
                pontuacao -= 4;
                bugs++;
            }
            case "tracao" -> {
                pontuacao += 3;
                tracoes++;
            }
            case "investidor" -> {
                pontuacao -= 6;
                investidoresIrritados++;
            }
            case "fakenews" -> {
                pontuacao -= 8;
                fakeNews++;
            }
        }
    }

    public void ganharBatalha() {
        pontuacao += 30;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getNome() {
        return nome;
    }

    public String getSlogan() {
        return slogan;
    }

    public int getPitchs() { return pitchs; }
    public int getBugs() { return bugs; }
    public int getTracoes() { return tracoes; }
    public int getInvestidoresIrritados() { return investidoresIrritados; }
    public int getFakeNews() { return fakeNews; }


    public String getEstatisticas() {
        return String.format("Pitchs: %d | Bugs: %d | Trações: %d | Investidores Irritados: %d | Fake News: %d",
                pitchs, bugs, tracoes, investidoresIrritados, fakeNews);
    }

    @Override
    public String toString() {
        return String.format("%s - Pontuação: %d - %s", nome, pontuacao, getEstatisticas());
    }
}
