package metaroa.traumimages.dto;

import metaroa.traumimages.Paypal.PaypalPaymentStatus;

public class PaypalResponseDTO {
    private String id;
    private Long amount;
    private String currencyCode;
    private PaypalPaymentStatus status;

    public Long getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getId() {
        return id;
    }

    public PaypalPaymentStatus getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setStatus(PaypalPaymentStatus status) {
        this.status = status;
    }
}
