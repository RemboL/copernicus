package pl.rembol.jme3.copernicus.config;

public class PlanetConfig extends AstralConfig {

    private AtmosphereConfig atmosphere;

    public AtmosphereConfig getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(AtmosphereConfig atmosphere) {
        this.atmosphere = atmosphere;
    }
}
