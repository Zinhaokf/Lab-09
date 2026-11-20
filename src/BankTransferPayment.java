public class BankTransferPayment extends PaymentProcessor {
    private final String account;

    public BankTransferPayment(String account) {
        this.account = account;
    }

    @Override
    protected boolean validatePaymentInfo() {
        return account != null && account.matches("\\d{8,}");
    }

    @Override
    protected double calculateFee(double amount) {
        return 10000;
    }

    @Override
    protected boolean executePayment(double total) {
        System.out.println("  [bank] transferring " + total);
        return true;
    }
}