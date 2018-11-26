/******************************************************************************************************************
 # Name of Program  :   LecturerDetailViewController.swift
 #
 # Description      :   Lecturer details page
 #
 # Created By       :   Masters SE Team (app version 2)
 #
 # Created On       :   30 March 2017
 #
 # Version          :   1.0
 #*****************************************************************************************************************
 # Revised By       :   Masters SE Team-3 (app version 3)
 #
 # Revised On       :   16 October 2018
 #
 # Version          :   1.1
 #*****************************************************************************************************************/

import Foundation
import MessageUI
import UIKit

// Contoller for Displaying professor's details page
class LecturerDetailViewController: UITableViewController, MFMailComposeViewControllerDelegate {
    
    // Email functionality
    @IBAction func lEmail(_ sender: Any) {
        if MFMailComposeViewController.canSendMail() {
            let mail = MFMailComposeViewController()
            mail.mailComposeDelegate = self
            mail.setToRecipients([newsEmail!])
            mail.setSubject("Ask a question")
            mail.setMessageBody("<p>Enter your question here</p>" ,isHTML: true)
            present(mail, animated: true, completion: nil)
        } else {
            print("Error in mail compose")
            // show failure alert
        }
    }
    
    @IBAction func lGallery(_ sender: Any) {
    }
    @IBOutlet weak var nImage: UIImageView!
    @IBOutlet weak var buttonReadMore: UIButton!
    @IBOutlet weak var nTitle: UILabel!
    @IBOutlet weak var lecEmail: UIButton!
    
    @IBOutlet weak var gallery: UIButton!
    @IBOutlet weak var nDesc: UILabel!
    @IBOutlet weak var lecTitle: UILabel!
    var newsTitle: String?
    var newsBio: String?
    var newsId: String?
    var newsImage: String?
    var newsLink: String?
    var newsName: String?
    var newsEmail: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        
        buttonReadMore.layer.cornerRadius = 0
        gallery.layer.cornerRadius = 0
        lecEmail.layer.cornerRadius = 0
        
        if(newsName != nil){
            nTitle.text = newsName
            nTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
            nTitle.numberOfLines = 0
        }
        if(newsBio != nil){
        nDesc.text = newsBio
        nDesc.lineBreakMode = NSLineBreakMode.byWordWrapping
        nDesc.numberOfLines = 0
        }
        if(newsTitle != nil){
        newsTitle = newsTitle!.replacingOccurrences(of: "\\n", with: "\n")
        lecTitle.text = newsTitle
        lecTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
        lecTitle.numberOfLines = 0
        }
        
        if (newsImage != nil)
        {
            
            // getting the image URL
            let imageUrl:URL = URL(string: newsImage!)!
            
            // getting the image data from the image URL
            let imageData:NSData = NSData(contentsOf: imageUrl)!
            let imageView = UIImageView(frame: CGRect(x:0, y:0, width:200, height:200))
            imageView.center = self.view.center
            
            // setting the image to view
            let image = UIImage(data: imageData as Data)
            self.nImage.image = image
        }
        
        //toolbar
        let label = UILabel(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(350), height: CGFloat(21)))
        label.text = "ASU IHO 2018"
        label.center = CGPoint(x: view.frame.midX, y: view.frame.height)
        label.textAlignment = NSTextAlignment.center
        label.textColor = UIColor.white
        let toolbarTitle = UIBarButtonItem(customView: label)
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        self.toolbarItems = [flexible,toolbarTitle]
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        self.navigationController?.navigationBar.isHidden=false;
        self.navigationController?.setToolbarHidden(false, animated: false)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.setToolbarHidden(true, animated: false)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // connecting and sending data to LecturerGalleryControllerView
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        NSLog("seque identifier is \(segue.identifier)")
        if segue.identifier == "LecturerGallery" {
            let viewController:LecturerGalleryViewController = segue.destination as! LecturerGalleryViewController
            if(newsEmail != nil){
            viewController.lecEmail  = self.newsEmail!
            }
        }
        
        if segue.identifier == "LecturerDetails" {
            let viewController:WebViewLecturerDetailController = segue.destination as! WebViewLecturerDetailController
            viewController.newsLinkString = newsLink!
        }
    }
    
    // Mail composing controller
    func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
        dismiss(animated: true, completion: nil)
    }
    
    // Control returns from Email client
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        
        return true
    }
    
}


