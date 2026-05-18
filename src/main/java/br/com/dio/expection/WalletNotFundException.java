package br.com.dio.expection;

public class WalletNotFundException extends RuntimeException {
    public WalletNotFundException(String message) {
        super(message);
    }
}
