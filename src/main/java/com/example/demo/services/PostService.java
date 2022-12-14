package com.example.demo.services;

import com.example.demo.dto.PostDTO;
import com.example.demo.exception.PostException;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostDTO findById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()){
            throw new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return postMapper.toDTO(post.get());

    }

    public ResponseEntity editPost(Long id, String title, String text){
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post with the indicated id not found");
        }
        post.get().setTitle(title);
        post.get().setPostText(text);
        postRepository.save(post.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post edited");

    }

    public ResponseEntity delete(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(!post.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post with the indicated id not found");
        }
        postRepository.delete(post.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post deleted");
    }

    public List<PostDTO> getAllPosts(){
        return postRepository.findAll().stream().map(postMapper::toDTO).collect(Collectors.toList());
    }

    public ResponseEntity createPost(String title, String text, Long authorId){
        Post post = new Post();
        post.setPostText(text);
        post.setTitle(title);
        post.setAuthorId(authorId);
        post.setIsBlocked(false);
        post.setTime(new Date());
        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.OK).body("Post created");
    }

}

