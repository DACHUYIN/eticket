package com.wechatapp.eticket.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "updated_time")
	private Date updatedTime;
}
/**
 * @MappedSuperclass
 * 表明这个类是一个父类，能够子类继承，且子类都会带有createdTime和updatedTime这两个字段
 * @EntityListeners
 * 监听一个类，这个类里面有两个set方法，表明何时对createdTime和updatedTime进行操作
 */