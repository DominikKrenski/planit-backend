package com.dominik.backend.service;

import com.dominik.backend.entity.Role;

/**
 * Created by dominik on 07.04.17.
 */
public interface RoleService {

    Role getRoleByName(String name);
}
