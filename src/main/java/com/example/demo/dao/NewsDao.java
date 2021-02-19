package com.example.demo.dao;

import com.example.demo.dto.NewsDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName NewsDao
 * @Des
 * @Author caizhixiang
 * @Date 2021/2/12 21:19
 */
public interface NewsDao extends ElasticsearchRepository<NewsDto,String> {
}
