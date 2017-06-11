package com.dominik.backend.service;

import com.dominik.backend.entity.Tag;
import com.dominik.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dominik on 11.06.2017.
 */
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
}
