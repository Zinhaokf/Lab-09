public class CreditCardPayment extends PaymentProcessor {
    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    protected boolean validatePaymentInfo() {
        return cardNumber != null && cardNumber.matches("\\d{16}");
    }

    @Override
    protected double calculateFee(double amount) {
        return amount * 0.02;
    }

    @Override
    protected boolean executePayment(double total) {
        System.out.println("  [card] charging " + total);
        return true;
    }
}