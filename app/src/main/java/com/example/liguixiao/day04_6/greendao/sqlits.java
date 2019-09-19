package com.example.liguixiao.day04_6.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liguixiao on 2019/9/15.
 */
@Entity
public class sqlits {

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String pic;
    @Generated(hash = 316350833)
    public sqlits(Long id, @NotNull String title, @NotNull String pic) {
        this.id = id;
        this.title = title;
        this.pic = pic;
    }
    @Generated(hash = 1942792590)
    public sqlits() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPic() {
        return this.pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
}
