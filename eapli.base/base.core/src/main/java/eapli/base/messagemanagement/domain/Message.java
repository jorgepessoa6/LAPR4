package eapli.base.messagemanagement.domain;

import eapli.base.machinemanagement.domain.Machine;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Message implements AggregateRoot<Calendar> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk;

    @Version
    private Long version;

    private Calendar messageDateCreation;
    @Embedded
    private MessageType messageStatus;

    @ManyToOne
    private Machine mach;

    private String desc;

    private MessageStatus messageState;

    public Message(Machine m, Calendar c, Type t,String s) {
        this.mach = m;
        this.messageDateCreation=c;
        this.messageStatus=new MessageType(t);
        this.desc=s;
        this.messageState = MessageStatus.POR_PROCESSAR;
    }

    protected Message() {
        //for ORM
    }

    public Calendar getMessageDateCreation() {
        return messageDateCreation;
    }

    public MessageType getMessage() {
        return messageStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Machine getMach() {
        return mach;
    }

    public MessageStatus getMessageState() {
        return messageState;
    }

    public void setMessageState(MessageStatus messageState) {
        this.messageState = messageState;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Message)) {
            return false;
        }

        final Message that = (Message) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public Calendar identity() {
        return this.messageDateCreation;
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }
}
