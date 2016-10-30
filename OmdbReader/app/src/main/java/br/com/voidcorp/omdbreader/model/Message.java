package br.com.voidcorp.omdbreader.model;

public class Message implements Content {

    private String mMessage;

    public Message(final String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    @Override
    public int getType() {
        return ContentType.MESSAGE;
    }
}
