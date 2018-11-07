//
//  Science.swift
//  IHO-ASU
//
//  Created by Sweta Singhal on 3/30/17.
//  Copyright © 2017 Sweta Singhal. All rights reserved.
//

import Foundation

open class Science{
    var title: String
    var id: String
    var desc: String
    var link: String
    var scienceTimestamp: String
    
    init(title: String, id: String, desc: String, image: String, link: String, scienceTimestamp: String){
        self.title = title
        self.id = id
        self.desc = desc
        self.link = link
        self.scienceTimestamp = scienceTimestamp
    }
    init() {
        self.title = "unknown"
        self.id = "unknown"
        self.desc = "unknown"
        self.link = "unknown"
        self.scienceTimestamp = "0"
    }
    
    init (jsonStr: String){
        self.title = ""
        self.id = ""
        self.desc = ""
        self.link = ""
        self.scienceTimestamp = "0"
        if let data: Data = jsonStr.data(using: String.Encoding.utf8){
            do{
                let dict = try JSONSerialization.jsonObject(with: data, options:.mutableContainers) as?[String:AnyObject]
                self.title = (dict!["title"] as? String)!
                self.id = (dict!["id"] as? String)!
                self.desc = (dict!["desc"] as? String)!
                self.link = (dict!["link"] as? String)!
                self.scienceTimestamp = (dict!["link"] as? String)!
            }catch {
                print("unable to convert to dictionary")
            }
        }
    }
    
    init(dict: [String:AnyObject]) {
        self.title = dict["title"] as! String
        self.id = dict["id"] as! String
        self.desc = dict["desc"] as! String
        self.link = dict["link"] as! String
        self.scienceTimestamp = dict["timestamp"] as! String
    }
    
    func toJsonString () ->String{
        var jsonStr = "";
        let dict : [String:Any] = ["title": title,"id": id,"desc": desc, "link": link, "timestamp": scienceTimestamp]
        do {
            let jsonData = try JSONSerialization.data(withJSONObject: dict, options: JSONSerialization.WritingOptions.prettyPrinted)
            jsonStr = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue)! as String
        }catch let error as NSError{
            print (error)
        }
        return jsonStr
    }
    
}
