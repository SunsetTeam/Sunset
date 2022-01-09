package sunset.core;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.scene.Element;
import arc.scene.event.Touchable;
import arc.scene.ui.TextButton;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import sunset.SnVars;
import sunset.type.planets.*;

import static mindustry.Vars.headless;

public class SnContentTranslation {

    public static final String invalidTranslation = "[#FF0000]//TODO";
    final static ObjectMap<ContentType, ObjectMap<UnlockableContent, ObjectSet<WrongTranslationType>>> contentWithoutTranslation = new ObjectMap<>();

    static {
        if (!headless) {
            Events.on(EventType.ClientLoadEvent.class, e -> {
                Time.runTask(10f, SnContentTranslation::showDialog);
            });
        }
    }

    private static void showDialog() {
      /*  for (ContentType key : contentWithoutTranslation.keys().toSeq()) {
            ObjectMap<UnlockableContent, ObjectSet<WrongTranslationType>> map = contentWithoutTranslation.get(key);
            Seq<UnlockableContent> mapKeys = map.keys().toSeq();
            for (UnlockableContent mapKey : mapKeys) {
                if (map.get(mapKey).size==0) {
                    map.remove(mapKey);
                }
            }

            if (map.size==0) {
                contentWithoutTranslation.remove(key);
            }
        }*/
        if (contentWithoutTranslation.size == 0) return;
        new TranslationsDialog().show();

    }

    public static void checkContent(UnlockableContent content) {
        SnSector.setup(content);
        if (headless) return;
        String nameKey = content.getContentType() + "." + content.name + ".name";
        String descriptionName = content.getContentType() + "." + content.name + ".description";
        String detailsKey = content.getContentType() + "." + content.name + ".details";

        ObjectMap<String, String> map = Core.bundle.getProperties();
        ObjectSet<WrongTranslationType> enumSet = new ObjectSet<>();

        if (!map.containsKey(nameKey)) {
            map.put(nameKey, invalidTranslation);
            enumSet.add(WrongTranslationType.name);
        }
        if (!map.containsKey(descriptionName)) {
            map.put(descriptionName, invalidTranslation);
            enumSet.add(WrongTranslationType.description);
        }
        if (!map.containsKey(detailsKey)) {
            map.put(detailsKey, invalidTranslation);
            enumSet.add(WrongTranslationType.details);
        }
        if (enumSet.size > 0) {
            contentWithoutTranslation.get(content.getContentType(), ObjectMap::new).put(content, enumSet);
        }

        content.localizedName = Core.bundle.get(nameKey, content.localizedName);
        content.description = Core.bundle.get(descriptionName, content.description);
        content.details = Core.bundle.get(detailsKey, content.details);
    }

    enum WrongTranslationType {
        description, name, details;
    }

    private static class TranslationsDialog extends BaseDialog {

        ObjectSet<ContentType> opened = new ObjectSet<>();

        public TranslationsDialog() {
            super("translation-dialog");
            setup();
        }

        private void setup() {
//        Tmp.m1.
            cont.fill(t -> t.pane(this::rebuild).grow());
            addCloseButton();
//        cont.pane(this::rebuild).fill().center().get().fillParent = true;
        }

        private void rebuild(Table table) {
            table.clearChildren();


            for (ContentType type : contentWithoutTranslation.keys().toSeq().sort()) {
                table.button(type.name(),new TextButton.TextButtonStyle(Styles.togglet), () -> {
                    if (!opened.add(type)) {
                        opened.remove(type);
                    }
                    rebuild(table);
                }).width(600f).left().checked(opened.contains(type));
                if (opened.contains(type)) {
                    table.row();
                    ObjectMap<UnlockableContent, ObjectSet<WrongTranslationType>> contentMap = contentWithoutTranslation.get(type);
                    table.table(t -> {
                        Seq<UnlockableContent> keys = contentMap.keys().toSeq().sort();
                        for (UnlockableContent key : keys) {
                            boolean bottom = key == keys.peek();
                            t.add(new Element() {
                                @Override
                                public void draw() {
                                    super.draw();
                                    Draw.color(Pal.darkerMetal);
                                    Lines.stroke(8f);
                                    drawLines(4f);
                                    Draw.color(color);
                                    Lines.stroke(4f);
                                    drawLines(2f);
                                }

                                public void drawLines(float strokeOffset) {
                                    float xoff = width / 3f;
                                    float padTop = 0f;
                                    float padBottom = 0f;
                                    if (bottom) {
                                        Lines.line(x + xoff, y + height / 2f, x + xoff, y + height-strokeOffset);
                                    } else {
                                        Lines.line(x + xoff, y - padBottom+strokeOffset, x + xoff, y + height + padTop-strokeOffset);
                                    }
                                    Lines.line(x + xoff, y + height / 2f, x + width, y + height / 2f);
                                }
                            }).width(10).fill();
                            t.button(SnVars.realName(key), () -> {
                                Vars.ui.content.show(key);
                            }).left().width(200f).touchable(Touchable.enabled);
                            t.add("[red]" + contentMap.get(key).toString("[lightgray], []")).left();
                            t.row();
                        }
                    }).left().padLeft(10f);
                }
                table.row();
            }
        }

    }
}

