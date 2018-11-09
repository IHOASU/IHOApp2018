/******************************************************************************************************************
 # Name of Program  :   WebViewLecturerDetailController.swift
 #
 # Description      :   In-app web view of the Lecturer Details page
 #
 # Created By       :   Masters SE Team (app version 3)
 #
 # Created On       :   6 Nov 2018
 #
 # Version          :   1.0
 #*****************************************************************************************************************/

import Foundation

import UIKit
import SafariServices

class WebViewLecturerDetailController: UIViewController {
    
    var newsLinkString = String ()
    @IBOutlet weak var openWeb: UIWebView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let url = NSURL(string : newsLinkString)!
        let request = NSURLRequest(url : url as URL)
        openWeb.loadRequest(request as URLRequest)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
}
