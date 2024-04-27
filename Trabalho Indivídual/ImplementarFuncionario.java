public class ImplementaFuncionario extends Thread implements Funcionario {
    private Conta contaSalario;
    private Conta contaInvestimento;

    public ImplementaFuncionario(Conta contaInvestimento, Conta contaSalario) {
        this.contaInvestimento = contaInvestimento;
        this.contaSalario = contaSalario;
        
    }

    @Override
    public void receberSalario(double valor) {
        contaSalario.depositar(valor);
    }

    @Override
    public void investir(double valor) {
        contaInvestimento.depositar(valor);
    }

    @Override
    public double calcularSalario() {
        return 1400.0;
    }


    public Conta getContaInvestimento() {
        return contaInvestimento;
    }


    public Conta getContaSalario() {
        return contaSalario;
    }

}