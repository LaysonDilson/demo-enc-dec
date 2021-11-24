package com.example.demo;

public class EncryData {

    private String strToEncDec;
    private String secret;
    private String vector;
    private String salt;

    public EncryData() {
    }

    public String getStrToEncDec() {
        return strToEncDec;
    }

    public void setStrToEncDec(String strToEncDec) {
        this.strToEncDec = strToEncDec;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
