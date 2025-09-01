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

import com.llamalad7.mixinextras.lib.apache.commons.ObjectUtils.Null;

import xyz.telosaddon.yuno.TelosAddon;
import xyz.telosaddon.yuno.utils.LocalAPI;

import static xyz.telosaddon.yuno.TelosAddon.LOGGER;
import static xyz.telosaddon.yuno.TelosAddon.CONFIG;

public class TeleportMenuScreen extends BaseOwoScreen<FlowLayout> {
    public static int count=0;
    public static boolean onServer=TelosAddon.getInstance().isOnTelos();
    final String[] NAServerNames = {
        "Groveridge",
        "Bayou",
        "Cedar",
        "Dakota",
        "Eagleton",
        "Farrion",
        "Ashburn",
        "Holloway",
        "Hub-1",
        "Hub-2",
        "Hub-3"
    };
    final String[] EUServerNames = {
            "Astra",
            "Balkan",
            "Creska",
            "Draskov",
            "Estenmoor",
            "Falkenburg",
            "Galla",
            "Helmburg",
            "Ivarn",
            "Jarnwald",
            "Krausenfeld",
            "Lindenburg",
            "Hub-1",
            "Hub-2",
            "Hub-3"
            };
    final String[] SGServerNames = {
            "Asura",
            "Bayan",
            "Chantara",
            "Hub-1",
            "Hub-2",
            "Hub-3"
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
                                Containers.grid(Sizing.content(), Sizing.content(), 4, calculateCols()) // Dynamically calculate the number of cols
                                        .<GridLayout>configure(layout -> {
                                            String[] finalServer = getServerName(); // get all server names
                                            layout.allowOverflow();
                                            int totalCols=calculateCols();
                                            int index=0;
                                            for (int i = 0; i < totalCols; i++) { // Iterate over the number of cols
                                                for (int j = 0; j < 4; j++) { // 4 row
                                                    if (index > finalServer.length-1) return ;
                                                    String realmName= finalServer[index];
                                                    //LOGGER.info(realmName+" "+ index +" "+ i + " "+ j);
                                                    layout.child(Components.button(Text.literal(realmName), button -> {
                                                        if (client == null || client.player == null || !onServer) return;
                                                        client.player.networkHandler.sendChatCommand("joinq " + realmName);
                                                    }).renderer(ButtonComponent.Renderer.flat(
                                                            new java.awt.Color(0, 0, 0, 150).getRGB(),
                                                            CONFIG.fillColor(),
                                                            new java.awt.Color(0, 0, 0, 50).getRGB()))
                                                            .sizing(Sizing.fixed(85), Sizing.fixed(20)).margins(Insets.of(5)), j, i
                                                    );
                                                    index+=1;
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
        if (!onServer){
            LOGGER.info("Player is not on tellos");
            return new String[] {"Not on Telos"};
        }
        String currentArea = LocalAPI.getCurrentCharacterWorld();
            if (currentArea.trim().toLowerCase().charAt(0) == 'n') {
                return NAServerNames;
            } else if (currentArea.trim().toLowerCase().charAt(0) == 'g') {
                return EUServerNames;
            } else {
                return SGServerNames;
            }
    }

    // Dynamically calculate the number of cols based on the number of servers
    private int calculateCols() { 
        int totalServers = getServerName().length;
        return (int) Math.ceil(totalServers / 4.0); // 4 Columns 
    }
}
