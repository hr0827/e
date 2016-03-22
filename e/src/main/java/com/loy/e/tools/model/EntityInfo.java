package com.loy.e.tools.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.LoyColumn;
import com.loy.e.core.annotation.LoyEntity;
import com.loy.e.tools.component.AbstractInput;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class EntityInfo {

	public static final String  QL = "ql";
	public static final String  REPOSITOR = "repository";
	public static final String  SERVICE_IMPL = "service.impl";
	public static final String  DOMAIN = "domain";
	public String modelPackageName;
	public String qlPackageName;
	public String repositoryPackageName;
	public String serviceImplPackageName;
	public String domainPackageName;
	public String name;
	public String entityName;
	public String modelName;
	public String left="<";
	
	public EntityInfo(Class clazz){
		build(clazz);
	}
	
	List<ColumnInfo> listColumns = new ArrayList<ColumnInfo>();
	List<AbstractInput> editColumns = new ArrayList<AbstractInput>();
	List<ColumnInfo> detailColumns = new ArrayList<ColumnInfo>();
	List<ColumnInfo> conditionColumns = new ArrayList<ColumnInfo>();
	
	
	public void build(Class clazz){
		String packageName = clazz.getPackage().getName();
		String simpleName = clazz.getSimpleName();
		this.entityName = simpleName;
		this.modelPackageName = packageName.replaceFirst(".domain.entity", "");
		int index = this.modelPackageName.lastIndexOf(".")+1;
		this.modelName = this.modelPackageName.substring(index, this.modelPackageName.length());
		LoyEntity loyEntity = AnnotationUtils.findAnnotation(clazz, LoyEntity.class);
		if(loyEntity != null){
			this.name = loyEntity.name();
		}
		Field[] fields = clazz.getDeclaredFields(); 
		for(int i=0;i<fields.length;i++) { 
			LoyColumn loyColumn = AnnotationUtils.findAnnotation(fields[i], LoyColumn.class);
			if(loyColumn != null){
				ColumnInfo columnInfo = new ColumnInfo(loyColumn);
				String fieldName = fields[i].getName();
				columnInfo.setFieldName(fieldName);
				if(loyColumn.list()){
					this.listColumns.add(columnInfo);
				}
				if(loyColumn.edit()){
					AbstractInput abstractInput = AbstractInput.newInput(loyColumn.inputType());
					abstractInput.setFieldName(columnInfo.getFieldName());
					abstractInput.setLabelName(columnInfo.getName());
					this.editColumns.add(abstractInput);
				}
				if(loyColumn.detail()){
					this.detailColumns.add(columnInfo);
				}
				ConditionParam conditionParam = loyColumn.condition();
				if(conditionParam != null){
					this.conditionColumns.add(columnInfo);
				}
			}
	    } 
	}

	public String getModelPackageName() {
		return modelPackageName;
	}

	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}

	public String getQlPackageName() {
		return this.modelPackageName +"."+QL;
	}

	public void setQlPackageName(String qlPackageName) {
		this.qlPackageName = qlPackageName;
	}

	public String getRepositoryPackageName() {
		return this.modelPackageName +"."+REPOSITOR;
	}

	public void setRepositoryPackageName(String repositoryPackageName) {
		this.repositoryPackageName = repositoryPackageName;
	}

	public String getServiceImplPackageName() {
		return this.modelPackageName +"."+SERVICE_IMPL;
	}

	public void setServiceImplPackageName(String serviceImplPackageName) {
		this.serviceImplPackageName = serviceImplPackageName;
	}

	public String getDomainPackageName() {
		return this.modelPackageName +"."+DOMAIN;
	}

	public void setDomainPackageName(String domainPackageName) {
		this.domainPackageName = domainPackageName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public List<ColumnInfo> getListColumns() {
		return listColumns;
	}

	public void setListColumns(List<ColumnInfo> listColumns) {
		this.listColumns = listColumns;
	}

	public List<AbstractInput> getEditColumns() {
		return editColumns;
	}

	public void setEditColumns(List<AbstractInput> editColumns) {
		this.editColumns = editColumns;
	}

	public List<ColumnInfo> getDetailColumns() {
		return detailColumns;
	}

	public void setDetailColumns(List<ColumnInfo> detailColumns) {
		this.detailColumns = detailColumns;
	}

	public List<ColumnInfo> getConditionColumns() {
		return conditionColumns;
	}

	public void setConditionColumns(List<ColumnInfo> conditionColumns) {
		this.conditionColumns = conditionColumns;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}
	
}
