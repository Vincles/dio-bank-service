# 🏦 DIO Bank - Sistema Bancário com Interface Gráfica

<img width="458" height="654" alt="image" src="https://github.com/user-attachments/assets/f1b2d0d0-349e-46dd-b65d-e1cc6d959ebd" />


Este projeto foi desenvolvido para o desafio de programação orientada a objetos (POO) do Bootcamp da DIO. O sistema simula as operações de um banco digital, incluindo contas e investimentos.

Originalmente projetado para o terminal, o projeto foi evoluído com o auxilo de IA para conter uma **interface gráfica** utilizando a biblioteca Swing do Java, adaptada especialmente para rodar nativamente em ambientes macOS e Windows.

---

## 🚀 Funcionalidades

O sistema conta com 13 operações divididas em 3 pilares principais:

### 👤 Gerenciamento de Contas
* **Criar Conta:** Registro de contas associando múltiplas chaves Pix e um depósito inicial.
* **Depositar / Sacar:** Operações financeiras básicas com validação de saldo.
* **Transferência:** Envio de valores entre contas distintas utilizando chaves Pix.
* **Histórico da Conta:** Exibição detalhada e formatada do extrato de transações.

### 📈 Sistema de Investimentos
* **Criar Investimento:** Configuração de novos fundos com taxas específicas.
* **Criar Carteira:** Vincula uma conta ativa a um produto de investimento.
* **Aplicar / Resgatar:** Movimentação de capital para a carteira de investimentos.
* **Atualizar Rendimentos:** Atualização em lote dos saldos com base nas taxas configuradas.

### 📋 Listagens Gerais
* Listagem completa de contas, investimentos disponíveis e carteiras ativas exibidas em caixas de texto roláveis (`JScrollPane`).

---

## 🛠️ Tecnologias e Conceitos Utilizados

* **Linguagem:** Java 21 (Amazon Corretto)
* **Automação de Build:** Gradle
* **Interface Gráfica (GUI):** Java Swing & `JOptionPane`
* **Paradigma Principal:** Programação Orientada a Objetos (Encapsulamento, Polimorfismo e Abstração)
* **Tratamento de Erros:** Exceções customizadas (`AccountNotFoundException`, `NoFundsEnoughException`, `WalletNotFundException`)
* **Padrão de Arquitetura:** Repository Pattern para simulação de banco de dados em memória.

---

## 💻 Como Rodar o Projeto (No Mac/Windows)

1. Clone este repositório:
   ```bash
   git clone (https://github.com/Vincles/dio-bank-service.git)
