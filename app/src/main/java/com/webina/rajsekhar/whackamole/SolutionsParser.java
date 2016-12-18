package com.webina.rajsekhar.whackamole;

/**
 * Created by RajSekhar on 12/11/2016.
 */
public class SolutionsParser {
    String name;
    String title;
    String body;
    String _id;

    public SolutionsParser(String name, String title,String body,String _id){
        this.setBody(body);
        this.setName(name);
        this.setTitle(title);
        this.set_id(_id);
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
