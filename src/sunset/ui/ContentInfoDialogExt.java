package sunset.ui;

import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import mindustry.ctype.UnlockableContent;
import mindustry.graphics.Pal;
import mindustry.ui.Cicon;
import mindustry.ui.dialogs.ContentInfoDialog;
import sunset.type.ContentDisplayerType;

/** Переопределяет стандартный интерфейс сведений о контенте для возможности перенести
 *  заполнение сведений непосредственно самому контенту. */
public class ContentInfoDialogExt extends ContentInfoDialog {
    @Override
    public void show(UnlockableContent content) {
        if(content instanceof ContentDisplayerType && !((ContentDisplayerType)content).useStat()) {
            showInternal(content);
        } else {
            super.show(content);
        }
    }
    private void showInternal(UnlockableContent content) {
        cont.clear();

        Table table = new Table();
        table.margin(10);

        //initialize stats if they haven't been yet
        content.checkStats();

        table.table(title1 -> {
            Cicon size = content.prefDatabaseIcon();

            title1.image(content.icon(size)).size(size.size).scaling(Scaling.fit);
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

        table.row();
        content.display(table.table().get());
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