package game.security.tictactoe.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "game.security.tictactoe")
@EnableJpaRepositories("game.security.tictactoe")
@EntityScan("game.security.tictactoe")
@EnableFeignClients("game.security.tictactoe")
@EnableDiscoveryClient
public class TicTacToeApplication {
    public static void main(String[] args){
        SpringApplication.run(TicTacToeApplication.class, args);
    }
}
