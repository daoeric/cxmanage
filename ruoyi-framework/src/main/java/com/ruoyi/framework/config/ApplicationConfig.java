package com.ruoyi.framework.config;

import java.util.TimeZone;

import com.ruoyi.common.utils.uuid.SnowflakeKeyGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序注解配置
 *
 * @author ruoyi
 */
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan({"com.baomidou.mybatisplus.samples.quickstart.mapper","com.ruoyi.**.mapper"})
public class ApplicationConfig
{
    /**
     * 时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization()
    {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }

    @Bean
    public SnowflakeKeyGenerator getSnowflakeKeyGenerator(@Value("${keyGenerator.snowflake.workerId:20}") Integer workerId,
                                                          @Value("${keyGenerator.snowflake.maxTolerateTime:1500}") Integer maxTolerateTime) {
        SnowflakeKeyGenerator.setWorkerId(workerId);
        SnowflakeKeyGenerator.setMaxTolerateTimeDifferenceMilliseconds(maxTolerateTime);
        return new SnowflakeKeyGenerator();
    }
}
