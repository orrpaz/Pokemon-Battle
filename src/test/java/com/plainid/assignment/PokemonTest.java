package com.plainid.assignment;

import com.plainid.assignment.dao.Battle;
import com.plainid.assignment.dao.PokemonList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PokemonTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllPokemons(){
        PokemonList pokemonList = restTemplate.getForEntity("http://localhost:" + port + "/pokemon/list",
                PokemonList.class).getBody();
        assertThat(pokemonList).isNotNull();
        assertThat(pokemonList.getPokemons()).isNotNull();

    }
    @Test
    public void testBattleWins(){
        Battle battle = restTemplate.getForEntity("http://localhost:" + port + "/battle/AVI/YOSSI",
                Battle.class).getBody();
        assertThat(battle).isNotNull();
        assertThat(battle.getStatus()).isNotNull();
        assertThat(battle.getMassage()).isNotNull();
        assertThat(battle.getStatus()).isEqualTo("success");
        assertThat(battle.getMassage()).isEqualTo("YOSSI wins");
    }
    @Test
    public void testBattleCanceled(){
        Battle battle = restTemplate.getForEntity("http://localhost:" + port + "/battle/OMER/OR",
                Battle.class).getBody();
        assertThat(battle).isNotNull();
        assertThat(battle.getStatus()).isNotNull();
        assertThat(battle.getMassage()).isNotNull();
        assertThat(battle.getStatus()).isEqualTo("error");
        assertThat(battle.getMassage()).isEqualTo("canceled");
    }
    @Test
    public void testBattleDraw(){
        Battle battle = restTemplate.getForEntity("http://localhost:" + port + "/battle/MATAN/AMITAY",
                Battle.class).getBody();
        assertThat(battle).isNotNull();
        assertThat(battle.getStatus()).isNotNull();
        assertThat(battle.getMassage()).isNotNull();
        assertThat(battle.getStatus()).isEqualTo("success");
        assertThat(battle.getMassage()).isEqualTo("draw");
    }
    @Test
    public void testCatchPokemons(){
        ResponseEntity<List<String>> response =
                restTemplate.exchange("http://localhost:" + port + "/trainer/YOSSI/catch/Bulbasaur",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                        });
        assertTrue(response.getBody().contains("Bulbasaur"));



    }
}
