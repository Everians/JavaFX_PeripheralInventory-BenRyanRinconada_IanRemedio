/*
 *ITP121 INTEGRATIVE PROGRAMMING & TECHNOLOGIES Final / Project. 
 *By: Ben Ryan Rinconada And Ian Remedio
 */
package javafxperipheralinventorysystem;

/**
 *
 * @author benry
 */
public class peripheralData {
    private Integer pid;
    private String brand;
    private String model;
    private String type;
    private String availability;
    private String image;
    
    public peripheralData(Integer pid, String brand, String model, String type, String availability, String image){
        this.pid = pid;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.availability = availability;
        this.image = image;
        
    }
    public Integer getPid(){
        return pid;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public String getType(){
        return type;
    }
    public String getAvailability(){
        return availability;
    }
     public String getImage(){
        return image;
    }
    
}
