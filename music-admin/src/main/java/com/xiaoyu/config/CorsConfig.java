package com.xiaoyu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author YuSir
 * @version 1.0
 * @description: 解决跨域问题、定位各种文件地址
 * @date 2022/10/20 10:53
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
                .allowedOriginPatterns("*")
                //放行哪些请求方式
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"});
        //.allowedMethods("*") //或者放行全部
        //放行哪些原始请求头部信息
//                .allowedHeaders("*")
//                //暴露哪些原始请求头部信息
//                .exposedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 歌手头像地址
        registry.addResourceHandler("/images/singerPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                        + System.getProperty("file.separator") + "singerPic"
                        + System.getProperty("file.separator")
        );
        // 歌曲图片地址
        registry.addResourceHandler("/images/songPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                        + System.getProperty("file.separator") + "songPic"
                        + System.getProperty("file.separator")
        );
        // 歌单图片
        registry.addResourceHandler("/images/songListPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                        +System.getProperty("file.separator") + "songListPic"
                        +System.getProperty("file.separator")
        );
        // 歌曲地址
        //此处注意：访问带中文的本地资源需要在配置文件中进行配置
        //原因在于SpringBoot2.6版本之后修改了映射请求处理的默认策略，由原来的AntPathMatcher更改为PathPatternParser
        //spring.mvc.pathmatch.matching-strategy=ant_path_matcher
        registry.addResourceHandler("/music/**").addResourceLocations(
                "file:"+System.getProperty("user.dir") + System.getProperty("file.separator")
                        + "music"+System.getProperty("file.separator")
        );
        // 配置前端用户头像地址
        registry.addResourceHandler("/images/avatar/**").addResourceLocations(
                "file:"+System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                        +System.getProperty("file.separator") + "avatar"
                        +System.getProperty("file.separator")
        );
        // 配置验证码保存地址
        registry.addResourceHandler("/code/**").addResourceLocations(
                "file:"+System.getProperty("user.dir") + System.getProperty("file.separator") + "code"
                        +System.getProperty("file.separator")
        );
        // 配置轮播图保存地址
        registry.addResourceHandler("/images/banner/**").addResourceLocations(
                "file:"+System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                        +System.getProperty("file.separator") + "banner"
                        +System.getProperty("file.separator")
        );
    }
}
