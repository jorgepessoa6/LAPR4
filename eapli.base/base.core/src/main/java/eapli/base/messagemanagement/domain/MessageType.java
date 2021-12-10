package eapli.base.messagemanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class MessageType implements ValueObject {

    private Type type;

    public MessageType() {
        this.type = null;
    }

    public MessageType(Type t) {
        this.type = t;
    }

    public void updateType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
