package com.an.spit.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class Spit implements Serializable {
    private static final long serialVersionUID = 2256612964083101630L;

    @Id
    private String _id;

    private String content;

    private Date publishtime;

    private String userid;

    private String nickname;

    private Integer vists;

    private Integer thumbup;

    private Integer share;

    private Integer comment;

    private String state;

    private String parentid;





}