package edu.mtp.Library.converters;

import edu.mtp.Library.dao.RoleDao;
import edu.mtp.Library.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<String, Role> {

    private final RoleDao roleDao;

    @Autowired
    public RoleConverter(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role convert(String roleId) {
        return roleDao.get(Integer.parseInt(roleId));
    }
}
