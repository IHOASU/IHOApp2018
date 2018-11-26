/******************************************************************************************************************
 # Name of Program  :   NewsViewController.swift
 #
 # Description      :   News page
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
import CoreData

// News controller
class NewsViewController: UITableViewController {
   
    @IBOutlet var newsTableView: UITableView!
    var urlString:String = ""
    var newsList:[String : News] = [String : News]()
    var names:[String]=[String]()
    var newsId:[String]=[String]()
    var reachability: Reachability = Reachability();
    let dispatch_group = DispatchGroup()
    let df = DateFormatter()
    
    func loadNewsList(){
        
        let url = URL(string:urlString + "newsobjects" )
        
        // Getting data from Global DB
        var task = URLSession.shared.dataTask(with: url!){ (data, response, error) in
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
                       
                        if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                            for news in newsFromJSON{
                                let newsObject = News()
                                if let title = news["title"] as? String,let desc = news["desc"] as? String,let id = news["id"] as? String,let image = news["image"] as? String, let link = news["link"] as? String, let date = news["date"] as? String{
                                    newsObject.id = id
                                    newsObject.title = title
                                    newsObject.desc = desc
                                    newsObject.image = image
                                    newsObject.link = link
                                    newsObject.newsDate = self.df.date(from: date)!
                                    self.names.append(newsObject.title)
                                }
                                self.newsList[newsObject.title] = newsObject
                            }
                        }
                        
                        let sortedArray = self.newsList.sorted { $0.value.newsDate >  $1.value.newsDate }
                        self.names = sortedArray.map {$0.0 }
                        
                        mainInstance.newsList = self.newsList;
                        mainInstance.names = self.names;
                        self.newsTableView.reloadData()
                    }
                    catch let error{
                        print(error)
                    }
                }
            }
        }
        task.resume()
    }
    
    // Getting and laoding News IDs
    func loadNewsId(){
        let urlId = URL(string:urlString + "newsid" )
      
        dispatch_group.enter()
        var taskUrlId = URLSession.shared.dataTask(with: urlId!){ (data, response, error) in
            if error != nil
            {
                print("ERROR",error ?? "There is an error")
            }
            else
            {                self.newsId = [];
                
                if let content = data
                {
                    do{
                        //Array
                        let myJSON = try JSONSerialization.jsonObject(with: content, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        
                        if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                            for news in newsFromJSON{
                                if let newsId = news["id"] as? String{
                                    self.newsId.append(newsId)
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
        self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        
        self.navigationItem.title = "News"
        
        //toolbar
        let label = UILabel(frame: CGRect(x: CGFloat(0), y: CGFloat(0), width: CGFloat(350), height: CGFloat(21)))
        label.text = "ASU IHO 2018"
        label.center = CGPoint(x: view.frame.midX, y: view.frame.height)
        label.textAlignment = NSTextAlignment.center
        label.textColor = UIColor.white
        let toolbarTitle = UIBarButtonItem(customView: label)
        let flexible = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        self.toolbarItems = [flexible,toolbarTitle]
        
        df.dateFormat = "MM-dd-yyyy"
        

        let flag = reachability.connectedToNetwork();
        if flag
        {
            if (!mainInstance.newsList.isEmpty && !mainInstance.names.isEmpty){
                
                loadNewsId();
                dispatch_group.wait()
                
                if(mainInstance.newsId == self.newsId){
                
                self.newsList = mainInstance.newsList;
                
                self.names = mainInstance.names;
                
                self.newsTableView.reloadData();
                }
                else{
                    mainInstance.clearNewsCache()
                    loadNewsList();
                    loadNewsId();
                    dispatch_group.wait()
                    mainInstance.newsId = self.newsId
                
                }
                
            }else{
                
                mainInstance.clearNewsCache()
                loadNewsList();
                loadNewsId();
                dispatch_group.wait()
                mainInstance.newsId = self.newsId
                
            }
            
        }else{
            
            if (!mainInstance.newsList.isEmpty && !mainInstance.names.isEmpty){
                
                self.newsList = mainInstance.newsList;
                
                self.names = mainInstance.names;
                
                self.newsTableView.reloadData();
                
            }else{
                do {
                    
                    if let file = Bundle.main.url(forResource: "news", withExtension: "json") {
                        
                        let data = try Data(contentsOf: file)
                        let myJSON = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as AnyObject
                        
                        if let newsFromJSON = myJSON as? [[String: AnyObject]]{
                            
                            for news in newsFromJSON{
                                
                                let newsObject = News()
                                
                                if let title = news["title"] as? String,let desc = news["desc"] as? String,let id = news["id"] as? String,let image = news["image"] as? String, let link = news["link"] as? String, let date = news["date"] as? String{
                                    newsObject.id = id
                                    newsObject.title = title
                                    newsObject.desc = desc
                                    newsObject.image = image
                                    newsObject.link = link
                                    newsObject.newsDate = self.df.date(from: date)!
                                    self.names.append(newsObject.title)
                                }
                                self.newsList[newsObject.title] = newsObject
                            }
                        } else {
                            print("JSON is invalid")
                        }
                    } else {
                        print("no file")
                    }
                    let sortedArray = self.newsList.sorted { $0.value.newsDate < $1.value.newsDate }
                    self.names = sortedArray.map {$0.0 }
                    mainInstance.newsList = self.newsList;
                    mainInstance.names = self.names;
                    self.newsTableView.reloadData()
                    
                } catch {
                  print(error.localizedDescription)
                }
            }
        }
        self.newsTableView.reloadData()
        
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
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = newsTableView.dequeueReusableCell(withIdentifier: "News Cell", for: indexPath)
        if(self.names != nil){
        cell.textLabel?.text = self.names[indexPath.row]
        }
        return cell
    }
 
    // Connecting to NewsDetailViewController
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        NSLog("seque identifier is \(segue.identifier)")
        if segue.identifier == "NewsDetail" {
            let viewController:NewsDetailViewController = segue.destination as! NewsDetailViewController
            let indexPath = self.tableView.indexPathForSelectedRow!
            let title = self.names[(indexPath.row)]
            let newsObjectToBeSend = newsList[title]! as News
            viewController.newsTitle = title
            viewController.newsDesc = newsObjectToBeSend.desc
            viewController.newsId = newsObjectToBeSend.id
            viewController.newsImage = newsObjectToBeSend.image
            viewController.newsLink = newsObjectToBeSend.link
        }
    }

}
