package com.v2tech.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
@NodeEntity
public class Order {
	@GraphId
	Long id;
	
	Float price;
	
	String serviceType;
	
	String packageDetails;
	
	boolean templateUseOnly;
	
	boolean expressService;
	
	boolean superExpressService;
	
	boolean eachAdditionIteration;
	
	boolean coverLetter;
	
	Float templateUseOnlyAmount;
	
	Float expressServiceAmount;
	
	Float superExpressServiceAmount;
	
	Float eachAdditionIterationAmount;
	
	Float coverLetterAmount;
	
	Float totalPrice;
	
	String createDate = "";
	
	boolean paymentConfirmed = false;
	
	String orderId = "";
	
	String user = "";
	
	String socialMedia = "";

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public boolean isTemplateUseOnly() {
		return templateUseOnly;
	}

	public void setTemplateUseOnly(boolean templateUseOnly) {
		this.templateUseOnly = templateUseOnly;
	}

	public boolean isExpressService() {
		return expressService;
	}

	public void setExpressService(boolean expressService) {
		this.expressService = expressService;
	}

	public boolean isSuperExpressService() {
		return superExpressService;
	}

	public void setSuperExpressService(boolean superExpressService) {
		this.superExpressService = superExpressService;
	}

	public boolean isEachAdditionIteration() {
		return eachAdditionIteration;
	}

	public void setEachAdditionIteration(boolean eachAdditionIteration) {
		this.eachAdditionIteration = eachAdditionIteration;
	}

	public boolean isCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(boolean coverLetter) {
		this.coverLetter = coverLetter;
	}

	public Float getTemplateUseOnlyAmount() {
		return templateUseOnlyAmount;
	}

	public void setTemplateUseOnlyAmount(Float templateUseOnlyAmount) {
		this.templateUseOnlyAmount = templateUseOnlyAmount;
	}

	public Float getExpressServiceAmount() {
		return expressServiceAmount;
	}

	public void setExpressServiceAmount(Float expressServiceAmount) {
		this.expressServiceAmount = expressServiceAmount;
	}

	public Float getSuperExpressServiceAmount() {
		return superExpressServiceAmount;
	}

	public void setSuperExpressServiceAmount(Float superExpressServiceAmount) {
		this.superExpressServiceAmount = superExpressServiceAmount;
	}

	public Float getEachAdditionIterationAmount() {
		return eachAdditionIterationAmount;
	}

	public void setEachAdditionIterationAmount(Float eachAdditionIterationAmount) {
		this.eachAdditionIterationAmount = eachAdditionIterationAmount;
	}

	public Float getCoverLetterAmount() {
		return coverLetterAmount;
	}

	public void setCoverLetterAmount(Float coverLetterAmount) {
		this.coverLetterAmount = coverLetterAmount;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getPackageDetails() {
		return packageDetails;
	}

	public void setPackageDetails(String packageDetails) {
		this.packageDetails = packageDetails;
	}

	public boolean isPaymentConfirmed() {
		return paymentConfirmed;
	}

	public void setPaymentConfirmed(boolean paymentConfirmed) {
		this.paymentConfirmed = paymentConfirmed;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSocialMedia() {
		return socialMedia;
	}

	public void setSocialMedia(String socialMedia) {
		this.socialMedia = socialMedia;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
