/******************************************************************************************************************
 # Name of Program  :   LecturerGalleryViewController.swift
 #
 # Description      :   Lectures gallery page
 #
 # Created By       :   Masters SE Team (app version 2)
 #
 # Created On       :   19 April 2017
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

class LecturerGalleryTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var imageview: UIImageView!
    
    @IBOutlet weak var textlabel: UITextView!
}

// Controller for Professor's individual gallery
class LecturerGalleryViewController: UITableViewController {
    
    @IBOutlet var galleryTableView: UITableView!
    
    var urlString:String = ""
    var imageList:[String : Image] = [String : Image]()
    var names:[String]=[String]()
    var lecEmail:String = ""
    var reachability: Reachability = Reachability();
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let path = Bundle.main.path(forResource: "Info", ofType: "plist") {
            let dictRoot = NSDictionary(contentsOfFile: path)
            if let dict = dictRoot {
                urlString = dict["URL"] as! String
            }
        }
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        // getting image object details
        self.navigationItem.title = "Gallery"
        let flag = reachability.connectedToNetwork();
        if flag{
            let url = URL(string:urlString + "lectureimages" + "/"+lecEmail )
            
            let task = URLSession.shared.dataTask(with: url!){ (data, response, error) in
                if error != nil
                {
                    print("ERROR",error ?? "There is an error")
                }
                else
                {
                    if let content = data
                    {
                        do{
                            
                            let allImagesData = try Data(contentsOf: url!)
                            let allImages = try JSONSerialization.jsonObject(with: allImagesData, options: JSONSerialization.ReadingOptions.allowFragments) as! [String : AnyObject]
                            
                            if let imageFromJSON = allImages["imagesarray"] as? NSArray{
                                if(imageFromJSON.count != 0){
                                    for index in 0...imageFromJSON.count-1 {
                                        
                                        let aObject = imageFromJSON[index] as! [String : AnyObject]
                                        let imageObject = Image()
                                        imageObject.title = aObject["title"] as! String
                                        imageObject.id =  aObject["id"] as! String
                                        imageObject.imageUrl = aObject["imageUrl"] as! String
                                        imageObject.order = aObject["order"] as! Double
                                        self.names.append(imageObject.title)
                                        self.imageList[imageObject.title] = imageObject
                                    }
                                }
                            }
                            let sortedArray = self.imageList.sorted { $0.value.order < $1.value.order }
                            self.names = sortedArray.map {$0.0 }
                            self.galleryTableView.reloadData()
                        }
                        catch let error{
                            print(error)
                        }
                    }
                }
            }
            task.resume()
        }
        else{
            
            let alert = UIAlertController(title: "No Internet", message: "Please try again later with internet connection", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Close", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
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
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return self.names.count
    }
    
    // Setting the data to view
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = galleryTableView.dequeueReusableCell(withIdentifier: "LecturerimageCell", for: indexPath)as! LecturerGalleryTableViewCell
        
        
        cell.textlabel?.text = self.names[indexPath.row]
        
        let title = self.names[(indexPath.row)]
        let imageObject = imageList[title]! as Image
        
        // Getting image data from dropbox URL
        if (imageObject.imageUrl != nil)
        {

            let image:URL = URL(string: imageObject.imageUrl)!
            
            let imageData:NSData = NSData(contentsOf: image)!
            let image1 = UIImage(data: imageData as Data)
            
            // setting image data to view
            cell.imageview.image = image1
            cell.imageview?.contentMode = .scaleAspectFit
        }
        return cell
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat
    {
        return 484.0;
    }
}

