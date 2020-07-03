package auto.cn.imoocfestivalstudy.beans;

public class MsgBean {
    private int id;
    private int fesId;
    private String content;

    public MsgBean(int id, int fesId, String content) {
        this.id = id;
        this.fesId = fesId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFesId() {
        return fesId;
    }

    public void setFesId(int fesId) {
        this.fesId = fesId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
