package com.v2tech.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.v2tech.domain.Rating;
import com.v2tech.domain.User;
import com.v2tech.domain.util.BookResult;
import com.v2tech.domain.util.KeywordResult;

@Repository
public interface UserRepository extends GraphRepository<User>
	{
		
		@Query("MATCH (user:User) WHERE user.user =~ {0} return user;")
		public Set<User> findUserByUserName(String userName);

		@Query("MATCH (user:User) WHERE user.user =~ {0} AND user.socialMediaType={1} return user;")
		public Set<User> findUserByNameAndSocialMediaType(String user, String socialMediaType);

		@Query("MATCH (a)-[r:HAVE_RATED]->(b) WHERE a.user={0} RETURN r LIMIT 25")
		public Set<Rating> getRatingsByUser(String userName);

		@Query("MATCH (user:User)-[:IS_FRIEND_OF]->(friend), (friend)-[:HAVE_RATED]->(book:Book) WHERE user.user = {0} RETURN book, COUNT(book) as rated 	ORDER BY rated DESC")
		public List<BookResult> searchBooksRatedByFriends(String userName);

		@Query("MATCH (user:User)-[f:IS_FRIEND_OF]->(friend), (friend)-[s:HAVE_SEARCHED]->(key:Keyword)WHERE user.user = {0}  RETURN s.count as searchedCount, key.text as searched ORDER BY searchedCount DESC")
		public List<KeywordResult> getMostSearchedKeyword(String user);
		
		@Query("MATCH (user:User)-[f:IS_FRIEND_OF]->(friend), (friend)-[s:HAVE_SEARCHED]->(key:Keyword) WHERE user.user = {0} AND key.keywordEntity = {1} RETURN s.count as searchedCount, key.text as searched ORDER BY searchedCount DESC")
		public List<KeywordResult> getMostSearchedKeywordUsingEntityType(String user, String keywordEntity);
		
		@Query("MATCH (keyword:Keyword) WHERE key.keywordEntity = {0} RETURN s.count as searchedCount, key.text as searched ORDER BY searchedCount DESC ")
		public List<KeywordResult> getTopRatedKeyWordForAGivenEntity(String keywordEntity);
	}
