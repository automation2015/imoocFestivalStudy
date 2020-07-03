package auto.cn.imoocfestivalstudy.beans;

import java.util.Date;

public class FestivalBean {
    private int id;
    private String name;
    private String desc;
    private Date date;

    public FestivalBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
