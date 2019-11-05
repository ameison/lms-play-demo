package models;

import javax.persistence.*;

@Entity
@Table(name = "payment")
public class Payment extends BaseModel{

    public static final int FREE = 0;
    public static final int BASIC = 1;
    public static final int TRAINED = 2;
    public static final int DEDICATED = 3;

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "payment_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long id;

    @Column(name = "plan_id")
    public Long plan_id;

    @Column(name="card_number",length = 20)
    public String cardNumber;

    @Column(name="card_cvc", length = 3)
    public String cardCvc;

    @Column(name = "card_month")
    public Long cardMonth;

    @Column(name = "card_year", length = 4)
    public String cardYear;

    @OneToOne
    public User user;

    public static Integer geyPaymentCode(String type){
        switch (type){
            case "free":
                return Payment.FREE;
            case "basic":
                return Payment.BASIC;
            case "trained":
                return Payment.TRAINED;
            case "dedicated":
                return Payment.DEDICATED;
            default:
                return null;

        }

    }

}
