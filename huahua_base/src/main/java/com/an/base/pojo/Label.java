package com.an.base.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_label")
@Data
public class Label implements Serializable {

    private static final long serialVersionUID = 661815299143275186L;

     @Id
    private String id;
    private String labelname;
    private String state;
    private Long  count;
    private Long fans;
    private String recommend;





}