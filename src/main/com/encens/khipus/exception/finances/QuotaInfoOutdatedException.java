package com.encens.khipus.exception.finances;

/**
 * This exception is thrown when the quota info has changed,
 * that implies that the rotatory fund collection registries
 * generated by the payroll generation are invalid or outdated
 *
 * @author
 * @version 2.23
 */

public class QuotaInfoOutdatedException extends Exception {
    public QuotaInfoOutdatedException() {
    }

    public QuotaInfoOutdatedException(String message) {
        super(message);
    }

    public QuotaInfoOutdatedException(Throwable cause) {
        super(cause);
    }
}