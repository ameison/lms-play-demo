package forms;

import models.Event;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Lang;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import util.Util;

import javax.inject.Inject;
import java.util.Date;

public class EventForm {

    public Long id;

    private MessagesApi messagesApi;

    @Inject
    public EventForm(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Required
    @MinLength(value = 4)
    public String name;

    @Required
    @MinLength(value = 10)
    public String description;

    public String address;

    public String urlImage;

    public String startDate;

    public String endDate;

    public Date startDateP;
    public Date endDateP;

    public String validate() {
        if (name.trim().isEmpty()) {
            return messagesApi.get(Lang.forCode("es"), "admin.course.name");
        }else if(description.trim().isEmpty()){
            return messagesApi.get(Lang.forCode("es"), "admin.course.description");
        }
        return null;
    }

    public EventForm(Event event){
        this.id = event.id;
        this.name = event.name;
        this.description = event.description;
        this.address = event.address;
        this.urlImage = event.urlImage;
        if (event.startDate != null)
            this.startDate = Util.parseDateToString(event.startDate);
        if (event.endDate != null)
            this.endDate = Util.parseDateToString(event.endDate);
        if (event.startDate != null)
            this.startDateP = event.startDate;
        if (event.endDate != null)
            this.endDateP = event.endDate;
    }

    public EventForm(){}

}