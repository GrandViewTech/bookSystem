package com.v2tech.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Keyword {
	@GraphId
	private Long id;
	
	@Fetch
	@NotNull
    @Indexed(unique=true, failOnDuplicate = true)
	String text = "";
	
	public Keyword(String txt){
		this.text = txt;
	}
	
	String keywordType = KeywordType.AUTHOR.toString();
	
	String	keywordEntity	= KeywordEntity.BOOKS.getEntity();
	public Keyword(){
		
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}



	public String getKeywordType() {
		return keywordType;
	}

		public void setKeywordType(String keywordType)
			{
				this.keywordType = keywordType;
			}

		@Override
		public int hashCode()
			{
				return this.text.hashCode();
			}

		public String getKeywordEntity()
			{
				return keywordEntity;
			}

		public void setKeywordEntity(String keywordEntity)
			{
				this.keywordEntity = keywordEntity;
			}

	}
