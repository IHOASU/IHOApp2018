/******************************************************************************************************************
 # Name of Program  :   Lecturer.swift
 #
 # Description      :   Lecturer page
 #
 # Created By       :   Masters SE Team (app version 2)
 #
 # Created On       :   30 March 2017
 #
 # Version          :   1.0
 #*****************************************************************************************************************/

import Foundation

open class Lecturer{
    var title: String
    var name: String
    var id: String
    var bio: String
    var imageUrl: String
    var link: String
    var email: String
    var lecOrder: Double
    
    init(title: String, id: String,bio: String,imageUrl: String,link: String,name: String,email: String,lecOrder: Double){
        self.title = title
        self.id = id
        self.bio = bio
        self.imageUrl = imageUrl
        self.link = link
        self.name = name
        self.email = email
        self.lecOrder = 0
    }
    init() {
        self.title = "unknown"
        self.id = "unknown"
        self.bio = "unknown"
        self.imageUrl = "unknown"
        self.link = "unknown"
        self.name = "unknown"
        self.email = "unknown"
        self.lecOrder = 0
    }
    
    init (jsonStr: String){
        self.title = ""
        self.id = ""
        self.bio = ""
        self.imageUrl = ""
        self.link = ""
        self.name = ""
        self.email = ""
        self.lecOrder = 0
        if let data: Data = jsonStr.data(using: String.Encoding.utf8){
            do{
                let dict = try JSONSerialization.jsonObject(with: data, options:.mutableContainers) as?[String:AnyObject]
                self.title = (dict!["title"] as? String)!
                self.id = (dict!["id"] as? String)!
                self.bio = (dict!["bio"] as? String)!
                self.imageUrl = (dict!["imageUrl"] as? String)!
                self.link = (dict!["link"] as? String)!
                self.name = (dict!["name"] as? String)!
                self.email = (dict!["email"] as? String)!
            }catch {
                print("unable to convert to dictionary")
            }
        }
    }
    
    init(dict: [String:AnyObject]) {
        self.title = dict["title"] as! String
        self.id = dict["id"] as! String
        self.bio = dict["desc"] as! String
        self.imageUrl = dict["imageUrl"] as! String
        self.link = dict["link"] as! String
    self.name = dict["name"] as! String
        self.email = dict["email"] as! String
        self.lecOrder = dict["order"] as! Double
    }
    
    func toJsonString () ->String{
        var jsonStr = "";
        let dict = ["title": title,"id": id,"bio": bio, "imageUrl": imageUrl, "link": link, "name": name, "email": email]
        do {
            let jsonData = try JSONSerialization.data(withJSONObject: dict, options: JSONSerialization.WritingOptions.prettyPrinted)
            jsonStr = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue)! as String
        }catch let error as NSError{
            print (error)
        }
        return jsonStr
    }
    
}
