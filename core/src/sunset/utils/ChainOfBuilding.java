package sunset.utils;

import arc.func.*;
import arc.struct.*;
import mindustry.gen.*;
import sunset.utils.ChainOfBuilding.*;

public class ChainOfBuilding<T extends Building&BuildingWithChain>{
    protected final static Queue<Building> blockQueue = new Queue<>();
    public Boolf<Building> blockFilter;
    public Seq<T> chained = new Seq<>();
    public boolean chainUpdater = true;
    public T building;

    public ChainOfBuilding(T building, Boolf<Building> blockFilter){
        this.blockFilter = blockFilter;
        this.building = building;
    }

    @SuppressWarnings("unchecked")
    public void updateChain(){
        chained.clear();
        chained = new Seq<>();

        blockQueue.clear();
        blockQueue.add(building);

        while(!blockQueue.isEmpty()){
            var next = blockQueue.removeLast();
            chained.add((T)next);

            for(var b : next.proximity){
                if(b instanceof BuildingWithChain buildingWithChain && blockFilter.get(b)){
                    ChainOfBuilding<T> chain = (ChainOfBuilding<T>)buildingWithChain.chain();
                    if(chain.chained != chained && blockFilter.get(b)){
                        chain.chained = chained;
                        blockQueue.addFirst(b);
                    }
                }
            }
        }
        chainUpdater = true;
        chained.each(w -> {
            if(w == building) return;
            ChainOfBuilding<?> chain = w.chain();
            if(chain.chainUpdater){
                chainUpdater = false;
            }
        });
    }

    public interface BuildingWithChain{
        ChainOfBuilding<?> chain();
    }
}
