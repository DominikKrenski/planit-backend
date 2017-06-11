package com.dominik.backend.service;

import com.dominik.backend.entity.Tag;

/**
 * Created by dominik on 11.06.2017.
 */
public interface TagService {

    Tag saveTag(Tag tag);
    Tag findTagByName(String name);
}
