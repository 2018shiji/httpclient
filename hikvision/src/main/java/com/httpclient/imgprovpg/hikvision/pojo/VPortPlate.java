package com.httpclient.imgprovpg.hikvision.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name="v_port_plate")
public class VPortPlate implements Serializable {
    private static final long serialVersionUID =2L;

    @Id
    @Column(name="plate_id")
    private long plateId;//not null 数据ID
    @Column(name="plate_no")
    private String plateNo;//车号
    @Column(name="trvalcrane_id")
    private long trvalcraneId;//桥吊id
    @Column(name="lane_no")
    private long lane_no;//车道号
    @Column(name="ctime")
    private Timestamp ctime;//创建时间
    @Column(name="mtime")
    private Timestamp mtime;//更新时间

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "plate_id")
    private List<VPortPlatePhoto> photos;
}
