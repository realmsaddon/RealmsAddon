package xyz.telosaddon.yuno.ui.tabs;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import xyz.telosaddon.yuno.utils.LocalAPI;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;

public class TeleportMenuScreen extends BaseOwoScreen<FlowLayout> {
    final String[] NAServerNames = { // man fuck this shit idk how to make it generate dynamically
            "Ashburn", "Bayou", "Cedar", "Dakota",
            "Eagleton", "Farrion", "Groveridge", "Holloway",
            "", "", "", "",
    };
    final String[] EUServerNames = {
            "Astra", "Balkan", "Creska", "Draskov",
            "Estenmoor", "Falkenburg", "Galla", "Helmburg",
            "Ivarn", "Jarnwald", "Krausenfeld", "Lindenburg"
    };

    final String[] SGServerNames = {
            "Asura", "Bayan", "Chantara", "", "","","","",
            "", "", "", "",
    };


    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER)
                .padding(Insets.of(5));

        rootComponent.child(
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(Components.label(Text.literal("Teleport Menu")))
                        .child(
                                Containers.grid(Sizing.content(), Sizing.content(),3,4)

                                        .<GridLayout>configure(layout ->{
                                            String[] finalServerNames = getServerName();
                                            layout.allowOverflow();
                                            for(int i = 0; i < 4; i++){
                                                for(int j = 0; j < 3; j++) {
                                                    int finalI = i;
                                                    int finalJ = j;

                                                    layout.child(Components.button(Text.literal(finalServerNames[finalJ*4+finalI]), button -> {
                                                        if (client == null || client.player == null) return;
                                                        client.player.networkHandler.sendChatCommand("joinq " + finalServerNames[finalJ*4+finalI]);
                                                    }).renderer(ButtonComponent.Renderer.flat(
                                                            new java.awt.Color(0, 0, 0, 150).getRGB(),
                                                            CONFIG.fillColor(),
                                                            new java.awt.Color(0, 0, 0, 50).getRGB()))
                                                            .sizing(Sizing.fixed(55), Sizing.fixed(20)).margins(Insets.of(5)), j, i
                                                    );
                                                }
                                            }
                                        })
                                        .surface(Surface.DARK_PANEL)
                                        .verticalAlignment(VerticalAlignment.CENTER)
                                        .horizontalAlignment(HorizontalAlignment.CENTER)
                                        .margins(Insets.of(5))


                        )
                        .margins(Insets.of(30))
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)



        );
    }

    private String[] getServerName() {
        String[] serverNames;
        String currentArea = LocalAPI.getCurrentCharacterWorld();
        System.out.println(currentArea);
        if (currentArea.trim().charAt(0) == 'N'){
            serverNames = NAServerNames;
        }
        else if (currentArea.trim().charAt(0) == 'G'){
            serverNames = EUServerNames;
        }
        else{
            serverNames = SGServerNames;
        }
        String[] finalServerNames = serverNames;
        return finalServerNames;
    }
}
