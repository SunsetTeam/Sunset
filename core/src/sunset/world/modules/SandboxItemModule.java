package sunset.world.modules;

import arc.util.io.*;
import mindustry.type.*;
import mindustry.world.modules.*;

public class SandboxItemModule extends ItemModule{
    public SandboxItemModule(){
        super();
    }

    @Override
    public ItemModule copy(){
        return new SandboxItemModule();
    }

    @Override
    public void set(ItemModule other){
    }

    @Override
    public void update(boolean showFlow){
        super.update(showFlow);
    }

    @Override
    public float getFlowRate(Item item){
        return super.getFlowRate(item);
    }

    @Override
    public boolean hasFlowItem(Item item){
        return super.hasFlowItem(item);
    }

    @Override
    public boolean has(Item item){
        return true;
    }

    @Override
    public boolean has(Item item, int amount){
        return true;
    }

    @Override
    public boolean has(ItemStack[] stacks){
        return true;
    }

    @Override
    public boolean has(ItemSeq items){
        return true;
    }

    @Override
    public boolean has(Iterable<ItemStack> stacks){
        return true;
    }

    @Override
    public boolean has(ItemStack[] stacks, float multiplier){
        return true;
    }

    @Override
    public boolean hasOne(ItemStack[] stacks){
        return true;
    }

    @Override
    public Item first(){
        return super.first();
    }

    @Override
    public Item take(){
        return super.take();
    }

    @Override
    public Item takeIndex(int takeRotation){
        return super.takeIndex(takeRotation);
    }

    @Override
    public int nextIndex(int takeRotation){
        return super.nextIndex(takeRotation);
    }

    @Override
    public int get(int id){
        return super.get(id);
    }

    @Override
    public int get(Item item){
        return super.get(item);
    }

    @Override
    public void set(Item item, int amount){
        super.set(item, amount);
    }

    @Override
    public void add(Iterable<ItemStack> stacks){
        super.add(stacks);
    }

    @Override
    public void add(ItemSeq stacks){
        super.add(stacks);
    }

    @Override
    public void add(ItemModule items){
        super.add(items);
    }

    @Override
    public void add(Item item, int amount){
        super.add(item, amount);
    }

    @Override
    public void undoFlow(Item item){
        super.undoFlow(item);
    }

    @Override
    public void remove(Item item, int amount){
        super.remove(item, amount);
    }

    @Override
    public void remove(ItemStack[] stacks){
        super.remove(stacks);
    }

    @Override
    public void remove(ItemSeq stacks){
        super.remove(stacks);
    }

    @Override
    public void remove(Iterable<ItemStack> stacks){
        super.remove(stacks);
    }

    @Override
    public void remove(ItemStack stack){
        super.remove(stack);
    }

    @Override
    public void clear(){
        super.clear();
    }

    @Override
    public void write(Writes write){
        super.write(write);
    }

    @Override
    public void read(Reads read, boolean legacy){
        super.read(read, legacy);
    }
}
