package com.nkraft.eyebox.exceptions;

import java.io.IOException;

public class NetworkException extends IOException {
    public NetworkException(String message) {
        super(message);
    }
}
