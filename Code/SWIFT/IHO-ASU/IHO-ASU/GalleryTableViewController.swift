/******************************************************************************************************************
 # Name of Program  :   GalleryTableViewController.swift
 #
 # Description      :   Gallery page
 #
 # Created By       :   Masters SE Team (app version 2)
 #
 # Created On       :   29 March 2017
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

class GalleryTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var imageview: UIImageView!
    
    @IBOutlet weak var textlabel: UITextView!
}



class GalleryTableViewController: UITableViewController {
    @IBOutlet var galleryTableView: UITableView!
    
    var urlString:String = ""
    var imageList:[String : Image] = [String : Image]()
    var names:[String]=[String]()
    var imageId:[String]=[String]()
    var reachability: Reachability = Reachability();
    let dispatch_group = DispatchGroup()
    

    func loadImageList(){
        
                let url = URL(string:urlString + "galleryobjects" )
        
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
                        //Array
                        let myJSON = try JSONSerialization.jsonObject(with: content, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        if let imageFromJSON = myJSON as? [[String: AnyObject]]{
                            for imageO in imageFromJSON{
                                let imageObject = Image()
                                if let title = imageO["title"] as? String,let id = imageO["id"] as? String,let imageUrl = imageO["imageUrl"] as? String, let order = imageO["order"] as? Double{
                                    imageObject.id = id
                                    imageObject.title = title
                                    imageObject.imageUrl = imageUrl
                                    imageObject.order = order
                                    self.names.append(imageObject.title)
                                }
                                self.imageList[imageObject.title] = imageObject
                                
                            }
                        }
                        
                        let sortedArray = self.imageList.sorted { $0.value.order < $1.value.order }
                        self.names = sortedArray.map {$0.0 }
                        mainInstance.imageList = self.imageList;
                        mainInstance.imageNames = self.names;
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
    
    func loadImageId(){
        
        let urlId = URL(string:urlString + "galleryid" )
        
        dispatch_group.enter()
        var taskUrlId = URLSession.shared.dataTask(with: urlId!){ (data, response, error) in
            if error != nil
            {
                print("ERROR",error ?? "There is an error")
            }
            else
            {
                
                self.imageId = [];
                
                if let content = data
                {
                    do{
                        //Array
                        let myJSON = try JSONSerialization.jsonObject(with: content, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        
                        if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                            for news in newsFromJSON{
                                if let newsId = news["id"] as? String{
                                    self.imageId.append(newsId)
                                }
                            }
                        }
                    }
                    catch let error{
                        print(error)
                    }
                }
                self.dispatch_group.leave()
            }
        }
        taskUrlId.resume()
    }
    
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let path = Bundle.main.path(forResource: "Info", ofType: "plist") {
            let dictRoot = NSDictionary(contentsOfFile: path)
            if let dict = dictRoot {
                urlString = dict["URL"] as! String
            }
        }
        
        self.galleryTableView.backgroundColor = UIColor(red: CGFloat((233 / 255.0)), green: CGFloat((233 / 255.0)), blue: CGFloat((233 / 255.0)), alpha: CGFloat(1))
        
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "Gallery"
        
        //toolbar
        let label = UILabel(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(350), height: CGFloat(21)))
        label.text = "ASU IHO 2018"
        label.center = CGPoint(x: view.frame.midX, y: view.frame.height)
        label.textAlignment = NSTextAlignment.center
        label.textColor = UIColor.white
        let toolbarTitle = UIBarButtonItem(customView: label)
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        self.toolbarItems = [flexible,toolbarTitle]
        
        //self.view.backgroundColor = UIColor(red: CGFloat((233 / 255.0)), green: CGFloat((233 / 255.0)), blue: CGFloat((233 / 255.0)), alpha: CGFloat(1))
        
        let flag = reachability.connectedToNetwork();
        if flag
        {
            
            if (!mainInstance.imageList.isEmpty && !mainInstance.imageNames.isEmpty){
                
                loadImageId();
                dispatch_group.wait()
                
                if(mainInstance.imageId == self.imageId){
                    self.imageList = mainInstance.imageList;
                    
                    self.names = mainInstance.imageNames;
                    
                    self.galleryTableView.reloadData();
                }
                else{
                    
                    mainInstance.clearImageCache()
                    loadImageList();
                    loadImageId();
                    dispatch_group.wait()
                    mainInstance.imageId = self.imageId
                    
                }
                
            }else{
                
                mainInstance.clearImageCache()
                loadImageList();
                loadImageId();
                dispatch_group.wait()
                mainInstance.imageId = self.imageId
                
            }
            
        }else{
            
            if (!mainInstance.imageList.isEmpty && !mainInstance.imageNames.isEmpty){
                
                self.imageList = mainInstance.imageList;
                self.names = mainInstance.imageNames;
                self.galleryTableView.reloadData();
                
            }else{
                
                do{
                    if let file = Bundle.main.url(forResource: "gallery", withExtension: "json") {
                    let data = try Data(contentsOf: file)
                        
                        let myJSON = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        if let imageFromJSON = myJSON as? [[String: AnyObject]]{
                            for imageO in imageFromJSON{
                                let imageObject = Image()
                                if let title = imageO["title"] as? String,let id = imageO["id"] as? String,let imageUrl = imageO["imageUrl"] as? String,let order = imageO["order"] as? Double{
                                    imageObject.id = id
                                    imageObject.title = title
                                    imageObject.imageUrl = imageUrl
                                    imageObject.order = order
                                    self.names.append(imageObject.title)
                                }
                                self.imageList[imageObject.title] = imageObject
                            }
                        }
                        let sortedArray = self.imageList.sorted { $0.value.order < $1.value.order }
                        self.names = sortedArray.map {$0.0 }
                    }
                    else{
                        print("no file")
                    }
                    mainInstance.imageList = self.imageList;
                    mainInstance.imageNames = self.names;
                    self.galleryTableView.reloadData()
                    
                } catch {
                    print(error.localizedDescription)
                }
            }
        }
        
        self.galleryTableView.reloadData()
        
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
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.names.count
    }
    
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = galleryTableView.dequeueReusableCell(withIdentifier: "imageCell", for: indexPath)as! GalleryTableViewCell
        
        cell.textlabel?.textColor = UIColor.black
        
        if(self.names != nil){
        cell.textlabel?.text = self.names[indexPath.row]
        }
        
        let title = self.names[(indexPath.row)]
        let imageObject = imageList[title]! as Image
        
        if (imageObject.imageUrl != nil)
        {
//            //base64 string to NSData
//            let decodedData = NSData(base64Encoded: imageObject.image, options: NSData.Base64DecodingOptions(rawValue: 0))

            //NSData to UIImage
//            cell.imageview?.image = UIImage(data: decodedData! as Data)
//            cell.imageview?.contentMode = .scaleAspectFit
            
            let image:URL = URL(string: imageObject.imageUrl)!

            print(image)

//            let imageData = NSData(contentsOf: image)
//            let imageinfo = UIImage(data: imageData! as Data)
//
//            print(imageinfo)
//
//            cell.imageview.image = imageinfo
//            cell.imageview?.contentMode = .scaleAspectFit
            
            // Start background thread so that image loading does not make app unresponsive
//            DispatchQueue.global(qos: .userInitiated).async {

                let imageData:NSData = NSData(contentsOf: image)!
                //let imageView = UIImageView(frame: CGRect(x:0, y:0, width:200, height:200))
                //imageView.center = self.view.center

                // When from background thread, UI needs to be updated on main_queue
//                DispatchQueue.main.async {
                    let image1 = UIImage(data: imageData as Data)
                    cell.imageview.image = image1
                    cell.imageview?.contentMode = .scaleAspectFit
//                }
//            }

        }
        return cell
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat
    {
        return 484.0;
    }
    
}

