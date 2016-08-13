package com.v2tech.domain;

public enum KeywordEntity
	{
	BOOKS("BOOKS"), COACHING_CLASSES("COACHING_CLASSES"), DIGITAL_RESOURCES("DIGITAL_RESOURCES");

		String entity;

		public String getEntity()
			{
				return entity;
			}

		KeywordEntity(String entity)
			{
				this.entity = entity;
			}

	}
