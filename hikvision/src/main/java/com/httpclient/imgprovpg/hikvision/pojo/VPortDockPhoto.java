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
@Table(name="v_port_photoinfo")
public class VPortDockPhoto implements Serializable {
    private static final long serialVersionUID = 4L;

    @Id
    @Column(name="photo_id")
    private long photoId;//not null 图片唯一ID
    @Column(name="dock_id")
    private long dockId;//not null 图片所属集装箱ID
    @Column(name="photo_url")
    private String photoUrl;//图片url(http://ip:port/url)
    @Column(name="ctime")
    private Timestamp ctime;//创建时间
    @Column(name="photo_name")
    private String photoName;//图片名称

    /**
     * 集装箱识别部位：
     * 0-1-路侧左，2-路侧右，
     * 3-海侧左，4-海侧右，
     * 5-小侧面左，6-小侧面右，
     * 7-拖车号左，8-拖车号右，
     * 9-箱顶，10-箱底
     * */
    @Column(name="photo_pos")
    private long photoPos;

    /**
     * 图片对应集装箱号：
     * 0-1-第一个箱号，2-第二个箱号
     */
    @Column(name="cont_order")
    private long contOrder;

    @Column(name="data_delay")
    private long dataDelay;//数据延时
    @Column(name="plcdata_boxheight")
    private long plcdataBoxheight;//PLC数据箱体高度
    @Column(name="plcdata_boxdisplacement")
    private long plcdataBoxdisplacement;//PLC数据箱体位移
}
