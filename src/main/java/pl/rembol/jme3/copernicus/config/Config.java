package pl.rembol.jme3.copernicus.config;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private String skybox;

    private List<StarConfig> stars = new ArrayList<>();

    private List<PlanetConfig> planets = new ArrayList<>();

    public String getSkybox() {
        return skybox;
    }

    public void setSkybox(String skybox) {
        this.skybox = skybox;
    }

    public List<StarConfig> getStars() {
        return stars;
    }

    public void setStars(List<StarConfig> stars) {
        this.stars = stars;
    }

    public List<PlanetConfig> getPlanets() {
        return planets;
    }

    public void setPlanets(List<PlanetConfig> planets) {
        this.planets = planets;
    }
}
