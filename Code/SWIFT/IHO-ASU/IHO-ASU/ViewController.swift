/******************************************************************************************************************
 # Name of Program  :   ViewController.swift
 #
 # Description      :   Home page
 #
 # Created By       :   Masters SE Team (app version 2)
 #
 # Created On       :   7 February 2017
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

class ViewController: UIViewController {
    
    @IBOutlet weak var donate: UIButton!
    @IBOutlet weak var about: UIButton!
    @IBOutlet weak var gallery: UIButton!
    @IBOutlet weak var connect: UIButton!
    @IBOutlet weak var field: UIButton!
    @IBOutlet weak var news: UIButton!
    var htmlpath: String? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        UINavigationBar.appearance().barTintColor = UIColor(red: CGFloat((255.0 / 255.0)), green: CGFloat((255.0 / 255.0)), blue: CGFloat((255.0 / 255.0)), alpha: CGFloat(1))
        
        // button sytle
        news.layer.cornerRadius = 0
        about.layer.cornerRadius = 0
        donate.layer.cornerRadius = 0
        gallery.layer.cornerRadius = 0
        connect.layer.cornerRadius = 0
        field.layer.cornerRadius = 0
        
        // ASU LOGO at navigation bar
        self.navigationController?.navigationBar.frame.size.height=50;
        var imageView: UIImageView?
        var ipad: Bool = (UIDevice.current.userInterfaceIdiom == .pad)
        
        // skull LOGO
        htmlpath = Bundle.main.path(forResource: "skull", ofType: "html")
        var html = try? String(contentsOfFile: htmlpath!, encoding: String.Encoding.utf8)
        var baseURL = URL(fileURLWithPath: "\(Bundle.main.bundlePath)")
        
        //toolbar
        let label = UILabel(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(200), height: CGFloat(21)))
        label.text = "ASU IHO 2018"
        label.center = CGPoint(x: view.frame.midX, y: view.frame.height)
        label.textAlignment = NSTextAlignment.center
        label.textColor = UIColor.white
        
        
        let creditsButton = UIButton(frame: CGRect(x: CGFloat(-200), y: CGFloat(0), width: CGFloat(60), height: CGFloat(21)))
        creditsButton.backgroundColor = nil
        
        UIColor(red: CGFloat((0 / 255.0)), green: CGFloat((51 / 255.0)), blue: CGFloat((102 / 255.0)), alpha: CGFloat(1))
        creditsButton.setTitle("Credits", for: .normal)
        creditsButton.addTarget(self, action: #selector(buttonAction), for: .touchUpInside)
        creditsButton.tag = 1
        self.view.addSubview(creditsButton)
        
        let toolbarButton = UIBarButtonItem(customView: creditsButton)
        let toolbarTitle = UIBarButtonItem(customView: label)
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        self.toolbarItems = [flexible,toolbarTitle,toolbarButton]
    }
    
    
    func buttonAction(sender: UIButton!) {
        var btnsendtag: UIButton = sender
        if btnsendtag.tag == 1 {
            self.performSegue(withIdentifier: "CreditsSeque", sender: self)
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.barTintColor = UIColor(red: CGFloat((255.0 / 255.0)), green: CGFloat((255.0 / 255.0)), blue: CGFloat((255.0 / 255.0)), alpha: CGFloat(1))
        self.navigationController?.setToolbarHidden(false, animated: false)
        self.navigationController?.navigationBar.isHidden=true
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.navigationController?.setToolbarHidden(true, animated: false)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }


}

