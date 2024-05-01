package com.green.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.dto.CommentDto;
import com.green.entity.Article;
import com.green.entity.Comments;
import com.green.repository.ArticleRepository;
import com.green.repository.CommentRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {
	
	@Autowired
	private  CommentRepository  commentRepository;
	@Autowired
	private  ArticleRepository  articleRepository;
	
	// 1 . 댓글 조회
	public List<CommentDto> comments(Long articleId) {
		
		/*
		// 1. 댓글을 db 에서 articleId 로 조회 Entity 에 담는다
		List<Comments> commentList 
		  = commentRepository.findByArticleId(articleId);
				
		// 2. 엔티티 -> Dto 로 변환
		List<CommentDto> dtos = new ArrayList<CommentDto>();
		for (int i = 0; i < commentList.size(); i++) {
			Comments    c   = commentList.get(i);
			CommentDto  dto = CommentDto.createCommentDto(c);
			dtos.add(dto);
		}
		
		// 3.결과를 반환		
		return dtos;
		*/
		/*   ArrayList.stream()
		 *    .map( (comment) -> {   
		 *     // 집합.map() :stream 전용함수 
		 *     //  집합(Collection) 에 element들을 반복으로 조작
		 *     //  .map() vs  .forEach() - 둘다 배열을 모두 조작한다
		 *     //   .map() : 내부의 element 값이나 사이즈가 변경할때 사용 : 모두대문자로 변경
		 *     //   .forEach() : 내부의 element 값이나 변경되지 않을때 사용 : 모두대문자로 변경
		 *        CommentDto.createCommentDto(comment) 
		 *     } )
		 *    */
		return commentRepository.findByArticleId(articleId)
				.stream()   // stream 으로 전환
				.map(comment -> CommentDto.createCommentDto(comment))
				.collect(Collectors.toList());  // stream 다시 arraylist() 변경
	}

	@Transactional   // 오류발생시 db 롤백하기 위해 (throw  사용하는 이유)
	public CommentDto create(Long articleId, CommentDto dto) {
		// 1. 게시글 조회 및 조회실패 예외발생
		// 게시글에 존재하지 않는 articleId 가 넘어오면 조회결과가 없다 Throw
		Article article = articleRepository.findById(articleId)
				.orElseThrow( () -> new IllegalArgumentException(
					"댓글 생성 실패! 대상 게시물이 없습니다") ); // 조회와 예외처리 동시실시
		
		// 2. 댓글 엔티티 생성 -> 저장할 데이터(comments)를 만든다 
	    Comments comments = Comments.createComment(dto, article);
		
	    // 3. 댓글 엔티리를 db 에 저장
	    Comments  created  =  commentRepository.save(comments);
	    
		// 4. 저장된 Comments type created -> DTO 롤 변환 후 controller return	
	    // 변환이유가 controller 에서 entity type 을 사용하지 않기 위해
		return CommentDto.createCommentDto( created );
	}         
		
}










