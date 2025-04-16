package entities;

import java.util.Date;

public class participation {

    private int id,event_id,user_id;
    private Date created_at;

    public participation(int id, int event_id, int user_id, Date created_at) {
        this.id = id;
        this.event_id = event_id;
        this.user_id = user_id;
        this.created_at = created_at;
    }

    public participation(int event_id, int user_id, Date created_at) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

}
