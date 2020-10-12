package com.httpclient.imgprovpg.hikvision.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@ToString
@Table(name="v_port_cont_dock")
public class VPortDock implements Serializable {
    private static final Long serialVersionUID = 5L;

    @Id
    @Column(name="dock_id")
    private Long dockId;//not null 数据ID
    @Column(name="container_no")
    private String containerNo;//箱号
    @Column(name="container_shape")
    private String containerShape;//箱型
    @Column(name="container_size")
    private Integer containerSize;//尺寸，单位尺
    @Column(name="plate_no")
    private String plateNo;//车号
    @Column(name="ctype")
    private Integer ctype;//单双箱模式，0：单箱，1：双箱
    @Column(name="cweight")
    private float cweight;//重量，吨
    @Column(name="dock_status")
    private Integer dockStatus;//装卸状态，0：装船，1：卸船
    @Column(name="container_pos")
    private String containerPos;//箱位
    @Column(name="carcont_pos")
    private String carcontPos;//箱子在拖车上的位置，F：前，A：后，M：中
    @Column(name="cstatus")
    private Long cstatus;//录入理货系统状态，-1,作废 0,初始状态 1,录入理货系统成功 2,录入理货系统失败
    @Column(name="confidence")
    private Integer confidence;//置信度
    @Column(name="cnam")
    private String cname;//中文船名
    @Column(name="enam")
    private String ename;//英文船名
    @Column(name="ship_code")
    private String shipCode;//船名代码
    @Column(name="i_voyage")
    private String iVoyage;//进口航次
    @Column(name="e_voyage")
    private String eVoyage;//出口航次
    @Column(name="trvalcrane_id")
    private Long trvalcraneId;//桥吊id
    @Column(name="trval_no")
    private String trvalNo;//桥号
    @Column(name="driver_no")
    private String driverNo;//桥司机工号
    @Column(name="gangs")
    private String gangs;//工班
    @Column(name="container_dir")
    private String containerDir;//箱门方向
    @Column(name="stream_dir")
    private String streamDir;//流向
    @Column(name="loading_port")
    private String loadingPort;//装货港
    @Column(name="unloading_port")
    private String unloadingPort;//卸货港
    @Column(name="dest_port")
    private String destPort;//目的港
    @Column(name="damage_flag")
    private Integer damageFlag;//残损标记，0：未检验，1：残损，2：正常
    @Column(name="lane_no")
    private Integer laneNo;//车道号
    @Column(name="begin_time")
    private Timestamp begIntegerime;//开始时间
    @Column(name="end_time")
    private Timestamp end_time;//结束时间
    @Column(name="bay_horizontal")
    private String bay_horizontal;//水平坐标，x
    @Column(name="bay_vertical")
    private String bay_vertical;//垂直坐标，y
    @Column(name="bay")
    private String bay;//bay位
    @Column(name="user_id")
    private Long userId;//用户id
    @Column(name="is_compare")
    private Integer isCompare;//是否比对，0：否，1：是
    @Column(name="is_entry")
    private Integer isEntry;//是否录入理货系统，0：否，1：是
    @Column(name="cancel_flag")
    private Integer cancelFlag;//废弃数据，1：废弃
    @Column(name="ctime")
    private Timestamp ctime;//创建时间
    @Column(name="mtime")
    private Timestamp mtime;//更新时间
    @Column(name="pic_num")
    private Integer picNum;//图片张数
    @Column(name="msg_index")
    private Long msgIndex;//消息id，终端上传，递增单不唯一，第一次数据与第二次数据一致
    @Column(name="check_status")
    private String checkStatus;//比对状态
    @Column(name="check_ship_no")
    private String checkShipNo;//船艘编号，比对的室友由理货系统返回
    @Column(name="plc_open_time")
    private Timestamp plcOpenTime;//plc开锁时间
    @Column(name="plc_close_time")
    private Timestamp plcCloseTiem;//plc闭锁时间
    @Column(name="ident_start_time")
    private Timestamp identStartTime;//识别开始时间
    @Column(name="ident_end_time")
    private Timestamp identEndTime;//识别结束时间

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dock_id")
    private List<VPortDockPhoto> photos;

}
