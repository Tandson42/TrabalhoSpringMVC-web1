package com.example.trabalho03.DAO;

public class ErroDao extends Exception {
    public ErroDao() {
        this("Erro Dao. ");
    }

    public ErroDao(String message) {
        super("Erro Dao:" + message);
    }

    public ErroDao(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroDao(Throwable cause) {
        super(cause);
    }

    public ErroDao(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
