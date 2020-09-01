package com.httpclient.imgprovpg.hikvision.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="v_port_plate_photo")
public class VPortPlatePhoto implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @Column(name="photo_id")
    private long photoId;//not null 图片唯一ID
    @Column(name="plate_id")
    private long plateId;//not null图片所属拖车ID
    @Column(name="photo_url")
    private String photoUrl;//图片url(http://ip:port/url)
    @Column(name="ctime")
    private Timestamp ctime;//创建时间

}
