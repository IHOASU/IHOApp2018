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


class LecturerDetailViewController: UITableViewController, MFMailComposeViewControllerDelegate {
    
    
    @IBAction func linkReadMore(_ sender: Any) {
        let url = URL(string: newsLink!)!
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
        
    }
    @IBAction func lEmail(_ sender: Any) {
        if MFMailComposeViewController.canSendMail() {
            let mail = MFMailComposeViewController()
            mail.mailComposeDelegate = self
            //mail.delegate = self as! UINavigationControllerDelegate
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
            //base64 string to NSData
            let decodedData = NSData(base64Encoded: newsImage!, options: NSData.Base64DecodingOptions(rawValue: 0))
            
            //NSData to UIImage
            nImage.image = UIImage(data: decodedData! as Data)
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
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        NSLog("seque identifier is \(segue.identifier)")
        if segue.identifier == "LecturerGallery" {
            let viewController:LecturerGalleryViewController = segue.destination as! LecturerGalleryViewController
            if(newsEmail != nil){
            viewController.lecEmail  = self.newsEmail!
            }
        }
    }
    
    func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
        dismiss(animated: true, completion: nil)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        
        return true
    }
    
}


