package br.com.dio.expection;

public class InvestmentNotFoundException extends RuntimeException {
    public InvestmentNotFoundException(String message) {
        super(message);
    }
}
