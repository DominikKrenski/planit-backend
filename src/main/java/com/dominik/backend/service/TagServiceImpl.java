package com.dominik.backend.service;

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
}
