package com.compose.service;

import com.compose.entities.Reader;
import com.compose.mapper.ReaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<Reader> getReaders() {
        // 从redis中获取
        if (Boolean.TRUE.equals(redisTemplate.hasKey("READER"))) {
            return (List<Reader>) redisTemplate.opsForValue().get("READER");
        }

        // 从数据库中查询
        List<Reader> readers = readerMapper.selectList(null);

        // 放到redis缓存中
        redisTemplate.opsForValue().set("READER", readers);

        return readers;
    }


    // 新增用户
    @Override
    public String newReader() {
        Reader reader = new Reader();
        reader.setCardId(UUID.randomUUID().toString().substring(0, 10));
        reader.setName("程序新增");
        reader.setAge(18);
        reader.setBalance(new BigDecimal(100));
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        reader.setInputdate(date);
        reader.setUpdatedate(date);
        reader.setSex("1");
        reader.setTel("13881764098");

        readerMapper.insert(reader);

        // 刷新redis缓存
        redisTemplate.delete("READER");

        return "新增读者成功！";
    }
}
