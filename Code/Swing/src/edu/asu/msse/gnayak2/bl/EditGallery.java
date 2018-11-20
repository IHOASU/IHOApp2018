package edu.asu.msse.gnayak2.bl;

import edu.asu.msse.gnayak2.delegates.GalleryDelegate;
import edu.asu.msse.gnayak2.models.GalleryModel;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditGallery extends JFrame {

    private JPanel panel;
    private JTextField tfTitle;
    private JTextArea taDescription;
    private JTextField tfLink;
    private JTextField tfOrder;
    private JLabel lblReadMore;
    private JLabel lblOrder;
    private JLabel lblTitle;
    private JScrollPane scrollPane;

    private GalleryModel gallery;
    private JButton btnSubmit;
    private JLabel lblImageUrl;
    private JTextField tfImageUrl;

    GalleryDelegate galleryDelegate;
    int flag = 0;
    private Pattern pattern;
    private Matcher matcher;

    /**
     * Create the frame.
     */
    public EditGallery(GalleryModel item, GalleryDelegate galleryDelegate) {
        gallery = item;
        this.galleryDelegate = galleryDelegate;
        setUpFrame();
        populateFileds(gallery);
    }

    public EditGallery(GalleryDelegate galleryDelegate) {
        this.galleryDelegate = galleryDelegate;

        setUpFrame();
    }

    public void setUpFrame() {
        setResizable(false);
        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));

        tfTitle = new JTextField("", 20);
        tfOrder = new JTextField("", 10);

//		scrollPane = new JScrollPane(taDescription);
        btnSubmit = new JButton("Submit");
        lblOrder = new JLabel("Order");

        panel = new JPanel();
        panel.add(new JLabel("Image Caption"));
        panel.add(tfTitle, "span,pushx,growx, wrap");

        lblImageUrl = new JLabel("Image Url from DropBox");
        panel.add(lblImageUrl);
        tfImageUrl = new JTextField("", 20);
        panel.add(tfImageUrl);

        panel.add(lblOrder);
        panel.add(tfOrder);

        panel.add(btnSubmit, "wrap");


        add(panel);
        pack();
        setVisible(true);
        setActionListenerForButton();
    }

    public void populateFileds(GalleryModel gallery) {
        tfTitle.setText(gallery.getTitle());
        String order = Integer.toString(gallery.getOrder());
        tfOrder.setText(order);
        tfImageUrl.setText(gallery.getImageUrl());
        setPreferredSize(new Dimension(180, 100));
    }

    public boolean validate(final String order) {
        String ORDER_PATTERN =
                "^\\d+$";
        pattern = Pattern.compile(ORDER_PATTERN);
        matcher = pattern.matcher(order);

        if (matcher.matches()) {
            System.out.println("Matches");
            matcher.reset();

            return true;
        } else {
            JOptionPane.showMessageDialog(tfOrder, "Please enter an integer value for order");
            return false;
        }
    }

    public void setActionListenerForButton() {
        btnSubmit.addActionListener(e -> {
            boolean isValid = validate(tfOrder.getText());
            if (isValid) {
                    int ord = Integer.parseInt(tfOrder.getText());

                    GalleryModel newGallery = new GalleryModel(
                            tfTitle.getText(),
                            ord,
                            tfImageUrl.getText().trim());
                    galleryDelegate.addGallery(newGallery);
                if (gallery != null) {
                    galleryDelegate.deleteGallery(gallery);
                    gallery = null;
                }
                dispose();
            }

        });
    }
}