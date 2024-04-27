public interface Funcionario {
    void run();

    void receberSalario(double valor);

    void investir(double valor);

    double calcularSalario();

    Conta getContaInvestimento();
    
    Conta getContaSalario();

    

}