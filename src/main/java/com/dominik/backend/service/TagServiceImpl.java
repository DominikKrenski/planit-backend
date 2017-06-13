package com.dominik.backend.service;

import com.dominik.backend.entity.Tag;
import com.dominik.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dominik on 11.06.2017.
 */

@Service
public class TagServiceImpl implements TagService {

    private TagRepository repository;

    @Autowired
    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tag saveTag(Tag tag) {
        return repository.save(tag);
    }

    @Override
    public Tag findTagByName(String name) {
        return repository.findOneByName(name);
    }

    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = (List<Tag>)repository.findAll();
        return tags;
    }

    @Override
    public void deleteTags() {
        repository.deleteAll();
    }

    @Override
    public void deleteTag(Tag tag) {
        repository.delete(tag);
    }

    @Override
    public Tag findTagById(Long id) {
        return repository.findOne(id);
    }
}
