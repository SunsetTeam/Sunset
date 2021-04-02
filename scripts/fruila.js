const fruila = extend(Planet, "fruila", Planets.sun, 4, 6, {
    generator: new SerpuloPlanetGenerator(),
    meshLoader: () => new HexMesh(this, 7),
    bloom: true,
    radius: 1.3,
    accessible: true,
    hasAtmosphere: true,
    atmosphereColor: Color.valueOf("CFEBAAFF"),
    atmosphereRadIn: 0.07,
    atmosphereRadOut: 0.10,
    localizedName: "Fruila"
});
fruila.meshLoader = () => extend(HexMesh, fruila, 7, {});


    const shatteredCreaters = extend(SectorPreset, "shattered-creaters", templura, 23, {
    captureWave: 45,
    localizedName: "Shattered Creaters",
    difficulty: 4
    description: "-"
    alwaysUnlocked: true
})

module.exports = {
    fruila: fruila,
    shattered-creaters: shattered-creaters
    
}