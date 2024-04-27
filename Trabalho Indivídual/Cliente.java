public class Cliente extends Thread {
    private Conta conta;
    private Banco banco;
    private Loja lojaum;
    private Loja lojadois;

    public Cliente(Conta conta, Banco banco, Loja lojaum, Loja lojadois) {
        this.conta = conta;
        this.banco = banco;
        this.loja1 = lojaum;
        this.loja2 = lojadois;
    }

    @Override
    public void run() {
        while (conta.getSaldo() > 0) {
            int valorCompra = (conta.getSaldo() >= 200) ? ((Math.random() < 0.5) ? 100 : 200) : 100;
            if (valorCompra <= conta.getSaldo()) {
                Loja loja = (Math.random() < 0.5) ? lojaum : lojadois;
                banco.transferencia(conta, loja.getConta(), valorCompra);
                esperar();
            } else {
                break;
            }
        }
        System.out.println(conta.getId() + " você está com saldo insuficiente");
    }

    private void esperar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}