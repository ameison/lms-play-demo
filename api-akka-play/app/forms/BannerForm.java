package forms;

import models.Banner;
public class BannerForm {

    public Long id;
    public String name;
    public String description;
    public String urlImage;
    public String validate() {
        return null;
    }

    public BannerForm(Banner banner){
        this.id = banner.id;
        this.name = banner.name;
        this.description = banner.description;
        this.urlImage = banner.urlImage;
    }

    public BannerForm(){}

}