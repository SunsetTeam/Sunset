package sunset.ui;

import arc.func.Cons;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Scaling;
import mindustry.ctype.UnlockableContent;
import mindustry.graphics.Pal;
import mindustry.ui.Cicon;
import mindustry.ui.dialogs.ContentInfoDialog;
import sunset.world.meta.SnCustomStat;
import sunset.world.meta.SnStats;
import sunset.world.meta.SnStatsUser;

/** Переопределяет стандартный интерфейс сведений о контенте
 * для возможности использовать SnStats или для возможности
 * отобразить свои сведения самостоятельно. */
public class ContentInfoDialogExt extends ContentInfoDialog {
    public final ContentInfoDialog parent;
    public ContentInfoDialogExt(ContentInfoDialog parent){
        this.parent=parent;
    }

    @Override
    public void show(UnlockableContent content) {
        if(content instanceof SnStatsUser) {
            showSnStats(content);
        } else if(content instanceof SnCustomStat) {
            showSnCustomStat(content);
        } else {
            parent.show(content);
        }
    }
    private void showSnStats(UnlockableContent content) {
        cont.clear();

        Table table = new Table();
        table.margin(10);

        content.checkStats();

        table.table(title1 -> {
            title1.image(content.uiIcon).size(32).scaling(Scaling.fit);
            title1.add("[accent]" + content.localizedName).padLeft(5);
        });

        table.row();
        SnStats stats = ((SnStatsUser)content).snStats();
        if(content.description != null){
            boolean any = content.stats.toMap().size > 0;

            if(any){
                table.add("@category.purpose").color(Pal.accent).fillX().padTop(10);
                table.row();
            }

            table.add("[lightgray]" + content.displayDescription()).wrap().fillX().padLeft(any ? 10 : 0).width(500f).padTop(any ? 0 : 10).left();
            table.row();

            if(!content.stats.useCategories && any){
                table.add("@category.general").fillX().color(Pal.accent);
                table.row();
            }
        }

        for(String cat : stats.toMap().keys()){
            OrderedMap<String, Seq<Cons<Table>>> map = stats.toMap().get(cat);

            if(map.size == 0) continue;

            //TODO check
            if(stats.useCategories){
                table.add(cat).color(Pal.accent).fillX();
                table.row();
            }

            for(String stat : map.keys()){
                table.table(inset -> {
                    inset.left();
                    inset.add("[lightgray]" + stat + ":[] ").left();
                    Seq<Cons<Table>> arr = map.get(stat);
                    for(Cons<Table> value : arr){
                        value.get(inset);
                        inset.add().size(10f);
                    }

                }).fillX().padLeft(10);
                table.row();
            }
        }

        if(content.details != null){
            table.add("[gray]" + content.details).pad(6).padTop(20).width(400f).wrap().fillX();
            table.row();
        }

        ScrollPane pane = new ScrollPane(table);
        cont.add(pane);

        show();
    }
    private void showSnCustomStat(UnlockableContent content) {
        cont.clear();

        Table table = new Table();
        table.margin(10);

        content.checkStats();

        table.table(title1 -> {

            title1.image(content.uiIcon).size(32).scaling(Scaling.fit);
            title1.add("[accent]" + content.localizedName).padLeft(5);
        });

        table.row();

        if(content.description != null){
            boolean any = content.stats.toMap().size > 0;

            if(any){
                table.add("@category.purpose").color(Pal.accent).fillX().padTop(10);
                table.row();
            }

            table.add("[lightgray]" + content.displayDescription()).wrap().fillX().padLeft(any ? 10 : 0).width(500f).padTop(any ? 0 : 10).left();
            table.row();
        }

        ((SnCustomStat)content).displayStats(table.table().get());
        table.row();

        if(content.details != null){
            table.add("[gray]" + content.details).pad(6).padTop(20).width(400f).wrap().fillX();
            table.row();
        }

        ScrollPane pane = new ScrollPane(table);
        cont.add(pane);

        show();
    }
}