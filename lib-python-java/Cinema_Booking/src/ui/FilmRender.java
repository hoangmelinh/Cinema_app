package ui;

import model.Film;
import javax.swing.*;
import java.awt.*;

// Kế thừa JPanel và implements ListCellRenderer
public class FilmRender extends JPanel implements ListCellRenderer<Film> {

    // Các thành phần trong template
    private JLabel titleLabel;
    private JLabel genreLabel;

    public FilmRender() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        genreLabel = new JLabel();
        genreLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        genreLabel.setForeground(Color.DARK_GRAY);

        add(titleLabel, BorderLayout.NORTH);
        add(genreLabel, BorderLayout.SOUTH);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Film> list,
                                                  Film film, // Đối tượng Film
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

        // Gán dữ liệu vào template
        titleLabel.setText(film.getTitle());
        genreLabel.setText(film.getGenre()); // (Giả sử bạn có hàm getGenre())

        // Xử lý màu sắc khi chọn
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            titleLabel.setForeground(list.getSelectionForeground());
            genreLabel.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            titleLabel.setForeground(Color.BLACK);
            genreLabel.setForeground(Color.DARK_GRAY);
        }

        return this; // Trả về template
    }
}