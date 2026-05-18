package br.com.dio;

import br.com.dio.expection.AccountNotFoundException;
import br.com.dio.expection.NoFundsEnoughException;
import br.com.dio.expection.WalletNotFundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class Main extends JFrame {

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    public Main() {
        setTitle("DIO Bank - Sistema Visual");
        setSize(450, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela do Mac
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("DIO BANK", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(14, 1, 8, 8));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        JButton btnCreateAccount = new JButton("1 - Criar uma conta");
        JButton btnCreateInvestment = new JButton("2 - Criar um investimento");
        JButton btnCreateWallet = new JButton("3 - Criar carteira de investimento");
        JButton btnDeposit = new JButton("4 - Depositar na conta");
        JButton btnWithdraw = new JButton("5 - Sacar da conta");
        JButton btnTransfer = new JButton("6 - Transferência entre contas");
        JButton btnIncInvest = new JButton("7 - Investir");
        JButton btnRescueInvest = new JButton("8 - Sacar investimento");
        JButton btnListAccounts = new JButton("9 - Listar contas");
        JButton btnListInvestments = new JButton("10 - Listar investimentos");
        JButton btnListWallets = new JButton("11 - Listar carteiras de investimento");
        JButton btnUpdateInvest = new JButton("12 - Atualizar investimentos");
        JButton btnHistory = new JButton("13 - Histórico de contas");
        JButton btnExit = new JButton("14 - Sair do Sistema");

        painelBotoes.add(btnCreateAccount);
        painelBotoes.add(btnCreateInvestment);
        painelBotoes.add(btnCreateWallet);
        painelBotoes.add(btnDeposit);
        painelBotoes.add(btnWithdraw);
        painelBotoes.add(btnTransfer);
        painelBotoes.add(btnIncInvest);
        painelBotoes.add(btnRescueInvest);
        painelBotoes.add(btnListAccounts);
        painelBotoes.add(btnListInvestments);
        painelBotoes.add(btnListWallets);
        painelBotoes.add(btnUpdateInvest);
        painelBotoes.add(btnHistory);
        painelBotoes.add(btnExit);

        add(painelBotoes, BorderLayout.CENTER);

        btnCreateAccount.addActionListener(e -> createAccount());
        btnCreateInvestment.addActionListener(e -> createInvestment());
        btnCreateWallet.addActionListener(e -> createWalletInvestment());
        btnDeposit.addActionListener(e -> deposit());
        btnWithdraw.addActionListener(e -> withdraw());
        btnTransfer.addActionListener(e -> transferToAccount());
        btnIncInvest.addActionListener(e -> incInvestment());
        btnRescueInvest.addActionListener(e -> rescueInvestment());

        btnListAccounts.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("=== CONTAS ===\n");
            accountRepository.list().forEach(account -> sb.append(account).append("\n"));
            showScrollableMessage(sb.toString(), "Lista de Contas");
        });

        btnListInvestments.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("=== INVESTIMENTOS ===\n");
            investmentRepository.list().forEach(invest -> sb.append(invest).append("\n"));
            showScrollableMessage(sb.toString(), "Lista de Investimentos");
        });

        btnListWallets.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("=== CARTEIRAS DE INVESTIMENTO ===\n");
            investmentRepository.listwallets().forEach(w -> sb.append(w).append("\n"));
            showScrollableMessage(sb.toString(), "Lista de Carteiras");
        });

        btnUpdateInvest.addActionListener(e -> {
            investmentRepository.updateAmount();
            JOptionPane.showMessageDialog(this, "Investimentos reajustados com sucesso!");
        });

        btnHistory.addActionListener(e -> checkHistory());
        btnExit.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Main tela = new Main();
                tela.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void createAccount() {
        String pixInput = JOptionPane.showInputDialog(this, "Informe as chaves pix (separadas por ';'):");
        if (pixInput == null) return;

        var pix = Arrays.stream(pixInput.split(";")).toList();

        String amountInput = JOptionPane.showInputDialog(this, "Informe o valor inicial de depósito:");
        if (amountInput == null) return;

        try {
            var amount = Long.parseLong(amountInput);
            var wallet = accountRepository.create(pix, amount);
            JOptionPane.showMessageDialog(this, "Conta criada com sucesso:\n" + wallet);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createInvestment() {
        String taxInput = JOptionPane.showInputDialog(this, "Informe a taxa do investimento:");
        if (taxInput == null) return;

        String fundsInput = JOptionPane.showInputDialog(this, "Informe o valor inicial de depósito:");
        if (fundsInput == null) return;

        try {
            var tax = Integer.parseInt(taxInput);
            var initialFunds = Long.parseLong(fundsInput);
            var investment = investmentRepository.create(tax, initialFunds);
            JOptionPane.showMessageDialog(this, "Investimento criado:\n" + investment);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dados numéricos inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdraw() {
        String pix = JOptionPane.showInputDialog(this, "Informe a chave pix da conta para saque:");
        if (pix == null) return;

        String amountInput = JOptionPane.showInputDialog(this, "Informe o valor que será sacado:");
        if (amountInput == null) return;

        try {
            var amount = Long.parseLong(amountInput);
            accountRepository.withdraw(pix, amount);
            JOptionPane.showMessageDialog(this, "Saque de R$ " + amount + " realizado com sucesso!");
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro no Saque", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deposit() {
        String pix = JOptionPane.showInputDialog(this, "Informe a chave pix da conta para depósito:");
        if (pix == null) return;

        String amountInput = JOptionPane.showInputDialog(this, "Informe o valor que será depositado:");
        if (amountInput == null) return;

        try {
            var amount = Long.parseLong(amountInput);
            accountRepository.deposit(pix, amount);
            JOptionPane.showMessageDialog(this, "Depósito de R$ " + amount + " efetuado!");
        } catch (AccountNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro no Depósito", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void transferToAccount() {
        String source = JOptionPane.showInputDialog(this, "Informe a chave pix da conta de origem:");
        if (source == null) return;

        String target = JOptionPane.showInputDialog(this, "Informe a chave pix da conta de destino:");
        if (target == null) return;

        String amountInput = JOptionPane.showInputDialog(this, "Informe o valor da transferência:");
        if (amountInput == null) return;

        try {
            var amount = Long.parseLong(amountInput);
            accountRepository.transferMoney(source, target, amount);
            JOptionPane.showMessageDialog(this, "Transferência de R$ " + amount + " realizada para " + target);
        } catch (AccountNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na Transferência", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createWalletInvestment() {
        String pix = JOptionPane.showInputDialog(this, "Informe a chave pix da conta:");
        if (pix == null) return;

        String investIdInput = JOptionPane.showInputDialog(this, "Informe o identificador do investimento:");
        if (investIdInput == null) return;

        try {
            var account = accountRepository.findByPix(pix);
            var investmentId = Integer.parseInt(investIdInput);
            var investmentWallet = investmentRepository.initInvestment(account, investmentId);
            JOptionPane.showMessageDialog(this, "Conta de investimento criada:\n" + investmentWallet);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void incInvestment() {
        String pix = JOptionPane.showInputDialog(this, "Informe a chave pix da conta para investimento:");
        if (pix == null) return;

        String amountInput = JOptionPane.showInputDialog(this, "Informe o valor que será investido:");
        if (amountInput == null) return;

        try {
            var amount = Long.parseLong(amountInput);
            investmentRepository.deposit(pix, amount);
            JOptionPane.showMessageDialog(this, "Investimento de R$ " + amount + " aplicado!");
        } catch (WalletNotFundException | AccountNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Investir", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rescueInvestment() {
        String pix = JOptionPane.showInputDialog(this, "Informe a chave pix da conta para resgate:");
        if (pix == null) return;

        String amountInput = JOptionPane.showInputDialog(this, "Informe o valor do resgate:");
        if (amountInput == null) return;

        try {
            var amount = Long.parseLong(amountInput);
            investmentRepository.withdraw(pix, amount);
            JOptionPane.showMessageDialog(this, "Resgate de R$ " + amount + " efetuado!");
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro no Resgate", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkHistory() {
        String pix = JOptionPane.showInputDialog(this, "Informe a chave pix para verificar o extrato:");
        if (pix == null) return;

        try {
            var sortedHistory = accountRepository.getHistory(pix);
            StringBuilder sb = new StringBuilder("=== EXTRATO HISTÓRICO ===\n\n");

            sortedHistory.forEach((k, v) -> {
                sb.append("Data/Hora: ").append(k.format(ISO_DATE_TIME)).append("\n");
                sb.append("ID Transação: ").append(v.getFirst().transactionId()).append("\n");
                sb.append("Descrição: ").append(v.getFirst().description()).append("\n");
                sb.append("Valor: R$ ").append(v.size() / 100).append(",").append(String.format("%02d", v.size() % 100)).append("\n");
                sb.append("----------------------------------------\n");
            });

            showScrollableMessage(sb.toString(), "Extrato da Conta");
        } catch (AccountNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showScrollableMessage(String text, String title) {
        JTextArea textArea = new JTextArea(15, 30);
        textArea.setText(text);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
}