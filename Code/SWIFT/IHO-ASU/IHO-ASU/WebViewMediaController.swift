/******************************************************************************************************************
 # Name of Program  :   WebViewMediaController.swift
 #
 # Description      :   In-app web view of the "In the Media" page
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

class WebViewMediaController: UIViewController {
    
    
    @IBOutlet weak var openWeb: UIWebView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let url = NSURL(string : "https://iho.asu.edu/media")!
        let request = NSURLRequest(url : url as URL)
        openWeb.loadRequest(request as URLRequest)
    }
    
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}
