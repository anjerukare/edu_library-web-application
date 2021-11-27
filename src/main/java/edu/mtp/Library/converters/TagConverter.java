package edu.mtp.Library.converters;

import edu.mtp.Library.dao.TagDao;
import edu.mtp.Library.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements Converter<String, Tag> {

    private final TagDao tagDao;

    @Autowired
    public TagConverter(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag convert(String tagId) {
        return tagDao.get(Integer.parseInt(tagId));
    }
}
