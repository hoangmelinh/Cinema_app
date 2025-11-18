package ui;

import model.Cinema; // Import model
import javax.swing.*;
import java.awt.*;

// 1. Class này kế thừa JPanel VÀ implements ListCellRenderer
public class CinemaRender extends JPanel implements ListCellRenderer<Cinema> {

    // 2. Khai báo các thành phần trong "template"
    private JLabel cinemaNameLabel;
    private JLabel cinemaAddressLabel;

    public CinemaRender() {
        // 3. Thiết lập layout cho template
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Thêm lề

        // 4. Khởi tạo các thành phần
        cinemaNameLabel = new JLabel();
        cinemaNameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Tên in đậm

        cinemaAddressLabel = new JLabel();
        cinemaAddressLabel.setFont(new Font("Arial", Font.ITALIC, 12)); // Địa chỉ in nghiêng
        cinemaAddressLabel.setForeground(Color.DARK_GRAY); // Màu xám

        // 5. Thêm vào template
        add(cinemaNameLabel, BorderLayout.NORTH);
        add(cinemaAddressLabel, BorderLayout.SOUTH);
    }

    // 6. Đây là hàm quan trọng nhất
    // JList sẽ gọi hàm này cho TỪNG HÀNG
    @Override
    public Component getListCellRendererComponent(JList<? extends Cinema> list,
                                                  Cinema cinema, // Đối tượng Cinema của hàng đó
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

        // 7. Gán dữ liệu của đối tượng vào template
        cinemaNameLabel.setText(cinema.getName());
        cinemaAddressLabel.setText(cinema.getAddress());

        // 8. Xử lý màu sắc khi người dùng bấm chọn
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            cinemaNameLabel.setForeground(list.getSelectionForeground());
            cinemaAddressLabel.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            cinemaNameLabel.setForeground(Color.BLACK); // (Hoặc màu bạn muốn)
            cinemaAddressLabel.setForeground(Color.DARK_GRAY);
        }

        return this; // Trả về chính template này
    }
}