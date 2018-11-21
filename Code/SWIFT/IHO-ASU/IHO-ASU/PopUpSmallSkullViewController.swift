/******************************************************************************************************************
 # Name of Program  :   PopUpSmallSkullViewController.swift
 #
 # Description      :   Pop-up page for left skull
 #
 # Created By       :   Masters SE Team (app version 3)
 #
 # Created On       :   20 November 2018
 #
 # Version          :   1.0
 #*****************************************************************************************************************/

import Foundation
import UIKit

class PopUpSmallSkullViewController: UIViewController {
    
    @IBOutlet weak var popupView: UITextView!
    @IBOutlet weak var closeButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        popupView.layer.cornerRadius = 10
        closeButton.layer.cornerRadius = 10
    }
    
    
    @IBAction func closePopup(_ sender: Any) {
        dismiss(animated: true, completion:nil)
    }
    
}
