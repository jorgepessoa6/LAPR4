package eapli.base.notificationmanagement.domain;

import eapli.base.messagemanagement.domain.Message;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Jorge Pessoa (1180761)
 */

@XmlRootElement
@Entity
public class Notification implements AggregateRoot<Error>{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk;

    @Version
    private Long version;

    @OneToOne(cascade = CascadeType.ALL)
    private Message message;

    @Embedded
    private Error error;

    private NotificationStatus notificationStatus;

    @ManyToOne
    private ProductionLine productionLine;

    public Notification(Message message, Error error, ProductionLine productionLine) {
        this.message = message;
        this.error = error;
        this.productionLine = productionLine;
        this.notificationStatus = NotificationStatus.POR_TRATAR;
    }

    public Notification(){
        //ORM
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Message getMessage() {
        return message;
    }

    public Error getError() {
        return error;
    }

    public ProductionLine getProductionLine() {
        return productionLine;
    }

    @Override
    public boolean sameAs(Object o) {
        if (!this.equals(o)) {
            return false;
        }
        final Notification other = (Notification) o;
        return message.equals(other.message) && error.equals(other.error);
    }

    @Override
    public Error identity() {
        return error;
    }
}

