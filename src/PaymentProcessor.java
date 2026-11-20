import java.util.Locale;

/**
 * Lớp trừu tượng định nghĩa khung thuật toán xử lý thanh toán (mẫu Template Method).
 *
 * Quy trình gồm 6 bước, được cố định trong phương thức process():
 *  - Bước chung (lớp cha tự làm, lớp con không đổi được): kiểm tra đơn hàng,
 *    ghi nhận giao dịch, gửi biên lai.
 *  - Bước thay đổi (abstract, mỗi lớp con tự cài đặt): kiểm tra thông tin thanh toán,
 *    tính phí, thực hiện thanh toán.
 *  - Hook (có cài đặt mặc định, lớp con có thể ghi đè): needSendReceipt().
 */
public abstract class PaymentProcessor {

    // Template method: khung thuật toán, final để không ai đổi thứ tự bước
    public final String process(Order order) {
        if (!validateOrder(order)) {                                        // bước chung
            return "FAIL: invalid order";
        }
        if (!validatePaymentInfo()) {                                       // bước thay đổi
            return "FAIL: invalid payment info";
        }
        double total = order.getAmount() + calculateFee(order.getAmount()); // bước thay đổi
        if (!executePayment(total)) {                                       // bước thay đổi
            return "FAIL: payment failed";
        }
        recordTransaction(order, total);                                    // bước chung
        if (needSendReceipt()) {                                            // hook
            sendReceipt(order, total);
        }
        return "OK: paid " + String.format(Locale.US, "%.2f", total);
    }

    // ----- Bước chung: dùng chung cho mọi lớp con, để private nên không ghi đè được -----

    // Kiểm tra đơn hàng: có mã đơn và số tiền lớn hơn 0
    private boolean validateOrder(Order order) {
        return order != null
                && order.getId() != null
                && !order.getId().isBlank()
                && order.getAmount() > 0;
    }

    // Ghi nhận giao dịch (giả lập bằng cách in ra màn hình)
    private void recordTransaction(Order order, double total) {
        System.out.println("  [record] order " + order.getId() + ", total " + total);
    }

    // Gửi biên lai (giả lập bằng cách in ra màn hình)
    private void sendReceipt(Order order, double total) {
        System.out.println("  [receipt] sent for order " + order.getId());
    }

    // ----- Hook: mặc định luôn gửi biên lai; hiện chưa có lớp con nào ghi đè -----
    protected boolean needSendReceipt() {
        return true;
    }

    // ----- Bước thay đổi: lớp con bắt buộc phải cài đặt -----

    // Kiểm tra thông tin thanh toán (số thẻ, số điện thoại, số tài khoản...)
    protected abstract boolean validatePaymentInfo();

    // Tính phí giao dịch theo từng phương thức thanh toán
    protected abstract double calculateFee(double amount);

    // Thực hiện thanh toán với tổng tiền (đã gồm phí)
    protected abstract boolean executePayment(double total);
}