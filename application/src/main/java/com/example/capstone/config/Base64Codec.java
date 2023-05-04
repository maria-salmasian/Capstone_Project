package com.example.capstone.config;

import io.jsonwebtoken.impl.AbstractTextCodec;

import javax.xml.bind.DatatypeConverter;

public class Base64Codec extends AbstractTextCodec {
    public Base64Codec() {
    }

    public String encode(byte[] data) {
        return DatatypeConverter.printBase64Binary(data);
    }

    public byte[] decode(String encoded) {
        return DatatypeConverter.parseBase64Binary(encoded);
    }
}
