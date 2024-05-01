package com.green.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.dto.CommentDto;
import com.green.service.CommentService;

// 댓글 조회, 댓글 추가, 수정, 삭제
@RestController
public class CommentApiController {
	
	@Autowired
	private CommentService commentService;
	
	// 1. 댓글 조회(GET)
	@GetMapping("/api/articles/{articleId}/comments")
	public ResponseEntity< List<CommentDto> > comments(
		  @PathVariable	Long articleId) {
			
		// 정보조회를 서비스에게 위임 
		List<CommentDto> dtos = commentService.comments(articleId);
		
		// ResponseEntity : staus.ok + dtos(arraylist 
		 // -> json 으로 출력(이유: @RestController 라서 ))를 리턴
		return ResponseEntity.status(HttpStatus.OK).body( dtos );		
	}
	
	// 2. 댓글 생성(POST)
	@PostMapping("/api/articles/{articleId}/comments")	
	public ResponseEntity<CommentDto> create(
		  @PathVariable	Long        articleId,   // {articleId}  : 게시글번호
		  @RequestBody  CommentDto   dto	         // 입력된 자료들 input, select
		  ) {
		CommentDto createdDto  =  commentService.create(articleId, dto);
		// 결과 응답
		return ResponseEntity.status(HttpStatus.OK).body( dto );
	}
	
	
	// 3. 댓글 수정(Patch)
	//@PatchMapping("/api/comments/{id}")
	
	// 4. 댓글 삭제(Delete)
	//@DeleteMapping("/api/comments/{id}")
}






