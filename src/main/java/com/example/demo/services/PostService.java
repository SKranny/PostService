package com.example.demo.services;

import com.example.demo.exception.PostException;
import com.example.demo.feign.PersonService;
import com.example.demo.feign.PostRequest;
import com.example.demo.mappers.PostMapper;
import com.example.demo.model.Post;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.specifications.PostSpecification;
import dto.postDto.PostDTO;
import dto.userDto.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PersonService personService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostDTO findById(Long id) {
        return postRepository
                .findById(id)
                .map(postMapper::toDTO)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
    }

    public Page<Post> find(Integer page, Boolean withFriends, LocalDate time, Boolean isDelete) {
        Specification<Post> specification = Specification.where(null);
        if (withFriends != null) {
            specification = specification.and(PostSpecification.isWithFriends(withFriends));
        }

        if (time != null) {
            specification = specification.and(PostSpecification.time(time));
        }

        if (isDelete != null) {
            specification = specification.and(PostSpecification.isDeletePost(isDelete));

        }
        return postRepository.findAll(specification, PageRequest.of(page - 1, 5));
    }


    public PostDTO editPost(PostRequest postRequest, Long id){
        return postRepository
                .findById(id)
                .map(p -> {
                    p.setTitle(postRequest.getTitle());
                    p.setPostText(postRequest.getText());
                    postRepository.save(p);
                    return postMapper.toDTO(p);
                })
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
    }

    public void delete(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException("Post with the id doesn't exist", HttpStatus.BAD_REQUEST));
        postRepository.delete(post);
    }

    public List<PostDTO> getAllPosts(){
        return postRepository
                .findAllByOrderByTimeDesc()
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PostDTO createPost(PostRequest postRequest){
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setPostText(postRequest.getText());
        post.setAuthorId(postRequest.getAuthorId());
        post.setTime(LocalDateTime.now());
        post.setIsBlocked(false);
        postRepository.save(post);
        return postMapper.toDTO(post);
    }

    public List<PostDTO> getAllPostsByUser(String email){
        PersonDTO personDTO = personService.getPersonDTOByEmail(email);
        return postRepository.findAll()
                .stream()
                .filter(i -> Objects.equals(i.getAuthorId(), personDTO.getId()))
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getAllPostsByUserId(Long id) {
        return postRepository.findAll()
                .stream()
                .filter(i -> Objects.equals(i.getAuthorId(), id))
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }
}

