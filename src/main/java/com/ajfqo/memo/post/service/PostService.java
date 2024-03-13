package com.ajfqo.memo.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ajfqo.memo.common.FileManager;
import com.ajfqo.memo.post.domain.Post;
import com.ajfqo.memo.post.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public Post addPost(int userId, String title, String contents, MultipartFile file) {
		
		// file을 특정 경로에 저장한다.
		// 클라이언트에서 접근가능한 url 정보를 얻는다.
		String imagePath = FileManager.saveFile(userId, file);
		
		Post post = Post.builder()
					.userId(userId)
					.title(title)
					.contents(contents)
					.imagePath(imagePath)
					.build();
		
		return postRepository.save(post);
	}
	
	// 특정 사용자의 메모 목록을 돌려준다
	public List<Post> getPostList(int userId) {
		return postRepository.findByUserIdOrderByIdDesc(userId);
	}
	
	public Post getPost(int id) {
		Optional<Post> optionalPost =  postRepository.findById(id);
		Post post = optionalPost.orElse(null);
		return post;
	}
	
	
}
