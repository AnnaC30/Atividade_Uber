import java.time.LocalDate;

public class Viagem {
    String nomeMotorista;
    LocalDate data;
    double valor;
    int quilometragem;

    public Viagem(String nomeMotorista, LocalDate data, double valor, int quilometragem) {
        this.nomeMotorista = nomeMotorista;
        this.data = data;
        this.valor = valor;
        this.quilometragem = quilometragem;
    }

    @Override
    public String toString() {
        return nomeMotorista + " | " + data + " | R$" + valor + " | " + quilometragem + "km";
    }

    public String formatarParaArquivo() {
        return nomeMotorista + ";" + data + ";" + valor + ";" + quilometragem;
    }

    public static Viagem fromString(String linha) {
        String[] partes = linha.split(";");
        return new Viagem(
            partes[0],
            LocalDate.parse(partes[1]),
            Double.parseDouble(partes[2]),
            Integer.parseInt(partes[3])
        );
    }
}
