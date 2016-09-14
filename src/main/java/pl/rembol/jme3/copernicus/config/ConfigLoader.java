package pl.rembol.jme3.copernicus.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.skybox.SkyBox;
import pl.rembol.jme3.copernicus.stellarobjects.Planet;
import pl.rembol.jme3.copernicus.stellarobjects.Star;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigLoader {
    private final GameState gameState;

    public ConfigLoader(GameState gameState) {
        this.gameState = gameState;
    }

    public void loadFromFile(String fileName) {
        Config config = loadConfig(fileName);

        if (config != null) {
            createSkybox(config.getSkybox());
            config.getStars().forEach(this::createStar);
            config.getPlanets().forEach(this::createPlanet);
        }

    }

    private Config loadConfig(String fileName) {
        try {
            Constructor constructor = new Constructor(Config.class);
            TypeDescription configDescription = new TypeDescription(Config.class);
            constructor.addTypeDescription(configDescription);
            return (Config) new Yaml(constructor).load(Files.newInputStream(Paths.get(ClassLoader.getSystemResource(fileName).toURI())));

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createSkybox(String skybox) {
        if (skybox != null) {
            new SkyBox(gameState, skybox);
        }
    }

    private void createStar(StarConfig starConfig) {
        Star star = new Star(gameState, starConfig.getTexture(), starConfig.getName(), starConfig.getRadius(), starConfig.getMass());
        star.setPrecisePosition(starConfig.getPosition());
        star.accelerate(starConfig.getVelocity());
    }

    private void createPlanet(PlanetConfig planetConfig) {
        Planet planet = new Planet(
                gameState,
                planetConfig.getTexture(),
                planetConfig.getName(),
                planetConfig.getRadius(),
                planetConfig.getMass(),
                planetConfig.getRotation());
        planet.setPrecisePosition(planetConfig.getPosition());
        planet.accelerate(planetConfig.getVelocity());
        if (planetConfig.getAtmosphere() != null) {
            planet.addAtmosphere(
                    planetConfig.getAtmosphere().getTexture(),
                    planetConfig.getAtmosphere().getHeight(),
                    planetConfig.getRotation());
        }
    }
}
