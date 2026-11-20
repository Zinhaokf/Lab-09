public class EWalletPayment extends PaymentProcessor {
    private final String phone;
    private double balance;

    public EWalletPayment(String phone, double balance) {
        this.phone = phone;
        this.balance = balance;
    }

    @Override
    protected boolean validatePaymentInfo() {
        return phone != null && phone.matches("\\d{10}");
    }

    @Override
    protected double calculateFee(double amount) {
        return 0;
    }

    @Override
    protected boolean executePayment(double total) {
        if (total > balance) {
            return false;
        }
        balance -= total;
        return true;
    }
}