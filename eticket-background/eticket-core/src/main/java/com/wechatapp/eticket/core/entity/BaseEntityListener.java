package com.wechatapp.eticket.core.entity;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class BaseEntityListener {
	
	@PrePersist
	public void prePersist(BaseEntity baseEntity) {
		baseEntity.setCreatedTime(new Date());
	}

	@PreUpdate
	public void preUpdate(BaseEntity baseEntity) {
		baseEntity.setUpdatedTime(new Date());
	}

}
/**
 * @Prepersist：完成save之前的操作
 * @Preupdate：完成update之前的操作
 * @Postpersist：完成save之后的操作
 * @Postupdate：完成update之后的操作
 * 在方法中利用反射机制，可以实现对（创建日期，创建者，更新日期更新者，删除日期，删除者）等注解的字段的赋值操作
 */