/******************************************************************************************************************
 # Name of Program  :   AboutViewController.swift
 #
 # Description      :   About page
 #
 # Created By       :   Masters SE Team (app version 2)
 #
 # Created On       :   13 February 2017
 #
 # Version          :   1.0
 #*****************************************************************************************************************
 # Revised By       :   Masters SE Team-3 (app version 3)
 #
 # Revised On       :   16 October 2018
 #
 # Version          :   1.1
 #*****************************************************************************************************************/

import UIKit

class AboutViewController: UIViewController , UIWebViewDelegate {

    @IBOutlet weak var contactText: UITextView!
    @IBAction func mapIt(_ sender: Any) {
        
        let url = URL(string: "https://maps.google.com/maps?q=951+South+Cady+Mall,+Tempe,+AZ&hl=en&ll=33.420231,-111.930749&spn=0.011158,0.014999&sll=33.41972,-111.934757&sspn=0.002933,0.002591&oq=951+South+Cady+Mall&hnear=951+S+Cady+Mall,+Tempe,+Maricopa,+Arizona+85281&t=m&z=16")!
        
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            UIApplication.shared.openURL(url)
        }
    }
    @IBOutlet weak var aboutView: UIWebView!
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        aboutView.scrollView.isScrollEnabled = false
        aboutView.delegate = self
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        self.navigationItem.title = "About"
        aboutView.loadRequest(URLRequest(url: URL(fileURLWithPath: Bundle.main.path(forResource: "About", ofType: "html")!)))
        
        // to be used later --
//        let attributedString = NSMutableAttributedString(string: "Just click here to register")
//        let url = URL(string: "https://www.apple.com")!
//
//        // Set the 'click here' substring to be the link
//        attributedString.setAttributes([NSLinkAttributeName: url], range: NSMakeRange(5, 9))
//
//        contactText.attributedText = attributedString
//        contactText.isUserInteractionEnabled = true
//        contactText.isEditable = false
//
//        // Set how links should appear: blue and underlined
//        contactText.linkTextAttributes = [
//            NSForegroundColorAttributeName: UIColor.blue,
//            NSUnderlineStyleAttributeName: NSUnderlineStyle.styleSingle.rawValue
//        ]
        
        
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
    
    func webView(_ webView: UIWebView, shouldStartLoadWith request: URLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        
        if navigationType == UIWebViewNavigationType.linkClicked {
            UIApplication.shared.openURL(request.url!)
            return false
        }
        
        return true
        
    }
    
    func webViewDidFinishLoad(aboutView: UIWebView) {
        aboutView.frame.size.height = 1
        aboutView.frame.size = aboutView.sizeThatFits(CGSize.zero)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        self.navigationController?.navigationBar.isHidden=false;
        self.navigationController?.navigationBar.barTintColor = UIColor(red: CGFloat((3 / 255.0)), green: CGFloat((36 / 255.0)), blue: CGFloat((83 / 255.0)), alpha: CGFloat(1))
        
        self.navigationController?.navigationBar.tintColor = UIColor.white
        
        self.navigationController!.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName : UIColor.white]
        self.navigationController?.setToolbarHidden(false, animated: false)
    }

}
