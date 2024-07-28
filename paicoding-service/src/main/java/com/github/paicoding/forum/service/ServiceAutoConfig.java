package com.github.paicoding.forum.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author YiHui
 * @date 2022/7/6
 */
@Configuration
@ComponentScan("com.github.paicoding.forum.service")
/*@MapperScan(basePackages = {
        "com.github.paicoding.forum.service.article.repository.mapper",
        "com.github.paicoding.forum.service.user.repository.mapper",
        "com.github.paicoding.forum.service.comment.repository.mapper",
        "com.github.paicoding.forum.service.config.repository.mapper",
        "com.github.paicoding.forum.service.statistics.repository.mapper",
        "com.github.paicoding.forum.service.notify.repository.mapper",})*/
@MapperScan(basePackages = "com.github.paicoding.forum.service.**.repository.mapper")
public class ServiceAutoConfig {
    /*
    * 业务层配置类
    * 在@MapperScan注解中使用的路径模式是基于Spring框架的路径匹配策略。这里涉及到两种通配符：*和**。
      *：匹配路径中的一个段，但不跨越目录级别。例如，com.github.paicoding.forum.service.*.repository.mapper将匹配com.github.paicoding.forum.service直接下一级目录中的repository.mapper包，但不会匹配更深层次的目录结构。
      **：匹配路径中的多个段，包括任意层次的目录。因此，com.github.paicoding.forum.service.**.repository.mapper可以匹配com.github.paicoding.forum.service下的任意子目录中的repository.mapper包，无论它们位于多深的层次。
    * */
}