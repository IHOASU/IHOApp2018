package edu.asu.msse.gnayak2.bl;

import edu.asu.msse.gnayak2.delegates.GalleryDelegate;
import edu.asu.msse.gnayak2.delegates.LecturesDelegate;
import edu.asu.msse.gnayak2.library.LecturesGalleryLibrary;
import edu.asu.msse.gnayak2.models.GalleryModel;
import edu.asu.msse.gnayak2.models.Lecture;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;
import net.miginfocom.swing.MigLayout;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditLecturesFrame extends JFrame implements GalleryDelegate {

	private JPanel containerPanel;
	private JPanel mainPanel;
	private JPanel galleryPanel;

	private CardLayout cardLayout;
	
	private JButton viewGalleryButton;
	private JButton deleteGalleryButton;
	private GalleryModel selectedImage;
	private JList<GalleryModel> imageList;
	private DefaultListModel<GalleryModel>  galleryModel;
	private JButton btnAddGallery;
	private LecturesGalleryLibrary lecturesGalleryLibrary;
	GalleryDelegate galleryDelegate;
	
	private JTextField tfName;
	private JTextArea taDescription;
	private JTextField tfLink;
	private JLabel lblReadMore;
	private JLabel lblTitle;
	private JLabel lblName;
	private JLabel lblOrder;
	private JLabel lblEmail;
	private JLabel lbldesc;
	//private JLabel imageLabel;
	private JTextField tfDesc;
	private JTextField tfEmail;
	private JTextField tfOrder;
	private JScrollPane scrollPane;
	private Lecture lecture;
	private JButton btnSubmit;
	private JButton addButton;
	private JLabel lblImageUrl;
	private JTextField tfImageUrl;


	private JTextField imageFileButton;
	LecturesDelegate lectureDelegate;
	String filename;
	String encodedImage;
	int flag = 0;
	
	byte[] imageInByte;
	private JButton galleryButton;
	
	private Pattern pattern;
	private Matcher matcher;
	
	/**
	 * Create the frame.
	 */
	public EditLecturesFrame(Lecture item, LecturesDelegate lectureDelegate) {
		lecture = item;
		this.lectureDelegate = lectureDelegate;
		setUpFrame();
		populateFileds(lecture);
	}
	
	public EditLecturesFrame(LecturesDelegate lecturedelegate) {
		this.lectureDelegate = lecturedelegate;
		setUpFrame();
	}
	
	public void setUpFrame() {
//		galleryDelegate = this;
//
//		setResizable(true);
//		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
//		cardLayout = new CardLayout();
//		containerPanel = new JPanel(cardLayout);
//
//		String BUTTONPANEL = "Card with JButtons";
//		String TEXTPANEL = "Card with JTextField";
//
//		initializeMainPanel();
//		initializeGalleryLayout();
//		containerPanel.add(galleryPanel, BUTTONPANEL);
//		containerPanel.add(mainPanel, TEXTPANEL);
//
////		add(galleryPanel);
////		add(mainPanel);
////		add(containerPanel);
//		cardLayout.show(containerPanel, TEXTPANEL);
//
////		cardLayout.
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		pack();
//		setVisible(true);

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame window = new JFrame("CardLayout Example");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(300,300);
		window.setLayout(new BorderLayout());


		final CardLayout cardLayout = new CardLayout();
		final JPanel cardPanel = new JPanel(cardLayout);

		// create two dummy panels (the "cards") to show
		JPanel card1 = new JPanel();
		card1.setBackground(Color.red);

		JPanel card2 = new JPanel();
		card2.setBackground(Color.blue);

		initializeGalleryLayout();
		initializeMainPanel();
		cardPanel.add(galleryPanel,"RedCard");
		cardPanel.add(mainPanel,"BlueCard");


		// create two buttons
		JPanel buttonPanel = new JPanel();
		JButton b1 = new JButton("Profile");
		JButton b2 = new JButton("Gallery");
		buttonPanel.add(b1);
		buttonPanel.add(b2);



		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cardLayout.show(cardPanel, "RedCard");
			}
		});

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cardLayout.show(cardPanel, "BlueCard");
			}
		});


		// add card & button panels to the main window
		window.add(cardPanel,BorderLayout.CENTER);
		window.add(buttonPanel,BorderLayout.SOUTH);
		window.setVisible(true);
	}
		

	public void initializeMainPanel() {
		tfName = new JTextField();
		taDescription = new JTextArea("",120,120);
		lblReadMore = new JLabel("Read More: ");
		lblEmail = new JLabel("Email");
		lblName = new JLabel("Name");
		lblOrder = new JLabel("Order");
		lblTitle = new JLabel("Title");
		lbldesc = new JLabel("Description");
		lblImageUrl = new JLabel("Image Url from DropBox");
	//	imageLabel = new JLabel("Image Label");
		tfLink = new JTextField("http://",120);


		imageFileButton = new JTextField("",120);
		tfDesc = new JTextField("",120);
		tfImageUrl = new JTextField("",120);
		tfEmail = new JTextField("",120);
		tfOrder = new JTextField("",120);
		scrollPane = new JScrollPane(taDescription);
		btnSubmit = new JButton("Submit");
		galleryButton = new JButton("Gallery");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout());

		mainPanel.add(lblName);

		mainPanel.add(tfName
				, "span,pushx,growx, wrap");
		mainPanel.add(lblReadMore);
		mainPanel.add(tfLink, "wrap");

		mainPanel.add(lblTitle);
		mainPanel.add(tfDesc, "wrap");
		mainPanel.add(lblEmail);
		mainPanel.add(tfEmail, "wrap");
		mainPanel.add(lblOrder);
		mainPanel.add(tfOrder,"wrap");
		mainPanel.add(lbldesc);
		mainPanel.add(scrollPane,"wrap");

		mainPanel.add(lblImageUrl);
		mainPanel.add(tfImageUrl, "wrap");
		mainPanel.add(btnSubmit, "wrap");

		setActionListenerForButton();
	}
	
	public void initializeGalleryLayout() {
		galleryPanel = new JPanel();
		JButton galleryBackButton = new JButton("Back");
		viewGalleryButton = new JButton("View");
		deleteGalleryButton = new JButton("Delete");

		btnAddGallery = new JButton("Add");

		galleryPanel.setLayout(new MigLayout());
		galleryPanel.add(galleryBackButton);
		galleryPanel.add(viewGalleryButton);
		galleryPanel.add(deleteGalleryButton);

		galleryPanel.add(btnAddGallery, "wrap");
		imageList =  new JList<>();
		galleryPanel.add(new JScrollPane(imageList),"span,push,grow, wrap");

		galleryModel = new DefaultListModel<>();
		imageList.setModel(galleryModel);

		imageList.setCellRenderer(new ImageListRenderer());
		galleryBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(containerPanel, "1");
			}
		});


	    imageList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedImage = imageList.getSelectedValue();
				//System.out.println(selectedNews.getDesc());
			}
		});

		viewGalleryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedImage != null) {
					EditGallery editFrame = new EditGallery(selectedImage, galleryDelegate);
					editFrame.setVisible(true);
				}
			}
		});

		btnAddGallery.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditGallery galleryFrame = new EditGallery(galleryDelegate);
				galleryFrame.setVisible(true);
			}
		});

		deleteGalleryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedImage != null) {
					lecturesGalleryLibrary.deleteGallery(selectedImage.getId());
					galleryModel.removeElement(selectedImage);
					selectedImage = null;
					putLibraryToServer();
				}
			}
		});

	}
	
	public void putLibraryToServer() {
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			/*
			 * Assumption that lecture library is already there and lecture object is 
			 * not null because lectureGalleryLibrary already has lecture id
			 */
			
			// delete from library
			
			// make a put request of the entire library
			JSONObject lectureGalleryLibraryJson = convertLibraryToJSONOjbect();
			helper.put(lecturesGalleryLibrary.getLectureId(), lectureGalleryLibraryJson);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void postLibraryToServer() {
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		try {
			/*
			 * Assumption that lecture library is already there and lecture object is 
			 * not null because lectureGalleryLibrary already has lecture id
			 */
			
			// delete from library
			
			// make a put request of the entire library
			JSONObject lectureGalleryLibraryJson = convertLibraryToJSONOjbect();
			helper.post("lectureimages", lectureGalleryLibraryJson);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public JSONObject convertLibraryToJSONOjbect() {
		JSONObject resultObject = new JSONObject();
		resultObject.put("id", lecturesGalleryLibrary.getLectureId());
		JSONArray jsonArray = new JSONArray();
		
		Set<String> galleryid = lecturesGalleryLibrary.getKeySet();		
		for(String id: galleryid) {
			jsonArray.put(new JSONObject(lecturesGalleryLibrary.getGallery(id)));
		}
		resultObject.put("imagesarray", jsonArray);
		
		return resultObject;
	}
	
	public void initializeGalleryData() {
		if (lecture != null) {
			if (lecturesGalleryLibrary == null) {
				lecturesGalleryLibrary = new LecturesGalleryLibrary(lecture.getEmail(), false);
			} 
			Set<String> galleryid = lecturesGalleryLibrary.getKeySet();
			galleryModel.clear();
			
			for(String id: galleryid) {
				galleryModel.addElement(lecturesGalleryLibrary.getGallery(id));
			}
		} else {
			if(tfEmail.getText().equals("")){
				 JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid email id", "Dialog",
					        JOptionPane.ERROR_MESSAGE);
			} else {
				lecturesGalleryLibrary = new LecturesGalleryLibrary(tfEmail.getText(), true);
				postLibraryToServer();
			}
		}
	}
	
	public void populateFileds(Lecture lecture) {
		tfName.setText(lecture.getName());
		taDescription.setText(lecture.getBio());
		tfLink.setText(lecture.getLink());
		tfDesc.setText(lecture.getTitle());
		tfEmail.setText(lecture.getEmail());
		imageFileButton.setText(lecture.getImage());
	
		String order_value = Integer.toString(lecture.getOrder());
		tfOrder.setText(order_value);
//		  BufferedImage bufferedImage = convertStringToImage(lecture.getImage());
//			ImageIcon imageIcon = null;
			 
//	        if (bufferedImage != null) {
//	        	 imageIcon = new ImageIcon(bufferedImage);
//	        	 Image oldImage = imageIcon.getImage(); // transform it
//	        	 Image newImage = oldImage.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
//	        	 imageIcon = new ImageIcon(newImage);
//	        	 JLabel imageLabel = new JLabel(imageIcon);
//	        	 mainPanel.add(imageLabel);
//
//	        }
//

	        setPreferredSize(new Dimension(180,100));
	}
	private BufferedImage convertStringToImage(String base64String) {
    	BufferedImage image = null;
    	byte[] imageByte;
    	imageByte = Base64.getDecoder().decode(base64String);
    	ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
    	try {
			image = ImageIO.read(bis);
			bis.close();
			return image;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}     	

    }
	
	public boolean validate(final String order){
		 String ORDER_PATTERN =
				 "^\\d+$";
		  pattern = Pattern.compile(ORDER_PATTERN);
  matcher = pattern.matcher(order);

  if(matcher.matches()){
  System.out.println("Matches");
	 matcher.reset();

	 return true;
  }else{
	  JOptionPane.showMessageDialog(tfOrder, "Please enter an integer value for order");   
	  return false;
  }
}
	
	
	public void setActionListenerForButton() {
		
		galleryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initializeGalleryData();
				cardLayout.show(containerPanel, "2");
			}
		});
		
		// delete old lecture
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(flag==1)
				{
				 try{
                      
					 	BufferedImage originalImage =
					                               ImageIO.read(new File(filename));
			      	 	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			  		 	ImageIO.write( originalImage, "jpg", baos );
					 	baos.flush();
					 	imageInByte = baos.toByteArray();
				

			            encodedImage = Base64.getEncoder().encodeToString(imageInByte);
					 	System.out.println("BYTE ARRAY_________"+imageInByte);
					 	baos.close();
                       
                        
			           	}catch(IOException e1){
					 		System.out.println(e1.getMessage());
						 	}
				}
				 boolean validate = validate(tfOrder.getText());
				 if(validate)
				 {
					 if(flag==1){
						 int ord = Integer.parseInt(tfOrder.getText());
						 Lecture newLecture = new Lecture(
						 			tfName.getText(),
								 taDescription.getText(),
								 tfLink.getText(),
								 tfDesc.getText(),
								 encodedImage,
								 convertToStaticDropBoxUrl(tfImageUrl.getText().trim()),
								 tfEmail.getText(),
								 ord);
						 lectureDelegate.addLecture(newLecture);
					 }

					 else {
						 int ord = Integer.parseInt(tfOrder.getText());
						 Lecture newLecture = new Lecture(
						 		tfName.getText(),
								 taDescription.getText(),
								 tfLink.getText(),
								 tfDesc.getText(),
								 imageFileButton.getText(),
								 convertToStaticDropBoxUrl(tfImageUrl.getText().trim()),
								 tfEmail.getText(),
								 ord);
						 lectureDelegate.addLecture(newLecture);
					 }
					 // delete old lecture

					 if (lecture != null){
						 lectureDelegate.deleteLecture(lecture);
					 }
					 if (lecture == null && lecturesGalleryLibrary == null) {
						 lecturesGalleryLibrary = new LecturesGalleryLibrary(tfEmail.getText(), true);
						 postLibraryToServer();

					 }
					 dispose();
				 }
			}
		});
	}

	//https://www.dropbox.com/s/d0duvt4a5lq17yl/boyd.jpg?dl=0 is converted to
	//https://dl.dropboxusercontent.com/s/d0duvt4a5lq17yl/boyd.jpg
	private String convertToStaticDropBoxUrl(String urlWithMetaData) {
		return urlWithMetaData.replace("www.dropbox.com", "dl.dropboxusercontent.com").replace("?dl=0", "");
	}

	public class ImageListRenderer extends JLabel implements ListCellRenderer<GalleryModel> {
		 
	    public ImageListRenderer() {
	        setOpaque(true);
	    }
	 
	    @Override
	    public Component getListCellRendererComponent(JList<? extends GalleryModel> list, GalleryModel galleryModel, int index,
	            boolean isSelected, boolean cellHasFocus) {
	    	
	        String code = galleryModel.getTitle();
	        BufferedImage bufferedImage = convertStringToImage(galleryModel.getImage());
	        ImageIcon imageIcon = null;
	        if (bufferedImage != null) {
	        	 imageIcon = new ImageIcon(bufferedImage);
	        	 imageIcon = new ImageIcon(bufferedImage);
	        	 Image oldImage = imageIcon.getImage(); // transform it 
	        	 Image newImage = oldImage.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	        	 imageIcon = new ImageIcon(newImage); 
	        }
	        
	        setIcon(imageIcon);
	        setPreferredSize(new Dimension(180,100));
	        setText(code);
	        setToolTipText(code);

	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }
	 
	        return this;
	    }
	    
	    private BufferedImage convertStringToImage(String base64String) {
	    	BufferedImage image = null;
	    	byte[] imageByte;
	    	imageByte = Base64.getDecoder().decode(base64String);
	    	ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	    	try {
				image = ImageIO.read(bis);
				bis.close();
				return image;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}     	

	    }
	}
	@Override
	public void addGallery(GalleryModel gallery) {
		galleryModel.addElement(gallery);
		lecturesGalleryLibrary.addToLibrary(gallery);
		putLibraryToServer();
	}

	// This method is only for delegate add or edit. Delete is handled separately. 
	// Since we are making a put request, we delete only from library but make a put 
	// request to server only in above add method.
	@Override
	public void deleteGallery(GalleryModel gallery) {
		if (selectedImage != null) {
			lecturesGalleryLibrary.deleteGallery(selectedImage.getId());
			galleryModel.removeElement(selectedImage);
			selectedImage = null;
		}
	} 


}

