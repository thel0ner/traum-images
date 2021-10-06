package metaroa.traumimages.Paypal;

import lombok.Data;
import lombok.NoArgsConstructor;
import metaroa.traumimages.User.User;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class PaypalOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String approvedOrderId;

    @Column(nullable = false)
    private Date creationTime;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String href;

    @Column(nullable = false)
    private boolean isCompleted;

    @Column(nullable = false)
    private PaypalPaymentStatus status;

    @Column(nullable = false)
    private String paypalPaymentId;

    @Column(nullable = false)
    private Long planId;

    public String getPaypalPaymentId() {
        return paypalPaymentId;
    }

    public Long getPrice() {
        return price;
    }

    public boolean getIsCompleted(){
        return isCompleted;
    }

    public PaypalPaymentStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public Long getPlanId() {
        return planId;
    }

    public String getHref() {
        return href;
    }

    public String getApprovedOrderId() {
        return approvedOrderId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }

    public void setApprovedOrderId(String approvedOrderId) {
        this.approvedOrderId = approvedOrderId;
    }

    public void setPaypalPaymentId(String paypalPaymentId) {
        this.paypalPaymentId = paypalPaymentId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public void setStatus(PaypalPaymentStatus status) {
        this.status = status;
    }
}
