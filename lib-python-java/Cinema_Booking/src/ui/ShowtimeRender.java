package ui;

import model.Showtime;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat; // Cần để format giờ
import java.text.NumberFormat;  // Cần để format tiền

public class ShowtimeRender extends JPanel implements ListCellRenderer<Showtime> {

    private JLabel timeLabel;
    private JLabel priceLabel;
    private SimpleDateFormat timeFormatter;
    private NumberFormat currencyFormatter;

    public ShowtimeRender() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));

        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setForeground(Color.RED.darker()); // Màu đỏ đô

        add(timeLabel, BorderLayout.NORTH);
        add(priceLabel, BorderLayout.SOUTH);

        // Định dạng (formatters)
        timeFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        currencyFormatter = NumberFormat.getCurrencyInstance(); // (Format tiền tệ mặc định, VD: $)
        // (Nếu muốn VND, bạn cần làm phức tạp hơn một chút)
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Showtime> list,
                                                  Showtime showtime, // Đối tượng Showtime
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

        // Gán dữ liệu (kiểm tra null)
        if (showtime.getDate() != null) {
            timeLabel.setText(timeFormatter.format(showtime.getDate()));
        } else {
            timeLabel.setText("N/A");
        }

        // (Giả sử bạn có hàm getPrice())
        priceLabel.setText(String.format("Giá: %.0f VND", showtime.getPrice()));

        // Xử lý màu sắc khi chọn
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            timeLabel.setForeground(list.getSelectionForeground());
            priceLabel.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            timeLabel.setForeground(Color.BLACK);
            priceLabel.setForeground(Color.RED.darker());
        }

        return this;
    }
}