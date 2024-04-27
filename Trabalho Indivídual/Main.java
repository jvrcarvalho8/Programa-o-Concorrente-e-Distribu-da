import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        Banco banco = new Banco(lock);

        Conta contaSalarioFuncionario1 = new Conta("Funcionario1", 0);
        Conta contaInvestimentoFuncionario1 = new Conta("Funcionario1", 0);
        Conta contaSalarioFuncionario2 = new Conta("Funcionario2", 0);
        Conta contaInvestimentoFuncionario2 = new Conta("Funcionario2", 0);

        Conta contaSalarioFuncionario3 = new Conta("Funcionario3", 0);
        Conta contaInvestimentoFuncionario3 = new Conta("Funcionario3", 0);
        Conta contaSalarioFuncionario4 = new Conta("Funcionario4", 0);
        Conta contaInvestimentoFuncionario4 = new Conta("Funcionario4", 0);

        Conta contaLoja1 = new Conta("Lojaum", 0);
        Conta contaLoja2 = new Conta("Lojadois", 0);

        ImplementaFuncionario funcionario1 = new ImplementaFuncionario(contaInvestimentoFuncionario1, contaSalarioFuncionario1);
        ImplementaFuncionario funcionario2 = new ImplementaFuncionario(contaInvestimentoFuncionario2, contaSalarioFuncionario2);

        ImplementaFuncionario funcionario3 = new ImplementaFuncionario(contaInvestimentoFuncionario3, contaSalarioFuncionario3);
        ImplementaFuncionario funcionario4 = new ImplementaFuncionario(contaInvestimentoFuncionario4, contaSalarioFuncionario4);

        List<Funcionario> funcionariosLoja1 = new ArrayList<>();
        List<Funcionario> funcionariosLoja2 = new ArrayList<>();

        funcionariosLoja1.add(funcionario1);
        funcionariosLoja1.add(funcionario2);
        funcionariosLoja2.add(funcionario3);
        funcionariosLoja2.add(funcionario4);

        Loja loja1 = new Loja(contaLoja1, funcionariosLoja1, banco);
        Loja loja2 = new Loja(contaLoja2, funcionariosLoja2, banco);

        funcionario1.start();
        funcionario2.start();
        funcionario3.start();
        funcionario4.start();

        for (int i = 1; i <= 5; i++) {
            Conta contaCliente = new Conta("Cliente" + i, 1000);
            Cliente cliente = new Cliente(contaCliente, banco, loja1, loja2);
            cliente.start();
        }

        try {
            banco.esperarTransferenciasConcluidas();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loja1.pagarFuncionario();
        loja2.pagarFuncionario();

        try {
            Thread.sleep(20000); // Tempo parar que todas as compras ocorram
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Conta loja UM: " + loja1.getConta().getSaldo());
        System.out.println("Conta loja DOIS: " + loja2.getConta().getSaldo());

        for (int i = 1; i <= 5; i++) {
            Conta contaCliente = new Conta("Cliente" + i, 0);
            System.out.println("Saldo do cliente Cliente" + i + ": " + contaCliente.getSaldo());
        }

        System.out.println("Saldo e investimento funcionários:");
        for (Funcionario funcionario : funcionariosLoja1) {
            System.out.println("Saldo: " + funcionario.getContaSalario().getId() + " na loja 1 :"
                    + funcionario.getContaSalario().getSaldo()
                    + " - Investimento: " + funcionario.getContaInvestimento().getSaldo());
        }
        for (Funcionario funcionario : funcionariosLoja2) {
            System.out.println("Saldo: " + funcionario.getContaSalario().getId() + " na loja 1 :"
                    + funcionario.getContaSalario().getSaldo()
                    + " - Investimento: " + funcionario.getContaInvestimento().getSaldo());
        }
        loja1.deveContinuar = false;
        loja2.deveContinuar = false;
    }
}