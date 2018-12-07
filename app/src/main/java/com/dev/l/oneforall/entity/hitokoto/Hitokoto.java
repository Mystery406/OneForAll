package com.dev.l.oneforall.entity.hitokoto;

public class Hitokoto {
    //    {
//        "id": 179,
//            "hitokoto": "我在未来等你。",
//            "type": "a",
//            "from": "穿越时空的少女",
//            "creator": "库腐腐",
//            "created_at": "1468605911"
//    }
    private String id;
    private String hitokoto;
    private String type;
    private String from;
    private String creator;
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
