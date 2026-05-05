package com.juanmunguia.to_do_list_final_project.tags;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TagService {

    private TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public List<Tag> getAll() {
        return repository.findAll();
    }

    public Tag getTagById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public Tag save(TagDTO dto) {
        if (repository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Tag already exists");
        }

        Tag newTag = Tag.builder()
                .name(dto.getName())
                .build();

        return repository.save(newTag);
    }

    public Tag updateTag(Long id, TagDTO dto) {
        Tag tagToUpdate = repository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));

        tagToUpdate.setName(dto.getName());

        return repository.save(tagToUpdate);
    }

    public String detele(Long id) {
        Tag tagToDelete = repository.findById(id).orElseThrow(() -> new RuntimeException("  Tag not found"));
        repository.delete(tagToDelete);
        return "Tag deleted successfully";
    }

}
