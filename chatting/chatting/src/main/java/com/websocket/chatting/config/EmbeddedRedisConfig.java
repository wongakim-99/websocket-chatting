package com.websocket.chatting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 로컬 환경일경우 내장 레디스가 실행된다.
 * Profile("local")과 같은 코드의 경우 해당 설정이 로컬 환경에서만 활성화되도록 설정함
 * 스프링 프로필 중 local 프로필이 활성화될 때에만 이 설정이 적용됨
 * 즉, application.properties에서 spring.profiles.active = local 로 설정한 경우에만 이 클래스가 동작
 * ※주의사항 : 실제 배포 환경이나 다른 환경에서는 이 설정이 적용되지 않도록 해야 함
 */
@Profile("local")
@Configuration  // 스프릴의 설정 클래스임을 나타내는 어노테이션. 이 클래스는 스프링이 관리하는 빈을 등록하고 설정
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")  // properties 파일에 설정된 Redis 포트를 주입받음
    private int redisPort;

    private RedisServer redisServer;  // 임베디드 Redis 서버를 실행하기 위한 클래스
    /*이 클래스를 사용하면 애플리케이션이 실행될 때 Redis 서버를 시작, 애플리케이션이 종료될 때 Redis 서버 종료*/
    // Redis 서버가 실제로 설치되어 있지 않아도 이 클래스를 통해 Redis 환경을 시뮬레이션 할 수 있음

    @PostConstruct
    public void redisServer() {
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}