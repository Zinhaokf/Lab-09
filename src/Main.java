// Main: chạy demo, sau đó 6 ca kiểm thử cho template method thanh toán.
public class Main {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        System.out.println("=== DEMO ===");

        // Thẻ tín dụng hợp lệ: thanh toán thành công
        System.out.println(new CreditCardPayment("1234567812345678")
                .process(new Order("DH001", 100000)));

        // Ví thiếu số dư: thất bại ở bước thanh toán
        System.out.println(new EWalletPayment("0912345678", 50000)
                .process(new Order("DH002", 100000)));

        System.out.println("\n=== TESTS ===");

        // Test 1: thẻ 16 số hợp lệ, thanh toán thành công với phí 2%
        check("Card valid",
                "OK: paid 102000.00",
                new CreditCardPayment("1234567812345678").process(new Order("DH003", 100000)));

        // Test 2: thẻ chỉ có 15 số, bị từ chối ở bước kiểm tra thông tin thanh toán
        check("Card invalid (15 digits)",
                "FAIL: invalid payment info",
                new CreditCardPayment("123456781234567").process(new Order("DH004", 100000)));

        // Test 3: số dư ví không đủ, bị từ chối ở bước thanh toán
        check("E-wallet insufficient balance",
                "FAIL: payment failed",
                new EWalletPayment("0912345678", 50000).process(new Order("DH005", 100000)));

        // Test 4: số tài khoản có chữ cái, bị từ chối ở bước kiểm tra thông tin
        check("Bank wrong account",
                "FAIL: invalid payment info",
                new BankTransferPayment("ABC123").process(new Order("DH006", 100000)));

        // Test 5: tài khoản hợp lệ, thanh toán thành công với phí cố định 10000
        check("Bank valid",
                "OK: paid 110000.00",
                new BankTransferPayment("12345678").process(new Order("DH007", 100000)));

        // Test 6: đơn có số tiền 0, bị từ chối ở bước kiểm tra đơn hàng chung
        check("Invalid order (amount 0)",
                "FAIL: invalid order",
                new CreditCardPayment("1234567812345678").process(new Order("DH008", 0)));

        System.out.println("\nTotal: " + passed + " PASS, " + failed + " FAIL");
    }

    // So sánh kết quả thực tế với kết quả mong đợi, in PASS hoặc FAIL
    private static void check(String name, String expected, String actual) {
        if (expected.equals(actual)) {
            passed++;
            System.out.println("PASS - " + name);
        } else {
            failed++;
            System.out.println("FAIL - " + name
                    + " | expected: " + expected
                    + " | actual: " + actual);
        }
    }
}