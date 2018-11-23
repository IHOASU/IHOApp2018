/******************************************************************************************************************
 # Name of Program  :   FeaturedNewsViewController.swift
 #
 # Description      :   Featured News page
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
import UIKit

class FeaturedNewsViewController: UITableViewController {
    @IBOutlet var featuredNewsTableView: UITableView!

    @IBOutlet weak var nTitle: UILabel!
    @IBOutlet weak var nDesc: UITextView!
    @IBOutlet weak var nImage: UIImageView!
    //@IBOutlet weak var readMoreButton: UIButton!
    var newsTitle: String?
    var newsDesc: String?
    var newsId: String?
    var newsImage: String?
    var newsLink: String?
    var textView : UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        textView = UITextView()
        textView.sizeThatFits(CGSize(width: textView.frame.size.width, height:textView.frame.size.height))
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Featured News"
        //self.readMoreButton.layer.cornerRadius = 15
        
        if(newsTitle != nil){
        self.nTitle.text = self.newsTitle
        self.nTitle.lineBreakMode = NSLineBreakMode.byWordWrapping
        self.nTitle.numberOfLines = 0
        }
        if(newsDesc != nil){
        self.nDesc.text = self.newsDesc
        //self.nDesc.lineBreakMode = NSLineBreakMode.byWordWrapping
        //self.nDesc.numberOfLines = 0
        }
        
        if (self.newsImage != nil)
        {
            //base64 string to NSData
            let decodedData = NSData(base64Encoded: self.newsImage!, options: NSData.Base64DecodingOptions(rawValue: 0))
            
            //NSData to UIImage
            self.nImage.image = UIImage(data: decodedData! as Data)
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
        self.tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        
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
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        var webViewFNewsController = segue.destination as! WebViewFeaturedNewsController
        webViewFNewsController.newsLinkString = newsLink!
    }
    
}
